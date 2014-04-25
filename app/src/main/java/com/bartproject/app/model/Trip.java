package com.bartproject.app.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Anu on 4/24/14.
 * contains info about the trip including origin,destn,fare,clipper,
 * origin & dest time & dates.
 */
@Root(strict = false)
public class Trip {

    @Attribute
    private String origin;

    @Attribute
    private String destination;

    @Attribute
    private String fare;

    @Attribute
    private String origTimeMin;

    @Attribute
    private String origTimeDate;

    @Attribute
    private String destTimeMin;

    @Attribute
    private String destTimeDate;

    @Attribute
    private String clipper;

    @ElementList(inline=true)
    private List<Leg> legs;

}
