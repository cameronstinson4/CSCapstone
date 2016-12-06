package com.example.burni.visualizer.datamodels;

import com.google.android.gms.maps.model.LatLng;

/**
 * This class is used as a data model to house the data points to plot on the maps
 */
public class SignalCoordinate {

    /**
     * String Id, a unique identifier of this object
     */
    private String _id;

    /**
     * The LatLng position of this object
     */
    private LatLng _latLng;

    /**
     * The accuracy of this data point
     */
    private double _accuracy; //the accuracy in radial meters

    public SignalCoordinate(String Id, LatLng latLng, double accuracy) {
        _id = Id;
        _latLng = latLng;
        _accuracy = accuracy;
    }

    /**
     * Gets the Id of this object
     * @return
     */
    public String getIdentifier() {

        return _id;
    }

    /**
     * Sets the ID of this obeject
     * @param id
     */
    public void setIdentifier(String id) {
        this._id = id;
    }

    /**
     * Gets the LatLng of the object
     * @return
     */
    public LatLng getLatLng() {
        return _latLng;
    }

    /**
     * Sets the LatLng value of this object
     * @param _latLng
     */
    public void setLatLng(LatLng _latLng) {
        this._latLng = _latLng;
    }

    /**
     * Gets the accuracy of this object
     * @return
     */
    public double getAccuracy() {
        return _accuracy;
    }

    /**
     * Sets the accuracy of this object
     * @param _accuracy
     */
    public void setAccuracy(double _accuracy) {
        this._accuracy = _accuracy;
    }

    /**
     * Used to compare to obejcts of type signal coordinate
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignalCoordinate)) return false;

        SignalCoordinate that = (SignalCoordinate) o;

        if (Double.compare(that._accuracy, _accuracy) != 0) return false;
        if (!_id.equals(that._id)) return false;
        return _latLng.equals(that._latLng);

    }

    /**
     * Returns a hascode of the object for comparison & stuff idk
     * @return
     */
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

    /**
     * Builder class to assist in creating a signalcoordinate, if all data needed is not known
     */
    public static class SignalCoordinateBuilder {

        private String _identifier;
        private LatLng _latLng;
        private Double _accuracy;

        /**
         * Sets the identifier of the build
         * @param id
         */
        public void setIdentifier(String id) {
            _identifier = id;
        }

        /**
         * Sets the latlng of the builder
         * @param latLng
         */
        public void setLatLng(LatLng latLng) {
            _latLng = latLng;
        }

        /**
         * Sets the accuracy of the builder
         * @param accuracy
         */
        public void setAccuracy(double accuracy) {
            _accuracy = new Double(accuracy);
        }

        /**
         * Attempts to build a new signalcoordinate
         * @return
         */
        public SignalCoordinate build() {
            if (_identifier == null || _latLng == null || _accuracy == null) {
                throw new IllegalArgumentException("Not all required fields have values to build this object");
            }
            else return new SignalCoordinate(_identifier, _latLng, _accuracy);
        }
    }
}