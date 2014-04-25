package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import java.util.List;


/**
 * Created by Anu on 4/24/14.
 */

public class Schedule {

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
