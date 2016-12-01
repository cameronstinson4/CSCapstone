package com.example.burni.visualizer.datamodels;

public class SignalCoordinate {

    private String _id;
    private LatLngHt _latLngHt;
    private double _accuracy; //the accuracy in radial meters

    public SignalCoordinate(String Id, LatLngHt latLngHt, double accuracy) {
        _id = Id;
        _latLngHt = latLngHt;
        _accuracy = accuracy;
    }

    public String getIdentifier() {

        return _id;
    }

    public void setIdentifier(String _identifier) {
        this._id = _identifier;
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
        if (!_id.equals(that._id)) return false;
        return _latLngHt.equals(that._latLngHt);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = _id.hashCode();
        result = 31 * result + _latLngHt.hashCode();
        temp = Double.doubleToLongBits(_accuracy);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public static class SignalCoordinateBuilder {

        private String _identifier;
        private LatLngHt _latLngHt;
        private Double _accuracy;

        public void setIdentifier(String id) {
            _identifier = id;
        }
        public void setLatLngHt(LatLngHt latLngHt) {
            _latLngHt = latLngHt;
        }
        public void setAccuracy(double accuracy) {
            _accuracy = new Double(accuracy);
        }

        public SignalCoordinate build() {
            if (_identifier == null || _latLngHt == null || _accuracy == null) {
                throw new IllegalArgumentException("Not all required fields have values to build this object");
            }
            else return new SignalCoordinate(_identifier, _latLngHt, _accuracy);
        }
    }
}