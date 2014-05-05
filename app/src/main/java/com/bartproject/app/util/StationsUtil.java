package com.bartproject.app.util;

import com.bartproject.app.model.Station;
import com.bartproject.app.model.StationsResponse;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StationsUtil {

    public static List<Station> readStations(Context context) {

        AssetManager assetManager = context.getResources().getAssets();
        Serializer serializer = new Persister();
        StationsResponse stationsResponse = null;

        try {
            InputStream source = assetManager.open("stationsList.xml");
            stationsResponse = serializer.read(StationsResponse.class, source);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (stationsResponse != null) {
            return stationsResponse.getStations();
        } else {
            return new ArrayList<Station>(0);
        }
    }

}
