package com.bartproject.app.network;

import com.bartproject.app.model.Depart;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class GetDepartTrainHeadStationRequest extends RetrofitSpiceRequest<Depart, ApiService.BartService> {

    private final String origin;
    private final String destination;

    public GetDepartTrainHeadStationRequest(String orig,String dest) {
        super(Depart.class, ApiService.BartService.class);
        this.origin = orig;
        this.destination = dest;
    }

    //getDepartTrain does the apiService call
    @Override
    public Depart loadDataFromNetwork() {
        return getService().getDepartTrain(origin, destination, ApiService.KEY);
    }

    public String createCacheKey() {
        return GetDepartTrainHeadStationRequest.class.getName();
    }


}
