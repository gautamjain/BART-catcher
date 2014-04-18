package com.bartproject.app.model;

import org.simpleframework.xml.Element;

public class Station {

    @Element
    private String name;

    @Element
    private String abbr;

    @Element
    private Etd etd;

}
