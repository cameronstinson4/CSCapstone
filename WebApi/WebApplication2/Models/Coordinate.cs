using System;

namespace WebApplication2.Models
{
    public class Coordinate
    {
        public Guid Id { get; set; }

        public LatLngHt LatLngHt { get; set; }

        public double Accuracy { get; set; }

        public Coordinate(LatLngHt latLngHt, double accuracy)
        {
            this.Id = Guid.NewGuid();
            this.LatLngHt = latLngHt;
            this.Accuracy = accuracy;
        
        }
    }
}