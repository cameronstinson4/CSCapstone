using System;
using System.Device.Location;

namespace WebApplication2.DataModels
{
    public class Coordinate
    {
        public string Id { get; set; }

        public LatLng LatLng { get; set; }

        public double Accuracy { get; set; }

        public Coordinate(string id, LatLng latLng, double accuracy)
        {
            this.Id = id;
            this.LatLng = latLng;
            this.Accuracy = accuracy;
        
        }
    }
}