package com.xia.walkingnavigator;

/**
 * Created by Xia on 11/9/2016.
 */

public class HallWay {
    int id;
    String direction;
    Location start;
    Location end;

    public HallWay(int id, String direction, Location start, Location end){
        this.id=id;
        this.direction=direction;
        this.start=start;
        this.end=end;
    }
}
