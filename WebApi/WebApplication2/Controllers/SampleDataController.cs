using System.Collections.Generic;
using System.Web.Http;
using WebApplication2.Models;
using System.Device;
using System.Device.Location;
using System.Linq;
using System.Diagnostics;
using System.IO;
using System;

namespace WebApplication2.Controllers
{

    public class SampleDataController : ApiController
    {
        private const int consolidationConstant = 10;

        private static List<Coordinate> activeLocations = new List<Coordinate>();
        private static List<DroneData> droneData = new List<DroneData>();

        //Get coordinates
        public SampleData Get()
        {
            if (activeLocations.Count == 0)
            {
                seedOriginalData();
            }
            return new SampleData()
            {
                Coordinates = activeLocations
            };
        }
        
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



        private void processDroneData(DroneData newData)
        {

            if (droneData.Count < 2)
            {
                droneData.Add(newData);

            }
            else
            {
                var builder = new DroneDataSetBuilder();
                builder.addDroneData(newData);

                foreach (DroneData d in droneData)
                {
                    if (d.ScanId == newData.ScanId)
                    {
                        builder.addDroneData(d);
                    }
                }

                if (builder.canBuild())
                {
                    droneData = droneData.Where(x => x.ScanId != newData.ScanId).ToList<DroneData>(); //removes data that was sent to dronedataset
                    trilaterate(builder.build());
                }
                else
                {
                    droneData.Add(newData);
                }
            }
        }

        private void trilaterate(DroneDataSet dds)
        {
            Coordinate output;

            // full path of python interpreter  
            string python = @"C:\Python\python.exe";

            // python app to call  
            string myPythonApp = @"C:\Python\trilaterate.py";

            ProcessStartInfo start = new ProcessStartInfo();
            start.FileName = python;//cmd is full path to python.exe
            start.Arguments = myPythonApp + " " + dds.ToString();//args is path to .py file and any cmd line args
            start.UseShellExecute = false;
            start.RedirectStandardOutput = true;
            using (Process process = Process.Start(start))
            {
                using (StreamReader reader = process.StandardOutput)
                {
                    string result = reader.ReadToEnd();
                    string[] words = result.Split(" ".ToArray());
                    string[] secondwords = words[1].Split("/".ToArray());
                    try
                    {
                        double lat = double.Parse(words[0]);
                        double lng = double.Parse(secondwords[0]);

                        output = new Coordinate(new LatLng(lat, lng), consolidationConstant);

                        addNewCoordinate(output);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }

        private void addNewCoordinate(Coordinate newCoord)
        {
            foreach (Coordinate c in activeLocations)
            {
                if (distanceBetween(c, newCoord) < consolidationConstant)
                {
                    averageCoordinates(c, newCoord);
                    return;
                }
            }

            activeLocations.Add(newCoord);
        }

        private void averageCoordinates(Coordinate one, Coordinate two)
        {
            one.Accuracy = distanceBetween(one, two);
            one.LatLng.lat = (one.LatLng.lat + two.LatLng.lat)/2;
            one.LatLng.lng = (one.LatLng.lng + two.LatLng.lng)/2;
            
        }

        private double distanceBetween(Coordinate one, Coordinate two)
        {

            var sCoord = new GeoCoordinate(one.LatLng.lat, one.LatLng.lng);
            var eCoord = new GeoCoordinate(two.LatLng.lat, two.LatLng.lng);

            return sCoord.GetDistanceTo(eCoord);

        }

        private void seedOriginalData()
        {

            LatLng pos1 = new LatLng(37.06053496780209, -76.49047845974565);
            LatLng pos2 = new LatLng(37.06219398671905, -76.48933410469908);
            LatLng pos3 = new LatLng(37.06625769597734, -76.49276732699946);

            activeLocations.Add(new Coordinate(pos1, 10));
            activeLocations.Add(new Coordinate(pos2, 5));
            activeLocations.Add(new Coordinate(pos3, 7));

            droneData.Add(
                new DroneData(
                    "swxedcrfvtgb"
                    , 114.207460633365
                    , new LatLng(37.06431008815, -76.49267291623)
                    , "1"));
            droneData.Add(
                new DroneData(
                    "qzawsxedcrfvg"
                    , 211.437352892073
                    , new LatLng(37.06425093355, -76.49134212943)
                    , "1"));
        }
    }
}
