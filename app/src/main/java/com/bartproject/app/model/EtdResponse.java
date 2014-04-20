package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "root", strict = false)
public class EtdResponse {

    @Element
    private String uri;

    @Element
    private String date;

    @Element
    private String time;

    @Element
    private Station station;

    public String getUri() {
        return uri;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Station getStation() {
        return station;
    }

}
