package com.bartproject.app;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bartproject.app.model.Station;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import java.util.List;



public class MainActivity extends BaseActivity implements
        FavoriteStationsGridFragment.OnDestinationSelectedListener, GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

    // Global constants

    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;

    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 10;

    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;

    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 5;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Read the list of stations from file and cache in memory
        stationsList = StationsUtil.readStations(this);

        if (savedInstanceState == null) {
            // Add the three fragments for the main screen
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_top, new ApiTesterFragment())
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
        if (id == R.id.action_settings) {
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
        // At the momemnt, we don't need to do anything except show a toast.
        // Eventually, we will launch a new activity that has detailed timings
        // about your trip (from the nearest station to given destination station).

        Toast.makeText(this, destination.getName() + " was selected!", Toast.LENGTH_LONG ).show();
    }

    // This func gives access to the middle fragment
    // This function will be called once we get the location of the GPS co-ords
    // This method is for TESTING - remove this later
    public void setStationsList(List<Station> stations) {
        NearestStationFragment f = (NearestStationFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container_middle);

        int stationIndex = (int) (Math.random() * stations.size());

        Station station = stations.get(stationIndex);
        f.setStation(station);

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


    // Define the callback method that receives location updates
    @Override
    public void onLocationChanged(Location location) {
        
        // Report to the UI that the location was updated
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        
        Log.e(TAG, msg);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        // TODO: Calculate the closest station using Util function and setStation
        // When 'onLocationChanged' is called:
        // - Calculate the closet station - e.g. Util.getClosestStation(Location loc)
        // - Get a the NearestStationFragment from the FragmentManager
        // - Call NearestStationFragment#setStation(station) to set the closest station

        Station closestStation = Util.getClosestStation(location, stationsList);

        NearestStationFragment f = (NearestStationFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container_middle);
       // Station station = stations.get(stationIndex);
        f.setStation(closestStation);

    }
}
