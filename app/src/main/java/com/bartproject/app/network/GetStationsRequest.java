package com.bartproject.app.network;

import com.bartproject.app.model.StationsResponse;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


public class GetStationsRequest extends RetrofitSpiceRequest<StationsResponse, ApiService.BartService> {

    public GetStationsRequest() {
        super(StationsResponse.class, ApiService.BartService.class);
    }

    @Override
    public StationsResponse loadDataFromNetwork() {
        return getService().getStations(ApiService.KEY);
    }

    public String createCacheKey() {
        return GetStationsRequest.class.getName();
    }

}
