package com.xia.walkingnavigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xia on 11/29/2016.
 */

public class Node {
    int id;
    List<Node> adjList;
    boolean isHW;

    public Node(int id, boolean isHW){
        this.id=id;
        this.isHW=isHW;
        adjList=new ArrayList<>();
    }
}
