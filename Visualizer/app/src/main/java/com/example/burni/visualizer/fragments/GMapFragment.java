package com.example.burni.visualizer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.burni.visualizer.R;
import com.example.burni.visualizer.datamodels.Coordinate;
import com.example.burni.visualizer.datamodels.SampleData;
import com.example.burni.visualizer.web.WebApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class GMapFragment extends android.app.Fragment implements OnMapReadyCallback {

    private ProgressDialog _dialog;
    private GoogleMap _goolgeMap;
    private List<Marker> _markers;
    private MapFragment _mapFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmap, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //_dialog = ProgressDialog.show(getActivity(), "", "Loading. Please wait...", true);
        _mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        _mapFragment.getMapAsync(this);
        _markers = new ArrayList<>();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //_dialog.hide();
        _goolgeMap = googleMap;

        LatLng marker = new LatLng(37.06053496780209, -76.49047845974565);
        LatLng marker1 = new LatLng(37.06219398671905, -76.48933410469908);
        LatLng marker2 = new LatLng(37.06625769597734, -76.49276732699946);

        _goolgeMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 13));

        _markers.add(_goolgeMap.addMarker(new MarkerOptions().title("Survivor 1").position(marker)));
        _markers.add(_goolgeMap.addMarker(new MarkerOptions().title("Survivor 2").position(marker1)));
        _markers.add(_goolgeMap.addMarker(new MarkerOptions().title("Survivor 3").position(marker2)));

        new HttpRequestTask().execute();

        removeAllMarkers();
        addAllMarkers();
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

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            final String sampleData = WebApiClient.getSampleData();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity().getBaseContext()
                            , sampleData
                            , Toast.LENGTH_LONG).show();
                }
            });

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return sampleData;
        }

        @Override
        protected void onPostExecute(String sampleData) {
            _goolgeMap.clear();
        }
    }
}