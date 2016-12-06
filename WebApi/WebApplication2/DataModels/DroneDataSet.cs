using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication2.DataModels
{
    public class DroneDataSet
    {
        public List<DroneData> droneDataSet;

        public DroneDataSet(params DroneData[] input )
        {
            
            if (input.Length < 3)
            {
                throw new Exception("Must have atleast 3 input drone datas");
            }

            foreach (DroneData dd in input)
            {
                if (dd.ScanId != input[0].ScanId)
                {
                    throw new Exception("All Drone Data points must have the same scan Id");
                }
            }

            droneDataSet = new List<DroneData>();
            droneDataSet.AddRange(input);
        }
        
        override public string ToString()
        {
            string output = "";

            foreach(DroneData dd in droneDataSet)
            {
                output = $" {output} {dd.LatLng.lat.ToString("F7")} {dd.LatLng.lng.ToString("F7")} {(dd.Distance/1000).ToString("F9")}";
            }

            return output.Trim();
        }
    }

    public class DroneDataSetBuilder
    {
        public List<DroneData> droneDataSet = new List<DroneData>();

        public void addDroneData(params DroneData[] input)
        {
            if (droneDataSet.Count > 0)
            {
                foreach (DroneData dd in input)
                {
                    if (dd.ScanId != droneDataSet[0].ScanId)
                    {
                        throw new Exception("All Drone Data points must have the same scan Id");
                    }
                }
            }

            foreach (DroneData dd in input)
            {
                if (dd.ScanId != input[0].ScanId)
                {
                    throw new Exception("All Drone Data points must have the same scan Id");
                }
            }

            droneDataSet.AddRange(input);
        }

        public bool canBuild()
        {
            if (droneDataSet.Count < 3)
            {
                return false;
            }
            foreach (DroneData dd in droneDataSet)
            {
                if (dd.ScanId != droneDataSet[0].ScanId)
                {
                    throw new Exception("All Drone Data points must have the same scan Id");
                }
            }

            return true;
        }

        public DroneDataSet build()
        {
            if (canBuild())
            {
                return new DroneDataSet(droneDataSet.ToArray());
            }
            throw new Exception("Can't build");
        }
    }
}