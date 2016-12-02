package com.example.burni.visualizer;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.burni.visualizer.datamodels.SignalCoordinate;
import com.example.burni.visualizer.fragments.ARFragment;
import com.example.burni.visualizer.fragments.AboutFragment;
import com.example.burni.visualizer.fragments.GMapFragment;
import com.example.burni.visualizer.fragments.MainFragment;
import com.example.burni.visualizer.fragments.SettingsFragment;
import com.example.burni.visualizer.fragments.SetupFragment;
import com.example.burni.visualizer.fragments.ThirdViewFragment;
import com.example.burni.visualizer.web.AlertServerTask;
import com.example.burni.visualizer.web.GetBoundaryTask;
import com.example.burni.visualizer.web.GetLocationsTask;
import com.example.burni.visualizer.web.GetJsonDataTaskBase;
import com.example.burni.visualizer.web.ResultCallback;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ResultCallback{

    final int UPDATE_INTERVAL = 5000;

    private boolean _broadcast;
    private MainActivity _this;
    private AlertServerTask _alertingServer;
    private ArrayList<SignalCoordinate> _locations;
    private LatLngBounds _boundary;
    private SetupManager _setupManager;
    private GetJsonDataTaskBase _getSampleData;
    private Handler _getNewDataHandler  = new Handler();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _this = this;
        _broadcast = false;
        _locations = new ArrayList<>();

        _setupManager = new SetupManager(getApplicationContext());

        if (_setupManager.isSetup()) {
            _boundary = SetupManager.getBoundaries();
        }
        else  {
            SetupFragment setFrag = new SetupFragment();
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, setFrag, null)
                    .addToBackStack(null)
                    .commit();
            _boundary = SetupManager.getBoundaries();

        }
        GetJsonDataTaskBase getBoundaryData = new GetBoundaryTask(this);
        getBoundaryData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, _setupManager.getUrl() + "boundary");
        _getSampleData = new GetLocationsTask(_this);
        _getSampleData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, _setupManager.getUrl() + "sampledata");
        _alertingServer = new AlertServerTask(_this);
        _getNewDataHandler.postDelayed(updateLocations, UPDATE_INTERVAL);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(!_setupManager.isSetup()) {
                    return;
                }

                if (!_broadcast) {
                    _broadcast = true;
                    view.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{getResources().getColor(R.color.colorPersonFound)}));

                    Toast.makeText(getApplicationContext(), getString(R.string.alert_server), Toast.LENGTH_SHORT).show();
                    _alertingServer = new AlertServerTask(_this);
                    _alertingServer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                else if (_broadcast) {
                    _broadcast = false;
                    view.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{getResources().getColor(R.color.colorAccent)}));
                    _alertingServer.cancel(true);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();

        int id = item.getItemId();

        if(!getSetupManager().isSetup() && !(id == R.id.nav_about)) {
            fm.beginTransaction().replace(R.id.content_frame, new SetupFragment()).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        if (id == R.id.nav_2dview) {
            fm.beginTransaction().replace(R.id.content_frame, new GMapFragment()).commit();
        } else if (id == R.id.nav_3dview) {
            fm.beginTransaction().replace(R.id.content_frame, new ThirdViewFragment()).commit();
        } else if (id == R.id.nav_arview) {
            fm.beginTransaction().replace(R.id.content_frame, new ARFragment()).commit();
        } else if (id == R.id.nav_setup) {
            fm.beginTransaction().replace(R.id.content_frame, new SetupFragment()).commit();
        } else if (id == R.id.nav_settings) {
            fm.beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
        } else if (id == R.id.nav_about) {
            fm.beginTransaction().replace(R.id.content_frame, new AboutFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onPause() {
        super.onPause();

        _alertingServer.stopUpdates();
        _alertingServer.cancel(true);
        _getNewDataHandler.removeCallbacks(updateLocations);

    }
    @Override
    public void onResume() {
        super.onResume();

        if (isBroadcasting()) {
            _alertingServer = new AlertServerTask(this);
            _alertingServer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        _getNewDataHandler.postDelayed(updateLocations, UPDATE_INTERVAL);

    }
    public List<SignalCoordinate> getLocations() {

        return _locations;
    }
    public boolean isBroadcasting() {
        return _broadcast;
    }
    public void addLocations(List<SignalCoordinate> list) {

        _locations.addAll(list);
    }
    public void setLocations(List<SignalCoordinate> list) {

        _locations = (ArrayList<SignalCoordinate>) list;
    }
    public LatLngBounds getBoundary() {
        return _boundary;
    }
    public SetupManager getSetupManager() {
        return _setupManager;
    }

    @Override
    public void onResult(Object obj) {
        if (obj == null) {
            Toast.makeText(getApplicationContext(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            return;
        }
        if (obj instanceof LatLngBounds) {
            _boundary = (LatLngBounds) obj;

        }
        if (obj instanceof List) {
            if (!((List) obj).isEmpty()) {
                if (((List) obj).get(0) instanceof SignalCoordinate) {
                    setLocations((List<SignalCoordinate>) obj);
                }
            }
        }
    }

    private Runnable updateLocations = new Runnable() {
        @Override
        public void run() {

            _getSampleData = new GetLocationsTask(_this);
            _getSampleData.execute(_setupManager.getUrl() + "sampledata");
            _getNewDataHandler.postDelayed(this, UPDATE_INTERVAL);

        }
    };

}
