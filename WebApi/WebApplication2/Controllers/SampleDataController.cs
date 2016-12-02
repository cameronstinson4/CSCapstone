using System.Collections.Generic;
using System.Web.Http;
using WebApplication2.Models;
using System.Device;
using System.Device.Location;
using System.Linq;

namespace WebApplication2.Controllers
{

    public class SampleDataController : ApiController
    {
        public const int consolidationConstant = 5;

        private static List<Coordinate> activeLocations = new List<Coordinate>();
        private static List<DroneData> droneData = new List<DroneData>();
        private static List<DroneDataSet> droneDataSets = new List<DroneDataSet>();

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

        //public IHttpActionResult Post([FromBody]Coordinate value)
        //{
        //    if (value != null)
        //    {
        //        activeLocations.Add(value);
        //        consolidateLocations();
        //        return Ok();
        //    }
        //    else
        //    {
        //        return BadRequest();
        //    }


        //}
        
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

            //do stuff with data

        }

        //this doesnt workmwell
        private void consolidateLocations()
        {
            //foreach (Coordinate c in activeLocations)
            //{
            //    foreach (Coordinate j in activeLocations)
            //    {
            //        if (c.Equals(j))
            //        {
            //            break;
            //        }
            //        if (distanceBetween(c,j) < consolidationConstant)
            //        {
            //            c.LatLngHt.lat = (c.LatLngHt.lat + j.LatLngHt.lat)/2;
            //            c.LatLngHt.lng = (c.LatLngHt.lng + j.LatLngHt.lng)/2;
            //            c.Accuracy = c.Accuracy > distanceBetween(c, j) + j.Accuracy ? c.Accuracy : distanceBetween(c, j) + j.Accuracy;
            //            activeLocations.Remove(j);
            //            break;
            //        }        
            //    }
            //}
        }

        private void processDroneData(DroneData newData)
        {

            if (droneData.Count < 3)
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
                    droneDataSets.Add(builder.build());
                    droneData = droneData.Where(x => x.ScanId != newData.ScanId).ToList<DroneData>(); //removes data that was sent to dronedataset
                    calculateLocation();
                }
                else
                {
                    droneData.Add(newData);
                }
            }
        }

        private void calculateLocation()
        {

        }

        private Coordinate triangulate()
        {

            return null;
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


        }
    }
}
