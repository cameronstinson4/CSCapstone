package com.example.burni.visualizer;

import com.example.burni.visualizer.datamodels.LatLngHt;
import com.example.burni.visualizer.datamodels.SignalCoordinate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by burni on 11/15/2016.
 */

public class MockServer {

    //TODO Replace this whole class with data from webapi

    public static LatLngHt pos1 = new LatLngHt(37.06053496780209, -76.49047845974565, 1);
    public static LatLngHt pos2 = new LatLngHt(37.06219398671905, -76.48933410469908, 5);
    public static LatLngHt pos3 = new LatLngHt(37.06625769597734, -76.49276732699946, 0);


    public static void seedLocationData(List<LatLngHt> locations) {

        locations.add(pos1);
        locations.add(pos2);
        locations.add(pos3);
    }

    public static LatLngBounds getBoundaries() {

        return new LatLngBounds(
                  new LatLng(37.05, -76.5)
                , new LatLng(37.08, -76.47)
        );
    }

    public static boolean VerifyLoginInformation()
    {
        return true;
    }

    public static ArrayList<SignalCoordinate> updateLocations() {
        double rand = Math.random()/1000;
        ArrayList<SignalCoordinate> shit = new ArrayList<>();

        shit.add(new SignalCoordinate("1", new LatLngHt(pos1.lat - rand, pos1.lng -rand, pos1.ht -rand), 2));
        shit.add(new SignalCoordinate("2", new LatLngHt(pos2.lat - rand, pos2.lng -rand, pos2.ht -rand), 5));
        shit.add(new SignalCoordinate("3", new LatLngHt(pos3.lat - rand, pos3.lng -rand, pos3.ht -rand), 3));

        return shit;
    }
}
