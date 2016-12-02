package com.example.burni.visualizer.web;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.burni.visualizer.MainActivity;
import com.example.burni.visualizer.R;
import com.google.android.gms.maps.model.LatLng;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by burni on 10/17/2016.
 */

public class AlertServerTask extends AsyncTask<String, Void, Void> {


    private MainActivity _parentActivity;
    private LocationManager _locationManager;
    private LocationListener _locationListener;
    private Location _location;

    public AlertServerTask(MainActivity activity) {
        _parentActivity = activity;
        _locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        _locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                _location = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            public void onProviderEnabled(String provider) {

            }

            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                _parentActivity.startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(_parentActivity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(_parentActivity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        _locationManager.requestLocationUpdates("gps", 5000, 0, _locationListener);
    }

    @Override
    protected Void doInBackground(String... params) {

        if (ActivityCompat.checkSelfPermission(_parentActivity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(_parentActivity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }


        while (!Thread.interrupted()) {
            if (_location != null) {
                LatLng out = new LatLng(_location.getLatitude(), _location.getLongitude());
                //WebApiClient.alertServerSurviviorFound(out);

                //Alert user that they are broadcasting a survivor location
                _parentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_parentActivity.getBaseContext()
                                , _parentActivity.getString(R.string.alert_server) + "\n" + _location.getLatitude() + " " + _location.getLongitude()
                                , Toast.LENGTH_LONG).show();
                    }
                });

            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            if (!_parentActivity.isBroadcasting()) {
                _locationManager.removeUpdates(_locationListener);
                _locationListener = null;
                _locationManager = null;
                break;
            }
        }
        return null;
    }

    protected void onProgressUpdate(String... progress) {

    }

    protected void onPostExecute(String... result) {

    }

    public void stopUpdates() {

        if (ActivityCompat.checkSelfPermission(_parentActivity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(_parentActivity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        _locationManager.removeUpdates(_locationListener);
    }
}