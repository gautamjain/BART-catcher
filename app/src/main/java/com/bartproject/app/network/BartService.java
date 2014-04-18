package com.bartproject.app.network;


import com.bartproject.app.model.EtdResponse;
import com.bartproject.app.model.FareResponse;

import retrofit.http.GET;
import retrofit.http.Query;

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
    @GET("/sched.aspx?cmd=fare")
    FareResponse getFare(@Query("orig") String origStation, @Query("dest") String destStation, @Query("date") String date, @Query("key") String key);


}
