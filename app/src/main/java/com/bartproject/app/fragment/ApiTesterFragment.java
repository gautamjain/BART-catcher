package com.bartproject.app.fragment;

import com.bartproject.app.activity.MainActivity;
import com.bartproject.app.R;
import com.bartproject.app.activity.SelectStationActivity;
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

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class ApiTesterFragment extends Fragment {

    public static final String TAG = ApiTesterFragment.class.getSimpleName();
    private static final int SELECT_STATION_REQUEST_CODE = 1;

//    TextView tvDebug;
    Button btnFetchEtd;
    Button btnFetchStations;
    Button btnSelectStation;
    Button btnFilterStations;

    public ApiTesterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_api_tester, container, false);

//        tvDebug = (TextView) rootView.findViewById(R.id.tvDebug);
//        tvDebug.setMovementMethod(new ScrollingMovementMethod());

        btnFetchEtd = (Button) rootView.findViewById(R.id.btnFetchEtd);
        btnFetchStations = (Button) rootView.findViewById(R.id.btnFetchStations);
        btnFilterStations = (Button) rootView.findViewById(R.id.btnFilterStations);
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

        // Set on click listener.  Call fetch stations when the button is clicked.
        /*btnFilterStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterStations();
            }
        });*/

        // Set on click listenner. Open select station activity.
        btnSelectStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectStationActivity.class);
                intent.putExtra(SelectStationActivity.EXTRA_TITLE, "Select a new station:");
                startActivityForResult(intent, SELECT_STATION_REQUEST_CODE);
            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_STATION_REQUEST_CODE) {
            Station selectedStation = (Station) data.getSerializableExtra(SelectStationActivity.ITEM_STATION);
            Toast.makeText(getActivity(), "Station selected: " + selectedStation.getName(), Toast.LENGTH_LONG).show();
        }
    }

    // For Testing Filter destination station
    /*private void filterStations() {

        String origin = "ASHB";
        String destination = "RICH";
        // Create a request object
        GetDepartTrainHeadStationRequest request = new GetDepartTrainHeadStationRequest(origin,destination);

        // Create a unique cache key
        String cacheKey = request.createCacheKey();

        // Execute the network request
        // Set the cache duration for 10 seconds
        ((MainActivity) getActivity()).getSpiceManager().execute(request, cacheKey,
                DurationInMillis.ONE_SECOND * 10, new GetDepartTrainHeadStationRequestListener());


    }*/

    /*private class GetDepartTrainHeadStationRequestListener implements RequestListener<Depart> {

        List<Etd> etdList;

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(TAG, "Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

            // TODO: Show some kind of error message inside of this fragment's listview/view
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


            for (Trip t : tripInfo) {
                List<Leg> legsInfo = t.getLegs();
                String trainHeadStationName = legsInfo.get(0).getTrainHeadStation();

                for (Etd e : etdList) {
                    if (!e.getAbbrDest().equals(trainHeadStationName))
                    {
                        etdList.remove(e);
                    }
                }

                //  Update the ListAdapter with the new data
                adapter.notifyDataSetChanged();
                // if it doesn't refresh itself then do the addAll
                adapter.addAll(etdList);

                // attach the Etd list to ETD adapter
                // addAll(collection) works only from version 11 and above
                //adapter.addAll(etdResponse.getStationOrigin().getEtdList());
                //adapter.

            }
        }*/

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
//        tvDebug.append(s + "\n");
    }


    private class GetStationsRequestListener implements RequestListener<StationsResponse> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
//            tvDebug.setText("");
            appendLog("Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

            Toast.makeText(getActivity(), "Failed - see Logcat", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRequestSuccess(StationsResponse stationsResponse) {
//            tvDebug.setText("");
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
//            tvDebug.setText("");
            appendLog("Error fetching arrival times.");
            Log.e(TAG, spiceException.toString());

            Toast.makeText(getActivity(), "Failed - see Logcat", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRequestSuccess(EtdResponse etdResponse) {
//            tvDebug.setText("");
            appendLog("Fetching arrival times successful");

            // TODO: Need to extract relevant data from etdRespones here
            // This will be possible when all of the SimpleXML-related classes are finished

            // For example,  etdResponse.getArrivals()[0]

            appendLog("TIME: " + etdResponse.getDate() + " " + etdResponse.getTime());
            appendLog("ORIGIN: " + etdResponse.getStationOrigin().getName() + " - " + etdResponse
                    .getStationOrigin().getAbbr());

            for (Etd etd : etdResponse.getStationOrigin().getEtdList()) {
                appendLog("DEST: " + etd.getDestinationName() + " - " + etd.getAbbrDest());

                for (Estimate e : etd.getEstimatesList()) {
                    appendLog(e.getMinutes() + " minutes");
                }

            }

            Toast.makeText(getActivity(), "Success - see Logcat", Toast.LENGTH_SHORT).show();
        }
    }
}
