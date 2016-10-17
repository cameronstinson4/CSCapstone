package com.example.burni.visualizer.datamodels;

public class Coordinate
{
    public double Latitude;
    public double Longitude;

    public Coordinate() {}

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}