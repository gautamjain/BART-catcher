package com.bartproject.app.network;


import com.bartproject.app.model.EtdResponse;
import com.bartproject.app.model.StationsResponse;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.retrofit.RetrofitObjectPersisterFactory;
import com.octo.android.robospice.retrofit.RetrofitSpiceService;

import android.app.Application;

import java.io.File;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.converter.Converter;
import retrofit.converter.SimpleXMLConverter;
import retrofit.http.GET;
import retrofit.http.Query;

public class ApiService extends RetrofitSpiceService {

    private static final String API_URL = "http://api.bart.gov/api";

    private static final String DATE_FORMAT = "mm/dd/yyyy";

    public static final String KEY = "MW9S-E7SL-26DU-VV8V";

    public interface BartService {

        /**
         *  Get real-time train arrival information for a particular BART station.
         *
         * @param station Specifies the station. Stations are referenced by their four character abbreviations.
         * @param key API registration key.
         */
        @GET("/etd.aspx?cmd=etd")
        EtdResponse getArrivalTimes(@Query("orig") String station, @Query("key") String key);


        /**
         * Get fare information for a trip between two stations.
         *
         * @param origStation Specifies the origination station. Stations are referenced using their four character abbreviations.
         * @param destStation Specifies the destination station. Stations are referenced using their four character abbreviations.
         * @param date (Optional) Specifies a date to use for calculating the fare. If not specified, the current date will be used. Format must be "mm/dd/yyyy".
         * @param key API registration key.
         */
        //@GET("/sched.aspx?cmd=fare")
       // FareResponse getFare(@Query("orig") String origStation, @Query("dest") String destStation, @Query("date") String date, @Query("key") String key);

        /**
         *  Get a list of all of the BART stations.
         *
         * @param key API registration key.
         */
        @GET("/stn.aspx?cmd=stns")
        StationsResponse getStations(@Query("key") String key);


    }

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(BartService.class);
    }

    @Override
    protected String getServerUrl() {
        return API_URL;
    }

    @Override
    protected Converter createConverter() {
        return new SimpleXMLConverter();
    }

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(
                new RetrofitObjectPersisterFactory(application, getConverter(), getCacheFolder()));
        return cacheManager;
    }

    private File getCacheFolder() {
        // TODO getCacheFolder()
        return null;
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        return new RestAdapter.Builder()
                .setEndpoint(getServerUrl())
                .setLogLevel(LogLevel.FULL)
                .setConverter(getConverter());
    }

}
