package com.example.burni.visualizer.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.burni.visualizer.MainActivity;
import com.example.burni.visualizer.R;
import com.example.burni.visualizer.web.ResultCallback;
import com.example.burni.visualizer.web.RetrieveJsonArrayTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class GMapFragment extends android.app.Fragment implements OnMapReadyCallback, ResultCallback {

    private ProgressDialog _dialog;
    private GoogleMap _goolgeMap;
    private List<LatLng> _locations;
    private List<Marker> _markers;
    private MapFragment _mapFragment;
    private LatLngBounds _boundary;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _markers = new ArrayList<Marker>();
        _locations = ((MainActivity) getActivity()).getLocations();
        _boundary = ((MainActivity) getActivity()).getBoundary();


        _dialog = ProgressDialog.show(getActivity(), "", "Loading. Please wait...", true);
        _dialog.show();
        new RetrieveJsonArrayTask(getActivity(), this).execute("http://localhost:56680/api/sampledata");


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

        _dialog.hide();
        _goolgeMap = googleMap;

        for (int i = 0; i < _locations.size(); i++) {
            _markers.add(_goolgeMap.addMarker(new MarkerOptions().title("Survivor " + i).position(_locations.get(i))));

        }

        _goolgeMap.moveCamera(CameraUpdateFactory.newLatLngBounds(_boundary, 10));
    }

    private LatLng averageLocation() {
        double lat = 0;
        double lon = 0;
        for (LatLng l : _locations) {
            lat += l.latitude;
            lon += l.longitude;
        }
        lat = lat / _locations.size();
        lon = lon / _locations.size();

        return new LatLng(lat, lon);
    }

    private void addAllMarkers() {
        for (Marker m : _markers){
            m.setVisible(true);
        }
    }

    private void removeAllMarkers() {
        for (Marker m : _markers) {
            m.setVisible(false);
        }
    }

    @Override
    public void onResult(JSONArray array) {
        if (_goolgeMap != null) {
            onMapReady(_goolgeMap);
        }
    }
}