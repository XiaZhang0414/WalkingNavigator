package com.xia.walkingnavigator;

/**
 * Created by Xia on 11/9/2016.
 */

public class Office extends Room{
    String name;

    public Office(int id, Location location, String name){
        super(id,location,name);
        this.name=name;
    }
}
