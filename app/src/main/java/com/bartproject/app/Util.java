package com.bartproject.app;

import com.bartproject.app.model.Station;

import android.location.Location;

import java.util.List;

public class Util {

    public static Station getClosestStation(Location location, List<Station> stationsList) {

        Station closestStation;
        closestStation = stationsList.get(0);
        float closestDistance = Float.MAX_VALUE;

        for (Station s : stationsList) {
            float[] results = new float[3];

            Location.distanceBetween(
                    location.getLatitude(),
                    location.getLongitude(),
                    s.getGtfs_latitude(),
                    s.getGtfs_longitude(),
                    results);

            if (results[0] < closestDistance) {
                closestStation = s;
                closestDistance = results[0];
            }
        }

        return closestStation;
    }

}
