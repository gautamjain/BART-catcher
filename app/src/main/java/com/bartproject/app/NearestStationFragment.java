package com.bartproject.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartproject.app.model.Etd;
import com.bartproject.app.model.EtdResponse;
import com.bartproject.app.model.Station;
import com.bartproject.app.network.EtdAdapter;
import com.bartproject.app.network.GetArrivalTimesRequest;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;


import hugo.weaving.DebugLog;


/**
 *
 * The 'middle' fragment on our main screen.  Shows the train arrival times at a particular station.
 *
 */
public class NearestStationFragment extends Fragment {

    public static final String TAG = NearestStationFragment.class.getSimpleName();

    private TextView tvStationTitle;
    private ListView lvNearestStationList;
    private EtdAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NearestStationFragment.
     */
    // Not required in Bart project
    /*public static NearestStationFragment newInstance() {
        NearestStationFragment fragment = new NearestStationFragment();
        return fragment;
    }*/
    public NearestStationFragment() {
        // Required empty public constructor
    }

    //We don't see this oncreate for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View nearestStationView = inflater.inflate(R.layout.fragment_nearest_station, container, false);
        tvStationTitle = (TextView) nearestStationView.findViewById(R.id.tvStationTitle);
        lvNearestStationList =
                (ListView)nearestStationView.findViewById(R.id.lvNearestStationList);

        List <Etd> etdList1 = new ArrayList<Etd>();

        adapter = new EtdAdapter(getActivity(),etdList1);
        lvNearestStationList.setAdapter(adapter);
        return nearestStationView;
    }

    @DebugLog
    public void setStation(Station station) {
        // DONE SAVE station and execute network request to get ETD data about this station
        // Code below was copy/pasted from ApiTesterFragment.  NEEDS to be reviewed.

        // anu- test statement remove it later
        Toast.makeText(getActivity(), "inside set station of nearestStnFragment", Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), station.getName(), Toast.LENGTH_SHORT).show();


        // Create a request object - showing the etd station.
        GetArrivalTimesRequest request = new GetArrivalTimesRequest(station.getAbbr());
        //GetStationsRequest request = new GetStationsRequest();

        // Create a unique cache key
        String cacheKey = request.createCacheKey();

        // Execute the network request
        // Set the cache duration for 10 seconds
        ((MainActivity) getActivity()).getSpiceManager().execute(request, cacheKey,
                DurationInMillis.ONE_SECOND * 10, new GetArrivalTimesRequestListener());

        //Set the station title (TextView) of this fragment to the station's name.E.g. station.getName();
        tvStationTitle.setText(station.getName());

    }


    private class GetArrivalTimesRequestListener implements RequestListener<EtdResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(TAG, "Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

            // TODO: Show some kind of error message inside of this fragment's listview/view
        }

        @Override
        public void onRequestSuccess(EtdResponse etdResponse)
        {
            Log.i(TAG, "Fetching arrival times successful");

            //  Need to extract relevant data from etdRespones here
            // For example,  etdResponse.getArrivals()[0]
            //  Update the ListAdapter with the new data
            // attach the Etd list to ETD adapter
            // Each row of this list contains station_item list.

            adapter.clear();
            // addAll(collection) works only from version 11 and above
            adapter.addAll(etdResponse.getStationOrigin().getEtdList());

        }
    }

}
