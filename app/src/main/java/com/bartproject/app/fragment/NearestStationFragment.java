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
import java.util.List;

import hugo.weaving.DebugLog;


/**
 *
 * The 'middle' fragment on our main screen.  Shows the train arrival times at a particular station.
 *
 */
public class NearestStationFragment extends Fragment
{

    public static final String TAG = NearestStationFragment.class.getSimpleName();

    private TextView tvStationTitle;
    private ListView lvNearestStationList;
    private EtdAdapter adapter;
    List<Etd> etdList;
    List<String> trainHeadStationNames = new ArrayList<String>(0);

    public NearestStationFragment() {
        // Required empty public constructor
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
    public void setDestinationStation(Station origin, Station destStation)
    {
        // Create a request object from depart cmd for the TrainHeadStation info.
        GetDepartTrainHeadStationRequest request =
                new GetDepartTrainHeadStationRequest(origin.getAbbr(), destStation.getAbbr());

        // Create a unique cache key
        String cacheKey = request.createCacheKey();

        // Execute the network request
        // Set the cache duration for 10 seconds
        ((MainActivity) getActivity()).getSpiceManager().execute(request, cacheKey,
                DurationInMillis.ONE_SECOND * 10, new GetDepartTrainHeadStationRequestListener());
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

            List<String> trainNames = new ArrayList<String>(10);
            for (Trip t : tripInfo)
            {
                List<Leg> legsInfo = t.getLegs();
                String trainHeadStationName = legsInfo.get(0).getTrainHeadStation();
                trainNames.add(trainHeadStationName);


            }
            trainHeadStationNames = trainNames;
            filterArrivalTimes();
            adapter.notifyDataSetChanged();
        }
    }

    public void filterArrivalTimes() {
        List<Etd> filteredEtdList = new ArrayList<Etd>();
        for (Etd e : etdList)
        {
            for (String trainHeadStationName : trainHeadStationNames) {
            if (e.getAbbrDest().equals(trainHeadStationName))
                {
                    filteredEtdList.add(e);
                }
            }
        }

        if (filteredEtdList.size() != 0)
            etdList = filteredEtdList;

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
        public void onRequestSuccess(EtdResponse etdResponse)
        {
            Log.i(TAG, "Fetching arrival times successful");

            adapter.clear();
            etdList = etdResponse.getStationOrigin().getEtdList();
            filterArrivalTimes();
            adapter.addAll(etdList);

        }
    }

}
