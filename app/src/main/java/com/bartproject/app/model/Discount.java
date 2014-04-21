package com.bartproject.app.model;

import org.simpleframework.xml.Element;

/**
 * Created by Anu on 4/19/14.
 */
public class Discount {

    @Element
    private String clipper;

    public String getClipper() {
        return clipper;
    }

    public void setClipper(String clipper) {
        this.clipper = clipper;
    }
}
