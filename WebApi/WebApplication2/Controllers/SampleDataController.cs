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
        public SampleData sampleData = new SampleData()
        {
            Coordinates = new Coordinate[] {
                new Coordinate(),
                new Coordinate()
            }
        };

        public SampleData Get()
        {
            return sampleData;
        }
    }
}
