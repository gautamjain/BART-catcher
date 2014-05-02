package com.bartproject.app;

import android.location.Location;

import com.bartproject.app.model.Station;

import java.util.List;

public class Util {

    public static Station getClosestStation(Location location, List<Station> stationsList) {
        // TODO: Return the closest station to the given location
        // Iterate through through all stations.
        // Calculate distance to each station, using GPS coordinates.
        // Return station with the shortest distance.

       float[] results = new float[3];
        Station closestStation;
        closestStation = stationsList.get(0);
        float closestDistance = Float.MAX_VALUE;

        for (Station s : stationsList) {
            //appendLog(s.getAbbr() + " - " + s.getName());
            Location.distanceBetween(location.getLatitude(),location.getLongitude(),s.getGtfs_latitude(),s.getGtfs_longitude(),results);

            if (results[0] < closestDistance)
                closestStation = s;

        }

        return closestStation;
    }

}
