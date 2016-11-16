package com.example.burni.visualizer.fragments;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.burni.visualizer.MainActivity;
        import com.example.burni.visualizer.R;
        import com.example.burni.visualizer.web.ResultCallback;
        import com.example.burni.visualizer.web.RetrieveJsonArrayTask;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.LatLngBounds;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import org.json.JSONArray;

        import java.util.ArrayList;
        import java.util.List;

public class ThirdViewFragment extends android.app.Fragment implements OnMapReadyCallback, ResultCallback {

    private ProgressDialog _dialog;
    private GoogleMap _goolgeMap;
    private List<LatLng> _locations;
    private List<Marker> _markers;
    private MapFragment _mapFragment;
    private LatLngBounds _boundary;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _dialog = ProgressDialog.show(getActivity(), "", "Loading. Please wait...", true);
        _dialog.show();
        _markers = new ArrayList<>();
        _locations = ((MainActivity) getActivity()).getLocations();
        _boundary = ((MainActivity) getActivity()).getBoundary();

        return inflater.inflate(R.layout.fragment_gmap, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        _mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        _dialog.hide();
        _goolgeMap = googleMap;

        for (int i = 0; i < _locations.size(); i++) {
            _markers.add(_goolgeMap.addMarker(new MarkerOptions().title("Survivor " + i).position(_locations.get(i))));

        }

// Move the camera instantly to Sydney with a zoom of 15.
        _goolgeMap.moveCamera(CameraUpdateFactory.newLatLngBounds(_boundary, 10));

// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(_boundary.getCenter())      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        _goolgeMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onResult(JSONArray array) {
        if (_goolgeMap != null) {
            onMapReady(_goolgeMap);
        }
    }
}