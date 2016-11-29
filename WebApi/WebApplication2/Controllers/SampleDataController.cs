using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WebApplication2.Models;

namespace WebApplication2.Controllers
{
    public class SampleDataController : ApiController
    {

        public static LatLngHt pos1 = new LatLngHt(37.06053496780209, -76.49047845974565, 1);
        public static LatLngHt pos2 = new LatLngHt(37.06219398671905, -76.48933410469908, 5);
        public static LatLngHt pos3 = new LatLngHt(37.06625769597734, -76.49276732699946, 0);

        public SampleData Get()
        {
            return new SampleData()
            {
                Coordinates = new Coordinate[] {
                     new Coordinate(new LatLngHt(pos1.lat, pos1.lng, pos1.ht), 2),
                     new Coordinate(new LatLngHt(pos2.lat, pos2.lng, pos2.ht), 5),
                     new Coordinate (new LatLngHt(pos3.lat, pos3.lng, pos3.ht), 3)
                }
            };
        }

        public void Post([FromBody]double SNR, [FromBody]int droneId,[FromBody]Coordinate droneLocation)
        {

        }
    }
}
