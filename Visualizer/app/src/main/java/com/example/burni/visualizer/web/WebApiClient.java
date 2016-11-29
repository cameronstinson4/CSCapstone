package com.example.burni.visualizer.web;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by burni on 10/17/2016.
 */

public class WebApiClient {
    public static String getSampleData() {
        //todo Get the sample data from api
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.129.28.255:56679").build();
        Response response = null;
        String out = "FAILURE"; //todo Add FAILURE
        try {
            response = client.newCall(request).execute();
            out = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
    public static void alertServerSurviviorFound(LatLng gpsPosition) {
        //todo Alert the server that a survivor was found including gps coordinates


    }
}
