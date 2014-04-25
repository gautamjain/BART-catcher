package com.bartproject.app.model;

import org.simpleframework.xml.Attribute;

/**
 * Created by Anu on 4/24/14.
 * different legs
 * trip origin = "rock", dest = "wdub"
 * leg order is the stations along the way
 * question: do we need time here???
 */
public class Leg {

    @Attribute
    private String order;

    @Attribute
    private String origin;

    @Attribute
    private String destination;

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

    public String getOrigTimeDate() {
        return origTimeDate;
    }

    public void setOrigTimeDate(String origTimeDate) {
        this.origTimeDate = origTimeDate;
    }

    public String getDestTimeDate() {
        return destTimeDate;
    }

    public void setDestTimeDate(String destTimeDate) {
        this.destTimeDate = destTimeDate;
    }

    public String getBikeFlag() {
        return bikeFlag;
    }

    public void setBikeFlag(String bikeFlag) {
        this.bikeFlag = bikeFlag;
    }

    public String getRouteNum() {
        return routeNum;
    }

    public void setRouteNum(String routeNum) {
        this.routeNum = routeNum;
    }

    public String getNextStation() {
        return nextStation;
    }

    public void setNextStation(String nextStation) {
        this.nextStation = nextStation;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    @Attribute
    private String origTimeDate;

    @Attribute
    private String destTimeDate;

    @Attribute
    private String bikeFlag;

    @Attribute(name = "line")
    private String routeNum;

    @Attribute(name = "trainHeadStation")
    private String nextStation;

    @Attribute(name = "trainIdx")
    private String trainNum;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
