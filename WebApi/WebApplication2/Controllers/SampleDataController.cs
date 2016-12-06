using System.Collections.Generic;
using System.Web.Http;
using WebApplication2.Models;
using System.Device;
using System.Device.Location;
using System.Linq;
using System.Diagnostics;
using System.IO;
using System;
using WebApplication2.DataModels;

namespace WebApplication2.Controllers
{

    public class SampleDataController : ApiController
    {
        #region fields
        /// <summary>
        /// The distance in meters in which if two coordinates are this close together they will be consolodated
        /// </summary>
        private const int consolidationConstant = 20;

        /// <summary>
        /// the file path for the systems python interpreter
        /// </summary>
        private const string PythonFilePath = @"C:\Python\python.exe";

        /// <summary>
        /// The filepath for the trilateration python script
        /// </summary>
        private const string PythonScriptFilePath = @"C:\Python\trilaterate.py";

        /// <summary>
        /// List of consolidated locations based off of the datapoints
        /// </summary>
        private static List<Coordinate> _coordinates = new List<Coordinate>();

        /// <summary>
        /// List of individual drone data points
        /// </summary>
        private static List<DroneData> _droneDataPoints = new List<DroneData>();

        /// <summary>
        /// List of all data points calculated from the drone data, will be consolidated into the coordinates list
        /// </summary>
        private static List<Coordinate> _dataPoints = new List<Coordinate>();

        #endregion

        #region endpoints

        /// <summary>
        /// Get method which returns a list of all coordinates
        /// </summary>
        /// <returns></returns>
        public Coordinates Get()
        {
            if (_coordinates.Count == 0)
            {
                //seedOriginalData();
            }
            return new Coordinates()
            {
                CoordinateList = _coordinates
            };
        }

        /// <summary>
        /// Post method which puts in a new data point
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public IHttpActionResult Post([FromBody]DroneData value)
        {
            if (value != null)
            {
                processDroneData(value);
                return Ok();
            }
            else
            {
                return BadRequest();
            }
        }

        #endregion

        #region private methods

        /// <summary>
        /// Method will try to create a new set of drone data points to trilaterate
        /// </summary>
        /// <param name="newData"></param>
        private void processDroneData(DroneData newData)
        {

            if (_droneDataPoints.Count < 2)
            {
                _droneDataPoints.Add(newData);

            }
            else
            {
                var builder = new DroneDataSetBuilder();
                builder.addDroneData(newData);

                foreach (DroneData d in _droneDataPoints)
                {
                    if (d.ScanId == newData.ScanId)
                    {
                        builder.addDroneData(d);
                    }
                }

                if (builder.canBuild())
                {
                    _droneDataPoints = _droneDataPoints.Where(x => x.ScanId != newData.ScanId).ToList<DroneData>(); //removes data that was sent to dronedataset
                    trilaterate(builder.build());
                }
                else
                {
                    _droneDataPoints.Add(newData);
                }
            }
        }

        /// <summary>
        /// Calls a python function to run trilateration function to find position of unknown object
        /// Automatically retries with larger distances if function fails
        /// </summary>
        /// <param name="dds"></param>
        private void trilaterate(DroneDataSet dds)
        {

            for (int i = 0; i < 10; i++)
            {
                Coordinate output;
                if (i > 0)
                {
                    dds = addToSmallestDistance(dds);
                }

                ProcessStartInfo start = new ProcessStartInfo();
                start.FileName = PythonFilePath;//cmd is full path to python.exe
                start.Arguments = PythonScriptFilePath + " " + dds.ToString();//args is path to .py file and any cmd line args
                start.UseShellExecute = false;
                start.RedirectStandardOutput = true;
                using (Process process = Process.Start(start))
                {
                    using (StreamReader reader = process.StandardOutput)
                    {

                        string result = reader.ReadToEnd();
                        string[] words = result.Split(" ".ToArray());
                        string[] secondwords = words[1].Split("/".ToArray());

                        if (!result.Contains("nan"))
                        {

                            double lat = double.Parse(words[0]);
                            double lng = double.Parse(secondwords[0]);

                            output = new Coordinate(new LatLng(lat, lng), i);

                            addNewCoordinate(output);

                            break;
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Adds 1 to the smallest distance in a drone data array
        /// </summary>
        /// <param name="dds"></param>
        /// <returns></returns>
        private DroneDataSet addToSmallestDistance(DroneDataSet dds)
        {
            DroneData output = new DroneData("", double.MaxValue, new LatLng(0, 0), "");

            int index = 0;
            for (int i = 0; i < dds.droneDataSet.Count; i++)
            {
                if (dds.droneDataSet.ElementAt(i).Distance < output.Distance)
                {
                    output = dds.droneDataSet.ElementAt(i);
                    index = i;
                }
            }

            dds.droneDataSet.ElementAt(index).Distance += 1;

            return dds;
        }

        /// <summary>
        /// Adds a new coordinate to the database/arraylist, and checks if it needs to be consoliated with anything else
        /// </summary>
        /// <param name="newCoord"></param>
        private void addNewCoordinate(Coordinate newCoord)
        {
            _dataPoints.Add(newCoord);

            foreach (Coordinate c in _coordinates)
            {
                if (distanceBetween(c, newCoord) < consolidationConstant)
                {
                    Coordinate avgCoord = averageCoordinates(c, newCoord);
                    _coordinates.Remove(c);
                    _coordinates.Add(avgCoord);
                    return;
                }
            }

            _coordinates.Add(newCoord);
        }

        /// <summary>
        /// Average values of 2 coordinates
        /// </summary>
        /// <param name="one"></param>
        /// <param name="two"></param>
        private Coordinate averageCoordinates(Coordinate one, Coordinate two)
        {
            one.Accuracy = one.Accuracy > two.Accuracy ? one.Accuracy : two.Accuracy;
            one.Accuracy = one.Accuracy > distanceBetween(one, two) ? one.Accuracy : distanceBetween(one, two);
            one.LatLng.lat = (one.LatLng.lat + two.LatLng.lat) / 2;
            one.LatLng.lng = (one.LatLng.lng + two.LatLng.lng) / 2;

            return one;

        }

        /// <summary>
        /// Returns the distance between 2 coordinates
        /// </summary>
        /// <param name="one"></param>
        /// <param name="two"></param>
        /// <returns></returns>
        private double distanceBetween(Coordinate one, Coordinate two)
        {

            var sCoord = new GeoCoordinate(one.LatLng.lat, one.LatLng.lng);
            var eCoord = new GeoCoordinate(two.LatLng.lat, two.LatLng.lng);

            return sCoord.GetDistanceTo(eCoord);

        }

        /// <summary>
        /// Some fake data to seed
        /// </summary>
        private void seedOriginalData()
        {

            LatLng pos1 = new LatLng(37.06053496780209, -76.49047845974565);
            LatLng pos2 = new LatLng(37.06219398671905, -76.48933410469908);
            LatLng pos3 = new LatLng(37.06625769597734, -76.49276732699946);

            _coordinates.Add(new Coordinate(pos1, 10));
            _coordinates.Add(new Coordinate(pos2, 5));
            _coordinates.Add(new Coordinate(pos3, 7));

            _droneDataPoints.Add(
                new DroneData(
                    "swxedcrfvtgb"
                    , 114.207460633365
                    , new LatLng(37.06431008815, -76.49267291623)
                    , "1"));
            _droneDataPoints.Add(
                new DroneData(
                    "qzawsxedcrfvg"
                    , 211.437352892073
                    , new LatLng(37.06425093355, -76.49134212943)
                    , "1"));
        }

        #endregion
    }
}
