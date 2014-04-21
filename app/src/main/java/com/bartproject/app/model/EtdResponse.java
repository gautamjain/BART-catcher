package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

// to get the response, the etd request will be
// cmd (etd) + key + userInfo + direction(get this from list of station, given src,dest)
// the etd response will give the destination in etd array

// Create a method/file that will return the direction(N/S) given origin,destination
@Root(name = "root",strict = false)

public class EtdResponse {

    @Element
    private String uri;

    @Element
    private String date;

    @Element
    private String time;

    //origin of the station
    @Element(name = "station")
    private Station stationOrigin;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Station getStationOrigin() {
        return stationOrigin;
    }

    public void setStationOrigin(Station stationOrigin) {
        this.stationOrigin = stationOrigin;
    }
}
