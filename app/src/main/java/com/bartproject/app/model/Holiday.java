package com.bartproject.app.model;

/**
 * Created by Anu on 4/19/14.
 */

import org.simpleframework.xml.Element;

public class Holiday {

    @Element
    private String name;

    @Element
    private String date;

    // Anu- To use this sunday-type, we have to
    //keep track of the day. that could be determine
    // by date
    @Element
    private String schedule_type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSchedule_type() {
        return schedule_type;
    }

    public void setSchedule_type(String schedule_type) {
        this.schedule_type = schedule_type;
    }

}
