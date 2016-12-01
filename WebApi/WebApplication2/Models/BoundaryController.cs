using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace WebApplication2.Models
{
    public class BoundaryController : ApiController
    {

        public static OperationBoundary boundary;

        // GET: api/Boundary
        public OperationBoundary Get()
        {
            if (boundary == null)
            {
                seedBoundary();
            }

            return boundary;
        }

        private void seedBoundary()
        {
            boundary = new OperationBoundary(new LatLngHt(37.05, -76.5, 0), new LatLngHt(37.08, -76.47, 0));
        }
    }
}
