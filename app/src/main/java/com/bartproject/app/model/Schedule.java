package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import java.util.List;


/**
 * Created by Anu on 4/24/14.
 */

public class Schedule {

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

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public List<Trip> getRequest() {
        return request;
    }

    public void setRequest(List<Trip> request) {
        this.request = request;
    }

    @Element
    private String date;

    @Element
    private String time;

    @Element
    private String before;

    @Element
    private String after;

    @ElementList
    private List<Trip> request;

}
