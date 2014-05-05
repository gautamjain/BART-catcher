package com.bartproject.app.activity;

import com.bartproject.app.R;
import com.bartproject.app.model.Station;
import com.bartproject.app.model.StationsResponse;
import com.bartproject.app.network.GetStationsRequest;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SelectStationActivity extends BaseActivity {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String ITEM_STATION = "EXTRA_STATION";

    ListView lvStations;
    ArrayAdapter<Station> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);

        // Hide action bar logo
        getActionBar().setDisplayShowHomeEnabled(false);

        lvStations = (ListView) findViewById(R.id.lvStations);

        // Setep ListView
        List<Station> stations = new ArrayList<Station>(0);
        adapter = new ArrayAdapter<Station>(this, android.R.layout.simple_list_item_1, stations);
        lvStations.setAdapter(adapter);

        lvStations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent();

                data.putExtra(ITEM_STATION, adapter.getItem(position));

                setResult(RESULT_OK, data);
                finish();
            }
        });

        // Set title
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        getActionBar().setTitle(title);

        // Execute network request to get list of stations
        fetchStations();
    }

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

    private class GetStationsRequestListener implements RequestListener<StationsResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(SelectStationActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRequestSuccess(StationsResponse stationsResponse) {
            adapter.clear();
            adapter.addAll(stationsResponse.getStations());
        }
    }


}
