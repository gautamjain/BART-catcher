package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Anu on 4/24/14.
 */
@Root(name = "root", strict = false)
public class Depart {

    @Element
    private String uri;

    @Element
    private String  origin;

    @Element
    private String destination;

    @Element
    private String sched_num;

    @Element
    private Schedule schedule;


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

    public String getSched_num() {
        return sched_num;
    }

    public void setSched_num(String sched_num) {
        this.sched_num = sched_num;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
