package com.example.burni.visualizer.web;

import com.example.burni.visualizer.datamodels.LatLngHt;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by burni on 12/1/2016.
 */

public class GetBoundaryTask extends GetJsonDataTaskBase {
    public GetBoundaryTask(ResultCallback call) {
        super(call);
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        call.onResult(parseBoundaryData(result));
    }

    private LatLngBounds parseBoundaryData(JSONObject object) {

        if (object == null) {
            return null;
        }

        LatLngBounds bounds = null;

        try {
            JSONObject ne = object.getJSONObject("Northeast");
            JSONObject sw = object.getJSONObject("Southwest");

            LatLngHt Northeast = new LatLngHt((double) ne.get("lat"), (double) ne.get("lng"), (double) ne.get("ht"));
            LatLngHt Southwest = new LatLngHt((double) sw.get("lat"), (double) sw.get("lng"), (double) sw.get("ht"));

            bounds = new LatLngBounds(Northeast.getLatLng(), Southwest.getLatLng());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bounds;
    }
}
