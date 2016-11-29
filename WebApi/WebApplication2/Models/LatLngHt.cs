namespace WebApplication2.Models
{
    public class LatLngHt
    {
        public double lat { get; set; }
        public double lng { get; set; }
        public double ht { get; set; }

        public LatLngHt(double lat, double lng, double ht)
        {
            this.lat = lat;
            this.lng = lng;
            this.ht = ht;
        }
    }
}