package com.bartproject.app.activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import com.bartproject.app.R;
import com.bartproject.app.fragment.FavoriteStationsGridFragment;
import com.bartproject.app.fragment.NearestStationFragment;
import com.bartproject.app.model.Station;
import com.bartproject.app.model.StationsResponse;
import com.bartproject.app.network.GetStationsRequest;
import com.bartproject.app.util.StationsUtil;
import com.bartproject.app.util.Util;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import android.app.FragmentManager;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;



public class MainActivity extends BaseActivity implements
        FavoriteStationsGridFragment.OnDestinationSelectedListener, GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

    // Global constants

    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;

    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 15;

    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;

    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 10;

    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;

    private static final String TAG = MainActivity.class.getSimpleName();

    // Define an object that holds accuracy and frequency parameters
    LocationRequest mLocationRequest;

    // Define the location client
    LocationClient mLocationClient;

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private List<Station> stationsList;
    private Station closestStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup action bar, hide title & logo
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        // Read the list of stations from file and cache in memory
        stationsList = StationsUtil.readStations(this);

        if (savedInstanceState == null) {
            // Add the three fragments for the main screen
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_map, MapFragment.newInstance(getGoogleMapOptions()))
                    .add(R.id.fragment_container_middle, new NearestStationFragment())
                    .add(R.id.fragment_container_bottom, new FavoriteStationsGridFragment())
                    .commit();
        }

        // Create the LocationRequest object:
        setupLocationUpdateParameters();

        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        mLocationClient = new LocationClient(this, this, this);

    }

    private void setupLocationUpdateParameters() {
        // Location Services allows you to control the interval between updates and the
        // location accuracy you want, by setting the values in a LocationRequest object
        // and then sending this object as part of your request to start updates

        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient.connect();

        setupGoogleMaps();
    }

    private void setupGoogleMaps() {
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragment_container_map)).getMap();
        if (map != null) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
        } else {
            Log.e(TAG, "MAPS COULD NOT BE SETUP - Is Google Play Services missing?");
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
        // Handle action bar item clicks here.

        switch (item.getItemId()) {
            case R.id.action_edit_favorites:
                Intent intent = new Intent(this, FavoriteStationActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_fetch_stations:
                fetchStations();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Called when the Activity is no longer visible at all.
     * Stop updates and disconnect.
     */
    @Override
    protected void onStop() {
        // If the client is connected
        if (mLocationClient.isConnected()) {
            /*
             * Remove location updates for a listener.
             * The current Activity is the listener, so
             * the argument is "this".
             */
            mLocationClient.removeLocationUpdates(this);
        }
        /*
         * After disconnect() is called, the client is
         * considered "dead".
         */
        mLocationClient.disconnect();
        super.onStop();
    }

    @Override
    public void onDestinationSelected(Station destination) {
        // This method is called when a destination station has been selected.

        Toast.makeText(this, destination.getName() + " was selected!", Toast.LENGTH_LONG ).show();
        FragmentManager fm = getFragmentManager();

        // Create new fragment for middle area
        NearestStationFragment newMiddleFragment = NearestStationFragment.newInstance(closestStation, destination);

        // Hide favorites grid fragment
        FavoriteStationsGridFragment bottomFrag = (FavoriteStationsGridFragment) fm.findFragmentById(
                R.id.fragment_container_bottom);

        fm.beginTransaction()
                .replace(R.id.fragment_container_middle, newMiddleFragment)
                .remove(bottomFrag)
                .addToBackStack(null)
                .commit();

    }



    // This func gives access to the middle fragment
    // This function will be called once we get the location of the GPS co-ords
    // This method is for TESTING - remove this later
    public void setStationsList(List<Station> stations) {
        NearestStationFragment f = (NearestStationFragment)
                getFragmentManager().findFragmentById(R.id.fragment_container_middle);

        // Generatoe a random number
        int stationIndex = (int) (Math.random() * stations.size());

        // Select a random station from the list of stations
        Station station = stations.get(stationIndex);
        closestStation = station;
        f.setStation(station);

//        Station destination = new Station();
//        destination.setAbbr("RICH");
//        f.setDestinationStation(station, destination);
    }

    /*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

        // Start periodic updates
        mLocationClient.requestLocationUpdates(mLocationRequest, this);
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection failed.", Toast.LENGTH_LONG).show();
    }


    // The callback method that receives location updates
    @Override
    public void onLocationChanged(Location location) {
        // Report to the UI that the location was updated
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Log.e(TAG, msg);

        // Calculate closest station
        closestStation = Util.getClosestStation(location, stationsList);
        setClosestStation(closestStation);
    }

    public void setClosestStation(Station closestStation) {
        // Update fragment with closest station
        NearestStationFragment f = (NearestStationFragment)
                getFragmentManager().findFragmentById(R.id.fragment_container_middle);

        f.setStation(closestStation);

        // Update Map
        LatLng coordinates = new LatLng(closestStation.getGtfs_latitude(),
                closestStation.getGtfs_longitude());
        GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_container_map)).getMap();
        if (map != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 16));
        } else {
            Log.e(TAG, "MAPS COULD NOT BE ANIMATED - Is Google Play Services missing?");
        }
    }

    public GoogleMapOptions getGoogleMapOptions() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(37.741610, -122.313299))
                .zoom(8)
                .build();

        GoogleMapOptions options = new GoogleMapOptions();

        options.camera(cameraPosition)
                .mapType(GoogleMap.MAP_TYPE_NORMAL)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(true)
                .zoomControlsEnabled(false);

        return options;
    }

    /**
     * This method is here ONLY for testing purposes.  IT WILL BE DELETED!!
     */
    public void fetchStations() {
        // Create a request object
        GetStationsRequest request = new GetStationsRequest();

        // Create a unique cache key
        String cacheKey = request.createCacheKey();

        // Execute the network request
        // Set the cache duration for 10 seconds, although in reality this should be closer to one month
        getSpiceManager().execute(request, cacheKey,
                DurationInMillis.ONE_SECOND * 10, new GetStationsRequestListener());
    }

    /**
     * This request listener is here ONLY for testing purposes.  IT WILL BE DELETED!!
     */
    private class GetStationsRequestListener implements RequestListener<StationsResponse> {

        /**
         * ONLY for testing purposes
         */
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(TAG, "Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

            Toast.makeText(MainActivity.this, "Failed - see Logcat", Toast.LENGTH_SHORT).show();
        }

        /**
         * ONLY for testing purposes
         */
        @Override
        public void onRequestSuccess(StationsResponse stationsResponse) {
            Log.e(TAG, "Fetching stations successful");

            for (Station s : stationsResponse.getStations()) {
                Log.e(TAG, s.getAbbr() + " - " + s.getName());
            }

            setStationsList(stationsResponse.getStations());
        }
    }
}
