using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication2.Models
{
    public class Coordinate
    {

        public Coordinate()
        {
            Random random = new Random(DateTime.Now.Millisecond);
            Latitude = random.NextDouble()/1000 + 37;
            Longitude = random.NextDouble()/1000 - 76;
        }

        public double Latitude { get; set; }

        public double Longitude { get; set; }
    }
}