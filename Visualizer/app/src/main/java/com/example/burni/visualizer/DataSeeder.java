package com.example.burni.visualizer;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

/**
 * Created by burni on 11/15/2016.
 */

public class DataSeeder {

    //TODO Replace this whole class with data from webapi

    public static void seedLocationData(List<LatLng> locations) {

        LatLng pos1 = new LatLng(37.06053496780209, -76.49047845974565);
        LatLng pos2 = new LatLng(37.06219398671905, -76.48933410469908);
        LatLng pos3 = new LatLng(37.06625769597734, -76.49276732699946);

        locations.add(pos1);
        locations.add(pos2);
        locations.add(pos3);
    }

    public static LatLngBounds seedBoundaryData() {

        return new LatLngBounds(
                  new LatLng(37.05, -76.5)
                , new LatLng(37.08, -76.47)
        );
    }
}
