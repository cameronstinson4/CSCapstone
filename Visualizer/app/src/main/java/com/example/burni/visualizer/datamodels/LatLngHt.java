package com.example.burni.visualizer.datamodels;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by burni on 11/26/2016.
 */

public class LatLngHt {

    public double _lat;
    public double _lng;
    public LatLng _latLng;
    public double _ht;

    public LatLngHt(double lat, double lng, double ht) {

        _lat = lat;
        _lng = lng;
        _latLng = new LatLng(lat, lng);
        _ht = ht;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LatLngHt) {
            if (       ((LatLngHt) o)._lat == _lat
                    && ((LatLngHt) o)._lng == _lng
                    && ((LatLngHt) o)._ht == _ht) {

                return true;
            }
        }
        return false;
    }
}
