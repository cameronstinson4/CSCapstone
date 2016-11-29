package com.example.burni.visualizer.tasks;

import android.os.AsyncTask;

import com.example.burni.visualizer.MainActivity;
import com.example.burni.visualizer.MockServer;
import com.example.burni.visualizer.datamodels.LatLngHt;
import com.example.burni.visualizer.datamodels.SignalCoordinate;
import com.example.burni.visualizer.web.RetrieveJsonArrayTask;

import java.util.ArrayList;

/**
 * Created by burni on 11/27/2016.
 */

public class UpdateDataTask extends AsyncTask<String, Void, Void> {

    private MainActivity _parent;

    public UpdateDataTask(MainActivity parent) {
        _parent = parent;
    }

    @Override
    protected Void doInBackground(String... params) {
        while(!Thread.interrupted()) {


            ArrayList<SignalCoordinate> newLocations = MockServer.updateLocations();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            _parent.addLocations(newLocations);
        }
        return null;
    }
}
