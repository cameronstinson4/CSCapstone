namespace WebApplication2.Models
{
    public class LatLng
    {
        public double lat { get; set; }
        public double lng { get; set; }

        public LatLng(double lat, double lng)
        {
            this.lat = lat;
            this.lng = lng;
        }
    }
}