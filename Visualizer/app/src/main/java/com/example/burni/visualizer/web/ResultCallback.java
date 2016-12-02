package com.example.burni.visualizer.web;

import com.example.burni.visualizer.datamodels.SignalCoordinate;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by burni on 12/1/2016.
 */

public interface ResultCallback {

    void onResult(List<SignalCoordinate> list);

}
