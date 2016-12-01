using System.Collections.Generic;
using System.Web.Http;
using WebApplication2.Models;
using System.Device;
using System.Device.Location;

namespace WebApplication2.Controllers
{

    public class SampleDataController : ApiController
    {
        public const int consolidationConstant = 5;

        public static List<Coordinate> activeLocations = new List<Coordinate>();

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

        public IHttpActionResult Post([FromBody]Coordinate value)
        {
            if (value != null)
            {
                activeLocations.Add(value);
                consolidateLocations();
                return Ok();
            }
            else
            {
                return BadRequest();
            }


        }
        /*
        public IHttpActionResult Post([FromBody]DroneData value)
        {
            if (value != null)
            {
                return Ok();
            }
            else
            {
                return BadRequest();
            }

            //do stuff with data

        }*/

        private void calculateLocations()
        {

        }

        //this doesnt workmwell
        private void consolidateLocations()
        {
            foreach (Coordinate c in activeLocations)
            {
                foreach (Coordinate j in activeLocations)
                {
                    if (c.Equals(j))
                    {
                        break;
                    }
                    if (distanceBetween(c,j) < consolidationConstant)
                    {
                        c.LatLngHt.lat = (c.LatLngHt.lat + j.LatLngHt.lat)/2;
                        c.LatLngHt.lng = (c.LatLngHt.lng + j.LatLngHt.lng)/2;
                        c.Accuracy = c.Accuracy > distanceBetween(c, j) + j.Accuracy ? c.Accuracy : distanceBetween(c, j) + j.Accuracy;
                        activeLocations.Remove(j);
                        break;
                    }        
                }
            }
        }

        private double distanceBetween(Coordinate one, Coordinate two)
        {

            var sCoord = new GeoCoordinate(one.LatLngHt.lat, one.LatLngHt.lng);
            var eCoord = new GeoCoordinate(two.LatLngHt.lat, two.LatLngHt.lng);

            return sCoord.GetDistanceTo(eCoord);
 
        }

        private void seedOriginalData()
        {

            LatLngHt pos1 = new LatLngHt(37.06053496780209, -76.49047845974565, 1);
            LatLngHt pos2 = new LatLngHt(37.06219398671905, -76.48933410469908, 5);
            LatLngHt pos3 = new LatLngHt(37.06625769597734, -76.49276732699946, 0);

            activeLocations.Add(new Coordinate(pos1, 10));
            activeLocations.Add(new Coordinate(pos2, 5));
            activeLocations.Add(new Coordinate(pos3, 7));


        }
    }
}
