package com.bartproject.app.network;

import com.bartproject.app.model.EtdResponse;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


public class GetArrivalTimesRequest extends RetrofitSpiceRequest<EtdResponse, ApiService.BartService> {

    private final String station;

    public GetArrivalTimesRequest(String station) {
        super(EtdResponse.class, ApiService.BartService.class);
        this.station = station;
    }

    @Override
    public EtdResponse loadDataFromNetwork() {
        return getService().getArrivalTimes(station, ApiService.KEY);
    }

    public String createCacheKey() {
        return GetArrivalTimesRequest.class.getName() + station;
    }

}
