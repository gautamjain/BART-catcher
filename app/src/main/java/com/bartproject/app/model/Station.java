package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

// origin
@Root(strict = false)
public class Station implements Serializable {

    public Station(String name) {
        // Dummy constructor for creating fake Stations (testing purposes)
        this.name = name;
    }

    public Station() {
        
    }

    @Element
    private String name;

    @Element
    private String abbr;

    @Element(required = false)
    private Double gtfs_latitude;

    public Double getGtfs_latitude() {
        return gtfs_latitude;
    }

    public Double getGtfs_longitude() {
        return gtfs_longitude;
    }

    @Element(required = false)
    private Double gtfs_longitude;

    @Element(required = false)
    private String address;

    @Element(required = false)
    private String city;

    @Element(required = false)
    private String county;

    @Element(required = false)
    private String state;

    @Element(required = false)
    private String zipcode;

    //destination
    @ElementList(inline = true, required = false)
    private List<Etd> etdList;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public List<Etd> getEtdList() {
        return etdList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}
