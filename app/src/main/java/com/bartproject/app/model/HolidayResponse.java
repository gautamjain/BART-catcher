package com.bartproject.app.model;

/**
 * Created by Anu on 4/19/14.
 */

//request: holiday + key

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.ElementList;
import java.util.List;


@Root(name = "root")
public class HolidayResponse {

    @Element
    private String uri;

    @ElementList(inline = true)
    private List<Holiday> holidays;

}
