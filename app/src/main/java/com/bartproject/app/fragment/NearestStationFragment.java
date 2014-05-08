package com.bartproject.app.fragment;

import com.bartproject.app.R;
import com.bartproject.app.activity.MainActivity;
import com.bartproject.app.model.Depart;
import com.bartproject.app.model.Etd;
import com.bartproject.app.model.EtdResponse;
import com.bartproject.app.model.Leg;
import com.bartproject.app.model.Station;
import com.bartproject.app.model.Trip;
import com.bartproject.app.network.EtdAdapter;
import com.bartproject.app.network.GetArrivalTimesRequest;
import com.bartproject.app.network.GetDepartTrainHeadStationRequest;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hugo.weaving.DebugLog;


/**
 *
 * The 'middle' fragment on our main screen.  Shows the train arrival times at a particular station.
 *
 */
public class NearestStationFragment extends Fragment
{

    public static final String TAG = NearestStationFragment.class.getSimpleName();

    private static final String ARGS_ORIGIN = "ARGS_ORIGIN";

    private static final String ARGS_DESTINATION = "ARGS_DESTINATION";

    private TextView tvStationTitle;
    private ListView lvNearestStationList;
    private EtdAdapter adapter;
    List<Etd> etdList;
    Set<String> trainHeadStationNames = new HashSet<String>();

    Station origin;
    Station destination;

    public NearestStationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            origin = (Station) args.getSerializable(ARGS_ORIGIN);
            destination = (Station) args.getSerializable(ARGS_DESTINATION);
        }
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

    @Override
    public void onStart() {
        super.onStart();

        if (origin != null) {
            setStation(origin);

            if (destination != null) {
                setDestinationStation(origin, destination);
            }
        }

    }

    @DebugLog
    public void setDestinationStation(Station origin, Station destStation)
    {
        setStation(origin);

        // Create a request object from depart cmd for the TrainHeadStation info.
        GetDepartTrainHeadStationRequest request =
                new GetDepartTrainHeadStationRequest(origin.getAbbr(), destStation.getAbbr());

        // Create a unique cache key
        String cacheKey = request.createCacheKey();

        // Execute the network request
        // Set the cache duration for 10 seconds
        ((MainActivity) getActivity()).getSpiceManager().execute(request, cacheKey,
                DurationInMillis.ONE_MINUTE * 10, new GetDepartTrainHeadStationRequestListener());
    }

    public static NearestStationFragment newInstance(Station closestStation, Station destination) {
        NearestStationFragment f = new NearestStationFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_ORIGIN, closestStation);
        args.putSerializable(ARGS_DESTINATION, destination);

        f.setArguments(args);

        return f;
    }


    private class GetDepartTrainHeadStationRequestListener implements RequestListener<Depart>
    {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(TAG, "Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

        }

        @Override
        public void onRequestSuccess(Depart departResponse)
        {
            Log.i(TAG, "Fetching depart times successful");

            // Request has a list of trips
            //each trip has a list of leg order
            // we are interested in only the first leg and no transfers
            // get the trainHeadStationName from the first leg

            List<Trip> tripInfo = departResponse.getSchedule().getRequest();

            Set<String> trainNames = new HashSet<String>();
            for (Trip t : tripInfo)
            {
                List<Leg> legsInfo = t.getLegs();
                String trainHeadStationName = legsInfo.get(0).getTrainHeadStation();
                trainNames.add(trainHeadStationName);
            }
            trainHeadStationNames = trainNames;
            filterArrivalTimes();
        }
    }

    public void filterArrivalTimes() {
        List<Etd> filteredEtdList = new ArrayList<Etd>();

        // Make sure etdList is not null - this may be the case at night when there are no more trains
        if (etdList == null) {
            return;
        }

        for (Etd e : etdList)
        {
            for (String trainHeadStationName : trainHeadStationNames) {
                if (e.getAbbrDest().equals(trainHeadStationName))
                    {
                        Log.e(TAG, "Added to filtered list: " + e.getDestinationName());
                        filteredEtdList.add(e);
                    }
                }
        }

        if (filteredEtdList.size() != 0) {
            etdList = filteredEtdList;
            adapter.clear();
            adapter.addAll(etdList);
            Log.d(TAG, "Replaced etdList with filteredEtdList");
        }

    }

    public void sortArrivaltimes() {
        Collections.sort(etdList, new Comparator<Etd>() {
            @Override
            public int compare(Etd etd1, Etd etd2) {
                String e1 = etd1.getEstimatesList().get(0).getMinutes();
                String e2 = etd2.getEstimatesList().get(0).getMinutes();

                if (e1.equals("Leaving"))
                    e1 = "0";

                if (e2.equals("Leaving"))
                    e2 = "0";

                return Integer.valueOf(e1) - Integer.valueOf(e2);
            }
        });
    }

    @DebugLog
    public void setStation(Station station)
    {
        // Create a request object - showing the etd station.
        GetArrivalTimesRequest request = new GetArrivalTimesRequest(station.getAbbr());

        // Create a unique cache key
        String cacheKey = request.createCacheKey();

        // Execute the network request
        // Set the cache duration for 10 seconds
        ((MainActivity) getActivity()).getSpiceManager().execute(request, cacheKey,
                DurationInMillis.ONE_SECOND * 10, new GetArrivalTimesRequestListener());

        //Set the station title (TextView) of this fragment to the station's name.E.g. station.getName();
        // Example Powell to 24th
        if (destination !=null)
            tvStationTitle.setText(station.getName() + " to\n" + destination.getName());
        else
            tvStationTitle.setText(station.getName());
    }


    private class GetArrivalTimesRequestListener implements RequestListener<EtdResponse>
    {

        @Override
        public void onRequestFailure(SpiceException spiceException)
        {
            Log.e(TAG, "Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());
        }

        @Override
        public void onRequestSuccess(EtdResponse etdResponse) {
            Log.i(TAG, "Fetching arrival times successful");

            // Check to make sure EtdList is not null - this may happen at night when there are no more trains
            if (etdResponse.getStationOrigin().getEtdList() != null) {
                etdList = etdResponse.getStationOrigin().getEtdList();
                filterArrivalTimes();

                sortArrivaltimes();

                adapter.clear();
                adapter.addAll(etdList);
             }
        }
    }

}
