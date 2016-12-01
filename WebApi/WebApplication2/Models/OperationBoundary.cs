using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication2.Models
{
    public class OperationBoundary
    {
        public OperationBoundary(LatLngHt ne, LatLngHt sw)
        {
            Northeast = ne;
            Southwest = sw;
        }

        public LatLngHt Northeast { get; set; }
        public LatLngHt Southwest { get; set; }
    }
}