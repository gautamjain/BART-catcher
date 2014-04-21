package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "root")
public class FareResponse {

        @Element
        private String uri;

        @Element
        private String origin;

        @Element
        private String destination;


    @Element
    private Trip trip;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}