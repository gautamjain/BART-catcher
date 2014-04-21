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
    private List<Destination> destinationList;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public List<Destination> getDestination() {
        return destinationList;
    }

    public void setDestination(List<Destination> destinationList) {
        this.destinationList = destinationList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
