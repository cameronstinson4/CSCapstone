package com.example.burni.visualizer.datamodels;

public class SignalCoordinate {

    private String _identifier;
    private LatLngHt _latLngHt;
    private double _accuracy; //the accuracy in radial meters

    public SignalCoordinate(String Id, LatLngHt latLngHt, double accuracy) {
        _identifier = Id;
        _latLngHt = latLngHt;
        _accuracy = accuracy;
    }

    public String getIdentifier() {

        return _identifier;
    }

    public void setIdentifier(String _identifier) {
        this._identifier = _identifier;
    }

    public LatLngHt getLatLngHt() {
        return _latLngHt;
    }

    public void setLatLngHt(LatLngHt _latLngHt) {
        this._latLngHt = _latLngHt;
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
        if (!_identifier.equals(that._identifier)) return false;
        return _latLngHt.equals(that._latLngHt);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = _identifier.hashCode();
        result = 31 * result + _latLngHt.hashCode();
        temp = Double.doubleToLongBits(_accuracy);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}