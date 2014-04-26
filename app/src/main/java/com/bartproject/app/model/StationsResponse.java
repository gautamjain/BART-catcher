package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "root", strict = false)
public class StationsResponse {

    @Element
    private String uri;

    @ElementList
    private List<Station> stations;

    public List<Station> getStations() {
        return stations;
    }
}
