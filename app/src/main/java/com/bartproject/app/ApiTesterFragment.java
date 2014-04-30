package com.bartproject.app;

import com.bartproject.app.model.Estimate;
import com.bartproject.app.model.Etd;
import com.bartproject.app.model.EtdResponse;
import com.bartproject.app.model.Station;
import com.bartproject.app.model.StationsResponse;
import com.bartproject.app.network.GetArrivalTimesRequest;
import com.bartproject.app.network.GetStationsRequest;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
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
    Button btnSelectStation;

    public ApiTesterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_api_tester, container, false);

        tvDebug = (TextView) rootView.findViewById(R.id.tvDebug);
        tvDebug.setMovementMethod(new ScrollingMovementMethod());

        btnFetchEtd = (Button) rootView.findViewById(R.id.btnFetchEtd);
        btnFetchStations = (Button) rootView.findViewById(R.id.btnFetchStations);
        btnSelectStation = (Button) rootView.findViewById(R.id.btnSelectStation);

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

        // Set on click listenner. Open select station activity.
        btnSelectStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectStationActivity.class);
                intent.putExtra(SelectStationActivity.EXTRA_TITLE, "Select a new station:");
                startActivity(intent);
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

    public void appendLog(String s) {
        Log.d(TAG, s);
        tvDebug.append(s + "\n");
    }


    private class GetStationsRequestListener implements RequestListener<StationsResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            tvDebug.setText("");
            appendLog("Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

            Toast.makeText(getActivity(), "Failed - see Logcat", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRequestSuccess(StationsResponse stationsResponse) {
            tvDebug.setText("");
            appendLog("Fetching stations successful");

            appendLog("Number of stations: " + stationsResponse.getStations().size());

            for (Station s : stationsResponse.getStations()) {
                appendLog(s.getAbbr() + " - " + s.getName());
            }

            ((MainActivity) getActivity()).setStationsList(stationsResponse.getStations());

            Toast.makeText(getActivity(), "Success - see Logcat", Toast.LENGTH_SHORT).show();

        }
    }

    private class GetArrivalTimesRequestListener implements RequestListener<EtdResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            tvDebug.setText("");
            appendLog("Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

            Toast.makeText(getActivity(), "Failed - see Logcat", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRequestSuccess(EtdResponse etdResponse) {
            tvDebug.setText("");
            appendLog("Fetching arrival times successful");

            // TODO: Need to extract relevant data from etdRespones here
            // This will be possible when all of the SimpleXML-related classes are finished

            // For example,  etdResponse.getArrivals()[0]

            appendLog("TIME: " + etdResponse.getDate() + " " + etdResponse.getTime());
            appendLog("ORIGIN: " + etdResponse.getStationOrigin().getName() + " - " + etdResponse
                    .getStationOrigin().getAbbr());

            for (Etd etd : etdResponse.getStationOrigin().getEtdList()) {
                appendLog("DEST: " + etd.getDestinationName() + " - " + etd.getAbbrDest());

                for (Estimate e : etd.getEstimateTimeOfDep()) {
                    appendLog(e.getMinutes() + " minutes");
                }

            }

            Toast.makeText(getActivity(), "Success - see Logcat", Toast.LENGTH_SHORT).show();
        }
    }
}
