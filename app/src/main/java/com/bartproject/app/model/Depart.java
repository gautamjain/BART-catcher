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


}
