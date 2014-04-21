package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import java.util.List;

public class Destination {

    @Element(name = "destination")
    private String destinationName;

    @Element(name = "abbreviation")
    private String abbrDest;

    //estimated time of departure towards this destination
    @ElementList(inline = true,name = "estimate")
    private List<Estimate> estimateTimeOfDep;

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getAbbrDest() {
        return abbrDest;
    }

    public void setAbbrDest(String abbrDest) {
        this.abbrDest = abbrDest;
    }

    public List<Estimate> getEstimateTimeOfDep() {
        return estimateTimeOfDep;
    }

    public void setEstimateTimeOfDep(List<Estimate> estimateTimeOfDep) {
        this.estimateTimeOfDep = estimateTimeOfDep;
    }
}