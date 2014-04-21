package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;


// origin
public class Station {

    @Element
    private String name;

    @Element
    private String abbr;

    //destination
    @ElementList(inline = true,name = "etd")
    private List<Destination> destination;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public List<Destination> getDestination() {
        return destination;
    }

    public void setDestination(List<Destination> destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
