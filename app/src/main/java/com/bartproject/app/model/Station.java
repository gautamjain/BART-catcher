package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;


public class Station {

    @Element
    private String name;

    @Element
    private String abbr;

    @ElementList(inline = true)
    private List<Etd> etd;

}
