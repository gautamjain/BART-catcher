package com.bartproject.app;



import com.bartproject.app.model.Station;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * This fragment will eventually show a map of a specified station.  Currently uses a placeholder image.
 *
 */
public class StationMapFragment extends Fragment {


    public StationMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_station_map, container, false);
    }

    public void setStation(Station station) {
        // TODO: SAVE station and update map's center marker using station's GPS coordinates
    }

}
