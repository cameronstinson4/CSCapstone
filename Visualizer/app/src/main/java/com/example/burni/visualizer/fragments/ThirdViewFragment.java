package com.example.burni.visualizer.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.burni.visualizer.MainActivity;
import com.example.burni.visualizer.R;
import com.example.burni.visualizer.datamodels.LatLngHt;
import com.example.burni.visualizer.web.ResultCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ThirdViewFragment extends android.app.Fragment implements OnMapReadyCallback, ResultCallback {

    private GoogleMap _goolgeMap;
    private List<Marker> _markers;
    private MapFragment _mapFragment;
    private LatLngBounds _boundary;

    final int MARKER_UPDATE_INTERVAL = 5000; /* milliseconds */
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _markers = new ArrayList<>();

        _boundary = ((MainActivity) getActivity()).getBoundary();
        handler.postDelayed(updateMarker, MARKER_UPDATE_INTERVAL);

        return inflater.inflate(R.layout.fragment_gmap, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        _mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        _goolgeMap = googleMap;

        for (int i = 0; i < ((MainActivity) getActivity()).getLocations().size(); i++) {
            _markers.add(_goolgeMap.addMarker(
                    new MarkerOptions().title("Survivor " + i)
                            .position(((MainActivity) getActivity()).getLocations().get(i)._latLng)
                            .snippet(getString(R.string.height) + ": " + ((MainActivity) getActivity()).getLocations().get(i)._ht + " m")));
        }

// Move the camera instantly to Sydney with a zoom of 15.
        _goolgeMap.moveCamera(CameraUpdateFactory.newLatLngBounds(_boundary, 10));


// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(_boundary.getCenter())      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        _goolgeMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (ActivityCompat.checkSelfPermission(getActivity()
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity()
                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            _goolgeMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onResult(JSONArray array) {
        if (_goolgeMap != null) {
            onMapReady(_goolgeMap);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(updateMarker);
    }
    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(updateMarker, MARKER_UPDATE_INTERVAL);
    }


    Runnable updateMarker = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < ((MainActivity) getActivity()).getLocations().size(); i++) {
                _markers.add(_goolgeMap.addMarker(
                        new MarkerOptions().title("Survivor " + i)
                                .position(((MainActivity) getActivity()).getLocations().get(i)._latLng)
                                .snippet(getString(R.string.height) + ": " + ((MainActivity) getActivity()).getLocations().get(i)._ht + " m")));
            }
            handler.postDelayed(this, MARKER_UPDATE_INTERVAL);
        }
    };
}