package com.bartproject.app;

import com.bartproject.app.model.EtdResponse;
import com.bartproject.app.model.Station;
import com.bartproject.app.model.StationsResponse;
import com.bartproject.app.network.GetArrivalTimesRequest;
import com.bartproject.app.network.GetStationsRequest;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class ApiTesterFragment extends Fragment {

    public static final String TAG = ApiTesterFragment.class.getSimpleName();

    TextView tvDebug;
    Button btnFetchEtd;
    Button btnFetchStations;

    public ApiTesterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_api_tester, container, false);

        tvDebug = (TextView) rootView.findViewById(R.id.tvDebug);
        btnFetchEtd = (Button) rootView.findViewById(R.id.btnFetchEtd);
        btnFetchStations = (Button) rootView.findViewById(R.id.btnFetchStations);

        // Set on click listener.  Call fetch etd when the button is clicked.
        btnFetchEtd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchEtd();
            }
        });

        // Set on click listener.  Call fetch stations when the button is clicked.
        btnFetchStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchStations();
            }
        });


        return rootView;
    }

    /**
     * Fetch button was pressed, execute network request
     */
    public void fetchEtd() {

        // Determine a station for which to get data, examples:
        // Milbrae = MLBR
        // Richmond = RICH
        // Ashby = ASHB
        String stationAbbr = "ASHB";

        // Create a request object
        GetArrivalTimesRequest request = new GetArrivalTimesRequest(stationAbbr);

        // Create a unique cache key
        String cacheKey = request.createCacheKey();

        // Execute the network request
        // Set the cache duration for 10 seconds
        ((MainActivity) getActivity()).getSpiceManager().execute(request, cacheKey,
                DurationInMillis.ONE_SECOND * 10, new GetArrivalTimesRequestListener());
    }

    public void fetchStations() {
        // Create a request object
        GetStationsRequest request = new GetStationsRequest();

        // Create a unique cache key
        String cacheKey = request.createCacheKey();

        // Execute the network request
        // Set the cache duration for 10 seconds, although in reality this should be closer to one month
        ((MainActivity) getActivity()).getSpiceManager().execute(request, cacheKey,
                DurationInMillis.ONE_SECOND * 10, new GetStationsRequestListener());
    }


    private class GetStationsRequestListener implements RequestListener<StationsResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(TAG, "Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

            Toast.makeText(getActivity(), "Failed - see Logcat", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRequestSuccess(StationsResponse stationsResponse) {
            Log.i(TAG, "Fetching stations successful");

            Log.d(TAG, "Number of stations: " + stationsResponse.getStations().size());

            for (Station s : stationsResponse.getStations()) {
                Log.d(TAG, s.getAbbr() + " - " + s.getName());
            }

            Toast.makeText(getActivity(), "Success - see Logcat", Toast.LENGTH_SHORT).show();

        }
    }

    private class GetArrivalTimesRequestListener implements RequestListener<EtdResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(TAG, "Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

            Toast.makeText(getActivity(), "Failed - see Logcat", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRequestSuccess(EtdResponse etdResponse) {
            Log.i(TAG, "Fetching arrival times successful");

            // TODO: Need to extract relevant data from etdRespones here
            // This will be possible when all of the SimpleXML-related classes are finished

            // For example,  etdResponse.getArrivals()[0]

            Log.d(TAG, "TIME: " + etdResponse.getDate() + " " + etdResponse.getTime());
            Log.d(TAG, "STATION: " + etdResponse.getStationOrigin());
            Log.d(TAG, "URL: " + etdResponse.getUri());
            Log.d(TAG, etdResponse.toString());

            Toast.makeText(getActivity(), "Success - see Logcat", Toast.LENGTH_SHORT).show();
        }
    }
}
