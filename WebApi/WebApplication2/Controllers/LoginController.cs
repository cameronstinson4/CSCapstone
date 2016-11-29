using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace WebApplication2.Controllers
{
    public class LoginController : ApiController
    {
        public bool Get([FromUri]string name, [FromUri]string pin)
        {
            if (name == "CNUArea1" && pin == "12345")
            {
                return true;
            }

            return false;
        }
    }
}
