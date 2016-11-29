package com.example.burni.visualizer.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.burni.visualizer.MainActivity;
import com.example.burni.visualizer.R;
import com.example.burni.visualizer.datamodels.SignalCoordinate;
import com.example.burni.visualizer.web.ResultCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by burni on 11/29/2016.
 */

public abstract class MapFragmentBase extends Fragment implements OnMapReadyCallback, ResultCallback {

    final int MARKER_UPDATE_INTERVAL = 1000;

    protected GoogleMap _goolgeMap;
    protected List<Marker> _markers;
    protected List<Circle> _accuracyCircles;
    protected LatLngBounds _boundary;
    protected Handler _handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _markers = new ArrayList<>();
        _accuracyCircles = new ArrayList<>();
        _boundary = ((MainActivity) getActivity()).getBoundary();
        _handler.postDelayed(updateMarker, MARKER_UPDATE_INTERVAL);

        return inflater.inflate(R.layout.fragment_gmap, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        _goolgeMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            _goolgeMap.setMyLocationEnabled(true);
        }

        _goolgeMap.moveCamera(CameraUpdateFactory.newLatLngBounds(_boundary, 10));
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
        _handler.removeCallbacks(updateMarker);
    }
    @Override
    public void onResume() {
        super.onResume();
        _handler.postDelayed(updateMarker, MARKER_UPDATE_INTERVAL);
    }

    Runnable updateMarker = new Runnable() {
        @Override
        public void run() {

            List<SignalCoordinate> coords = ((MainActivity) getActivity()).getLocations();

            _goolgeMap.clear();

            for (int i = 0; i < ((MainActivity) getActivity()).getLocations().size(); i++) {

                _accuracyCircles.add( _goolgeMap.addCircle(new CircleOptions()
                        .center(coords.get(i).getLatLngHt().getLatLng())
                        .radius(coords.get(i).getAccuracy())
                        .fillColor(Color.BLUE)
                        .strokeColor(Color.RED)
                        .strokeWidth(8)));

                _markers.add(_goolgeMap.addMarker(
                        new MarkerOptions().title(getString(R.string.signal) + " " + i)
                                .position(coords.get(i).getLatLngHt().getLatLng())
                                .snippet(getString(R.string.height) + ": " + coords.get(i).getLatLngHt().ht + " m")));
            }
            _handler.postDelayed(this, MARKER_UPDATE_INTERVAL);
        }
    };
}
