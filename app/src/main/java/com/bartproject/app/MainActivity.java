package com.bartproject.app;

import com.bartproject.app.model.Station;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends BaseActivity implements
        FavoriteStationsGridFragment.OnDestinationSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            // Add the three fragments for the main screen
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_top, new ApiTesterFragment())
                    .add(R.id.fragment_container_middle, new NearestStationFragment())
                    .add(R.id.fragment_container_bottom, new FavoriteStationsGridFragment())
                    .commit();
        }

        // TODO: Initialize Google Play Services library and register a LocationListener
        // http://developer.android.com/training/location/receive-location-updates.html
        // When 'onLocationChanged' is called:
        // - Calculate the closet station - e.g. Util.getClosestStation(Location loc)
        // - Get a the NearestStationFragment from the FragmentManager
        // - Call NearestStationFragment#setStation(station) to set the closest station

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

    @Override
    public void onDestinationSelected(Station destination) {
        // This method is called when a destination station has been selected.
        // At the momemnt, we don't need to do anything except show a toast.
        // Eventually, we will launch a new activity that has detailed timings
        // about your trip (from the nearest station to given destination station).

        Toast.makeText(this, destination.getName() + " was selected!", Toast.LENGTH_LONG ).show();
    }
}
