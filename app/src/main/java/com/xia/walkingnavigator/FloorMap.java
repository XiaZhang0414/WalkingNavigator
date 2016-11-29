package com.xia.walkingnavigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xia on 11/9/2016.
 * Create a indoor map object, which include rooms, hallways, elevators...
 */

public class FloorMap {
    List<Room> officeList;
    List<Room> labList;
    List<Room> restRoomsList;
    List<Elevator> elevatorList;
    List<HallWay> hallWayList;

    public FloorMap(){
        officeList=new ArrayList<>();
        labList=new ArrayList<>();
        restRoomsList=new ArrayList<>();
        elevatorList=new ArrayList<>();
        hallWayList=new ArrayList<>();
    }

    public void buildMap(){
        addOffice();
        addLab();
        addRestroom();
        addElevator();
        addHallway();
    }

    public void addOffice(){
        officeList.add(new Office(0,new Location(133,120),"3.201"));
        officeList.add(new Office(1,new Location(123,120),"3.202"));
        officeList.add(new Office(2,new Location(113,120),"3.203"));
        officeList.add(new Office(3,new Location(103,120),"3.204"));
        officeList.add(new Office(4,new Location(93,120),"3.205"));
        officeList.add(new Office(5,new Location(83,120),"3.206"));
        officeList.add(new Office(6,new Location(73,120),"3.207"));
        officeList.add(new Office(7,new Location(63,120),"3.208"));
        officeList.add(new Office(8,new Location(53,120),"3.209"));
        officeList.add(new Office(9,new Location(43,120),"3.210"));
        officeList.add(new Office(10,new Location(33,120),"3.211"));
        officeList.add(new Office(11,new Location(23,120),"3.212"));
/*        officeList.add(new Office(12,new Location(0,0),"3.227"));
        officeList.add(new Office(13,new Location(0,0),"3.228"));
        officeList.add(new Office(14,new Location(0,0),"3.229"));
        officeList.add(new Office(15,new Location(0,0),"3.230"));
        officeList.add(new Office(16,new Location(0,0),"3.231"));
        officeList.add(new Office(17,new Location(0,0),"3.232"));*/
        officeList.add(new Office(18,new Location(43,18),"3.402"));
        officeList.add(new Office(19,new Location(53,18),"3.403"));
        officeList.add(new Office(20,new Location(63,18),"3.404"));
        officeList.add(new Office(21,new Location(73,18),"3.405"));
        officeList.add(new Office(22,new Location(83,18),"3.406"));
        officeList.add(new Office(23,new Location(93,18),"3.407"));
        officeList.add(new Office(24,new Location(103,18),"3.408"));
        officeList.add(new Office(25,new Location(113,18),"3.409"));
        officeList.add(new Office(26,new Location(123,18),"3.410"));
        officeList.add(new Office(27,new Location(128,18),"3.411"));
/*        officeList.add(new Office(28,new Location(0,0),"3.602"));
        officeList.add(new Office(29,new Location(0,0),"3.603"));
        officeList.add(new Office(30,new Location(0,0),"3.604"));
        officeList.add(new Office(31,new Location(0,0),"3.605"));
        officeList.add(new Office(32,new Location(0,0),"3.606"));
        officeList.add(new Office(33,new Location(0,0),"3.607"));
        officeList.add(new Office(34,new Location(0,0),"3.608"));
        officeList.add(new Office(35,new Location(0,0),"3.609"));
        officeList.add(new Office(36,new Location(0,0),"3.610"));
        officeList.add(new Office(37,new Location(0,0),"3.611"));
        officeList.add(new Office(38,new Location(0,0),"3.701"));
        officeList.add(new Office(39,new Location(0,0),"3.702"));
        officeList.add(new Office(40,new Location(0,0),"3.703"));
        officeList.add(new Office(41,new Location(0,0),"3.704"));
        officeList.add(new Office(42,new Location(0,0),"3.705"));
        officeList.add(new Office(43,new Location(0,0),"3.706"));
        officeList.add(new Office(44,new Location(0,0),"3.707"));
        officeList.add(new Office(45,new Location(0,0),"3.708"));
        officeList.add(new Office(46,new Location(0,0),"3.801"));
        officeList.add(new Office(47,new Location(0,0),"3.908"));
        officeList.add(new Office(48,new Location(0,0),"3.909"));
        officeList.add(new Office(49,new Location(0,0),"3.910"));*/
    }

    public void addLab(){
        labList.add(new Lab(50,new Location(50,116),"3.213"));
        labList.add(new Lab(51,new Location(70,116),"3.214"));
        labList.add(new Lab(52,new Location(90,116),"3.215"));
        labList.add(new Lab(53,new Location(110,116),"3.216"));
        labList.add(new Lab(54,new Location(130,116),"3.217"));
/*        labList.add(new Lab(55,new Location(0,0),"3.218"));
        labList.add(new Lab(56,new Location(0,0),"3.219"));
        labList.add(new Lab(57,new Location(0,0),"3.220"));
        labList.add(new Lab(58,new Location(0,0),"3.221"));
        labList.add(new Lab(59,new Location(0,0),"3.222"));
        labList.add(new Lab(60,new Location(0,0),"3.613"));
        labList.add(new Lab(61,new Location(0,0),"3.614"));
        labList.add(new Lab(62,new Location(0,0),"3.615"));
        labList.add(new Lab(63,new Location(0,0),"3.616"));
        labList.add(new Lab(64,new Location(0,0),"3.617"));*/
        labList.add(new Lab(65,new Location(130,53),"3.618"));
        labList.add(new Lab(66,new Location(110,53),"3.619"));
        labList.add(new Lab(67,new Location(90,53),"3.620"));
        labList.add(new Lab(68,new Location(70,53),"3.621"));
        labList.add(new Lab(69,new Location(50,53),"3.622"));
        labList.add(new Lab(70,new Location(30,53),"3.623"));
        labList.add(new Lab(71,new Location(130,22),"3.412"));
        labList.add(new Lab(72,new Location(110,22),"3.413"));
        labList.add(new Lab(73,new Location(90,22),"3.414"));
        labList.add(new Lab(74,new Location(70,22),"3.415"));
        labList.add(new Lab(75,new Location(50,22),"3.416"));
        labList.add(new Lab(76,new Location(30,22),"3.417"));
    }

    public void addRestroom(){
        restRoomsList.add(new RestRoom(77,new Location(10,91),"3.3R1","F"));
        restRoomsList.add(new RestRoom(78,new Location(10,98),"3.3R2","M"));
    }

    public void addElevator(){
        elevatorList.add(new Elevator(0,new Location(156,66)));
        elevatorList.add(new Elevator(1,new Location(160,66)));
    }

    public void addHallway(){
        hallWayList.add(new HallWay(0,"EW",new Location(0,20),new Location(153,20)));
        hallWayList.add(new HallWay(1,"EW",new Location(0,51),new Location(153,51)));
        hallWayList.add(new HallWay(2,"EW",new Location(0,118),new Location(153,118)));
        hallWayList.add(new HallWay(3,"NS",new Location(5,0),new Location(5,138)));
        hallWayList.add(new HallWay(4,"NS",new Location(148,0),new Location(148,138)));
    }
}
