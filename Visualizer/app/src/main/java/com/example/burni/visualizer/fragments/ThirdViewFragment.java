package com.example.burni.visualizer.fragments;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.burni.visualizer.R;
        import com.example.burni.visualizer.web.ResultCallback;
        import com.example.burni.visualizer.web.RetrieveJsonArrayTask;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import org.json.JSONArray;

        import java.util.ArrayList;
        import java.util.List;

public class ThirdViewFragment extends android.app.Fragment implements OnMapReadyCallback, ResultCallback {


    private ProgressDialog _dialog;
    private GoogleMap _goolgeMap;
    private List<Marker> _markers;
    private MapFragment _mapFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _dialog = ProgressDialog.show(getActivity(), "", "Loading. Please wait...", true);
        _dialog.show();
        new RetrieveJsonArrayTask(getActivity(), this).execute("http://localhost:56680/api/sampledata");


        return inflater.inflate(R.layout.fragment_gmap, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        _mapFragment.getMapAsync(this);
        _markers = new ArrayList<>();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng SYDNEY = new LatLng(-33.88,151.21);
        LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);

        _dialog.hide();
        _goolgeMap = googleMap;

// Move the camera instantly to Sydney with a zoom of 15.
        _goolgeMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));

// Zoom in, animating the camera.
        _goolgeMap.animateCamera(CameraUpdateFactory.zoomIn());

// Zoom out to zoom level 10, animating with a duration of 2 seconds.
        _goolgeMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(89)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        _goolgeMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void addAllMarkers() {
        for (Marker m : _markers){
            m.setVisible(true);
        }
    }

    private void removeAllMarkers() {
        for (Marker m : _markers) {
            m.setVisible(false);
        }
    }

    @Override
    public void onResult(JSONArray array) {
        if (_goolgeMap != null) {
            onMapReady(_goolgeMap);
        }
    }
}