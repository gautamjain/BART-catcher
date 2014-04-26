package com.bartproject.app;

import com.bartproject.app.model.EtdResponse;
import com.bartproject.app.model.Station;
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



/**
 *
 * The 'middle' fragment on our main screen.  Shows the train arrival times at a particular station.
 *
 */
public class NearestStationFragment extends Fragment {

    public static final String TAG = NearestStationFragment.class.getSimpleName();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NearestStationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearestStationFragment newInstance() {
        NearestStationFragment fragment = new NearestStationFragment();
        return fragment;
    }
    public NearestStationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Create this fragment's layout
        // Layout should include a station title (TextView) and a ListView

        // TODO: Implement a ListView and ListAdapter inside this fragment.
        // Each row/item should contain the following:
        // - Train icon (ImageView)
        // - Color bar (ImageView or some type of drawable)
        // - Train name/destination (TextView)
        // - Train arrival time (TextView)

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearest_station, container, false);
    }

    public void setStation(Station station) {
        // TODO: SAVE station and execute network request to get ETD data about this station
        // Code below was copy/pasted from ApiTesterFragment.  NEEDS to be reviewed.

        // Create a request object
        GetArrivalTimesRequest request = new GetArrivalTimesRequest(station.getAbbr());

        // Create a unique cache key
        String cacheKey = request.createCacheKey();

        // Execute the network request
        // Set the cache duration for 10 seconds
        ((MainActivity) getActivity()).getSpiceManager().execute(request, cacheKey,
                DurationInMillis.ONE_SECOND * 10, new GetArrivalTimesRequestListener());

        // TODO: Set the station title (TextView) of this fragment to the station's name.  E.g. station.getName();
    }


    private class GetArrivalTimesRequestListener implements RequestListener<EtdResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(TAG, "Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

            // TODO: Show some kind of error message inside of this fragment's listview/view
        }

        @Override
        public void onRequestSuccess(EtdResponse etdResponse) {
            Log.i(TAG, "Fetching arrival times successful");

            // TODO: Need to extract relevant data from etdRespones here
            // TODO: Update the ListAdapter with the new data

        }
    }

}
