package com.example.burni.visualizer.datamodels;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by burni on 11/26/2016.
 */

public class LatLngHt {

    public double lat;
    public double lng;
    public double ht;

    public LatLngHt(double lat, double lng, double ht) {

        this.lat = lat;
        this.lng = lng;
        this.ht = ht;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LatLngHt) {
            if (       ((LatLngHt) o).lat == lat
                    && ((LatLngHt) o).lng == lng
                    && ((LatLngHt) o).ht == ht) {

                return true;
            }
        }
        return false;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }
}
