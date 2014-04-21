package com.bartproject.app.model;

import org.simpleframework.xml.Element;

/**
 * Created by Anu on 4/19/14.
 */
public class Trip {

        @Element
        private String fare;

        @Element
        private Discount discount;

    }
