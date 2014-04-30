package com.bartproject.app.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.graphics.Color;

/**
 * Created by Anu on 4/19/14.
 */
@Root(strict = false)
public class Estimate {

    @Element
    private String minutes;

    @Element
    private String platform;

    @Element
    private String bikeflag;

    @Element
    private String hexcolor;


    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getBikeflag() {
        return bikeflag;
    }

    public void setBikeflag(String bikeflag) {
        this.bikeflag = bikeflag;
    }

    public int getColor() {
        return Color.parseColor(hexcolor);
    }

}
