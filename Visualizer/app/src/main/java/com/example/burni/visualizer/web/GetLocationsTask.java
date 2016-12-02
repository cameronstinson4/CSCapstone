package com.example.burni.visualizer.web;

import com.example.burni.visualizer.datamodels.SignalCoordinate;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by burni on 12/1/2016.
 */

public class GetLocationsTask extends GetJsonDataTaskBase {
    public GetLocationsTask(ResultCallback call) {
        super(call);
    }


    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        call.onResult(parseJsonData(result));
    }

    private ArrayList<SignalCoordinate> parseJsonData(JSONObject json) {

        if (json == null) {
            return null;
        }

        JSONArray array = null;
        try {
            array = json.getJSONArray("Coordinates");
        } catch (JSONException e) {

            e.printStackTrace();
        }

        ArrayList<SignalCoordinate> out = new ArrayList<>();

        for (int i = 0; i < array.length(); i ++) {
            try {
                JSONObject obj = array.getJSONObject(i);
                SignalCoordinate.SignalCoordinateBuilder builder = new SignalCoordinate.SignalCoordinateBuilder();


                builder.setIdentifier((String) obj.get("Id"));
                builder.setAccuracy((double) obj.get("Accuracy"));

                obj = obj.getJSONObject("LatLng");

                builder.setLatLng(
                        new LatLng((double) obj.get("lat"), (double) obj.get("lng")));

                out.add(builder.build());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return out;
    }
}
