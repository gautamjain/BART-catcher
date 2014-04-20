package com.bartproject.app;

import com.bartproject.app.model.EtdResponse;
import com.bartproject.app.network.GetArrivalTimesRequest;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class ApiTesterFragment extends Fragment {

    public static final String TAG = ApiTesterFragment.class.getSimpleName();

    TextView tvDebug;
    Button btnFetch;

    public ApiTesterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_api_tester, container, false);

        tvDebug = (TextView) rootView.findViewById(R.id.tvDebug);
        btnFetch = (Button) rootView.findViewById(R.id.btnFetch);

        // Set on click listener.  Call fetch when the button is clicked.
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch();
            }
        });

        return rootView;
    }

    /**
     * Fetch button was pressed, execute network request
     */
    public void fetch() {

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


    private class GetArrivalTimesRequestListener implements RequestListener<EtdResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(TAG, "Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());
        }

        @Override
        public void onRequestSuccess(EtdResponse etdResponse) {
            Log.i(TAG, "Fetching arrival times successful");

            // TODO: Need to extract relevant data from etdRespones here
            // This will be possible when all of the SimpleXML-related classes are finished

            // For example,  etdResponse.getArrivals()[0]

            Log.d(TAG, "TIME: " + etdResponse.getDate() + " " + etdResponse.getTime());
            Log.d(TAG, "STATION: " + etdResponse.getStation());
            Log.d(TAG, "URL: " + etdResponse.getUri());
            Log.d(TAG, etdResponse.toString());
            
            tvDebug.setText(etdResponse.toString());
        }
    }
}
