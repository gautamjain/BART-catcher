package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "root")
public class EtdResponse {

    @Element
    private String uri;

    @Element
    private String date;

    @Element
    private String time;

    @Element
    private Station station;

}
