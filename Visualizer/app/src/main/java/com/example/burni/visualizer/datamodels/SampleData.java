package com.example.burni.visualizer.datamodels;

import java.util.List;

public class SampleData
{
    public List<Coordinate> Coordinates;

    public SampleData() {}

    public List<Coordinate> getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        Coordinates = coordinates;
    }
}