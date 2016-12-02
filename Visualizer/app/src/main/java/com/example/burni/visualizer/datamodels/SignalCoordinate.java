package com.example.burni.visualizer.datamodels;

import com.google.android.gms.maps.model.LatLng;

public class SignalCoordinate {

    private String _id;
    private LatLng _latLng;
    private double _accuracy; //the accuracy in radial meters

    public SignalCoordinate(String Id, LatLng latLng, double accuracy) {
        _id = Id;
        _latLng = latLng;
        _accuracy = accuracy;
    }

    public String getIdentifier() {

        return _id;
    }

    public void setIdentifier(String id) {
        this._id = id;
    }

    public LatLng getLatLng() {
        return _latLng;
    }

    public void setLatLng(LatLng _latLng) {
        this._latLng = _latLng;
    }

    public double getAccuracy() {
        return _accuracy;
    }

    public void setAccuracy(double _accuracy) {
        this._accuracy = _accuracy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignalCoordinate)) return false;

        SignalCoordinate that = (SignalCoordinate) o;

        if (Double.compare(that._accuracy, _accuracy) != 0) return false;
        if (!_id.equals(that._id)) return false;
        return _latLng.equals(that._latLng);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = _id.hashCode();
        result = 31 * result + _latLng.hashCode();
        temp = Double.doubleToLongBits(_accuracy);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public static class SignalCoordinateBuilder {

        private String _identifier;
        private LatLng _latLng;
        private Double _accuracy;

        public void setIdentifier(String id) {
            _identifier = id;
        }
        public void setLatLng(LatLng latLng) {
            _latLng = latLng;
        }
        public void setAccuracy(double accuracy) {
            _accuracy = new Double(accuracy);
        }

        public SignalCoordinate build() {
            if (_identifier == null || _latLng == null || _accuracy == null) {
                throw new IllegalArgumentException("Not all required fields have values to build this object");
            }
            else return new SignalCoordinate(_identifier, _latLng, _accuracy);
        }
    }
}