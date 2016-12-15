using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication2.DataModels
{
    public class DroneData
    {
        public string DroneId { get; set; }

        public double Distance { get; set; }

        public LatLng LatLng { get; set; }

        public string ScanId { get; set; }

        public string SignalId { get; set;}

        public DroneData(string droneId, double distance, LatLng latLng, string scanId, string signalId)
        {
            DroneId = droneId;
            Distance = distance;
            LatLng = latLng;
            ScanId = scanId;
            SignalId = signalId;
        }
    }
}