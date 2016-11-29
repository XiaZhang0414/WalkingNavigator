package com.xia.walkingnavigator;

/**
 * Created by Xia on 11/9/2016.
 */

public class RestRoom extends Room{
    String gender;

    public RestRoom(int id, Location location, String name, String gender){
        super(id,location,name);
        this.gender=gender;
    }
}
