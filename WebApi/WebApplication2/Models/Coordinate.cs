using System;

namespace WebApplication2.Models
{
    public class Coordinate
    {
        public Guid id { get; set; }

        public LatLngHt latLngHt { get; set; }

        public double accuracy { get; set; }

        public Coordinate(LatLngHt latLngHt, double accuracy)
        {
            this.id = Guid.NewGuid();
            this.latLngHt = latLngHt;
            this.accuracy = accuracy;
        
        }
    }
}