using System;
using System.Device.Location;

namespace WebApplication2.DataModels
{
    public class Coordinate
    {
        public Guid Id { get; set; }

        public LatLng LatLng { get; set; }

        public double Accuracy { get; set; }

        public Coordinate(LatLng latLng, double accuracy)
        {
            this.Id = Guid.NewGuid();
            this.LatLng = latLng;
            this.Accuracy = accuracy;
        
        }
    }
}