package com.example.burni.visualizer.fragments;

import android.widget.Toast;

import com.example.burni.visualizer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;

public class ThirdViewFragment extends MapFragmentBase {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);

        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.tilt_help), Toast.LENGTH_LONG).show();

    }

    private Marker getNorthernmostMarker() {

        Marker out = _markers.get(0);

        for (Marker m: _markers) {
            if (m.getPosition().latitude > out.getPosition().latitude) {
                out = m;
            }
        }

        return out;
    }
}