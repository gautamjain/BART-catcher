package com.bartproject.app.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Anu on 4/24/14.
 * different legs
 * trip origin = "rock", dest = "wdub"
 * leg order is the stations along the way
 * question: do we need time here???
 */
@Root(strict=false)
public class Leg {

    @Attribute(required = false)
    private String order;

    @Attribute(required = false)
    private String origin;

    @Attribute(required = false)
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

    public String getTrainHeadStation() {
        return trainHeadStation;
    }

    public void setTrainHeadStation(String trainHeadStation) {
        this.trainHeadStation = trainHeadStation;
    }

    public String getTrainIdx() {
        return trainIdx;
    }

    public void setTrainIdx(String trainIdx) {
        this.trainIdx = trainIdx;
    }

    @Attribute(required = false)
    private String origTimeDate;

    @Attribute(required = false)
    private String destTimeDate;

    @Attribute(required = false)
    private String bikeFlag;

    @Attribute(name = "line")
    private String routeNum;

    @Attribute(required = false)
    private String trainHeadStation;

    @Attribute(required = false)
    private String trainIdx;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
