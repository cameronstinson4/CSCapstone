package com.example.burni.visualizer.fragments;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;

public class GMapFragment extends MapFragmentBase{

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);

        _goolgeMap.getUiSettings().setTiltGesturesEnabled(false);

        _goolgeMap.moveCamera(CameraUpdateFactory.newLatLngBounds(_boundary, 10));
    }
}