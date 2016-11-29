package com.xia.walkingnavigator;

/**
 * Created by Xia on 11/7/2016.
 */

public class Room {
    int id;
    String name;
    Location location;

    public Room(int id, Location location,String name){
        this.id=id;
        this.location=location;
        this.name=name;
    }
}
