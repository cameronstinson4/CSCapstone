package com.example.burni.visualizer;

import com.example.burni.visualizer.datamodels.LatLngHt;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

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

    public static ArrayList<LatLngHt> updateLocations() {
        double rand = Math.random()/1000;
        ArrayList<LatLngHt> shit = new ArrayList<>();
        shit.add(new LatLngHt(pos1._lat - rand, pos1._lng -rand, pos1._ht -rand));
        shit.add(new LatLngHt(pos2._lat - rand, pos2._lng -rand, pos2._ht -rand));
        shit.add(new LatLngHt(pos3._lat - rand, pos3._lng -rand, pos3._ht -rand));

        return shit;
    }
}
