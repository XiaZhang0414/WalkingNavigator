package com.xia.walkingnavigator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xia on 11/7/2016.
 */

public class DBManager {
    private static DBHelper helper;
    private static SQLiteDatabase db;
    private static FloorMap map;

    private static DBManager mgr;

    private DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    public static DBManager getDBManager(Context context){
        if(mgr==null)
            mgr=new DBManager(context);
        return mgr;
    }

    public List<Room> queryRoom() {
        ArrayList<Room> rooms = new ArrayList<Room>();
        Cursor c = db.rawQuery("SELECT * FROM room", null);
        while (c.moveToNext()) {
            Room room = new Room(c.getInt(c.getColumnIndex("_id")),new Location(c.getInt(c.getColumnIndex("x")),c.getInt(c.getColumnIndex("y"))),c.getString(c.getColumnIndex("name")));
            rooms.add(room);
        }
        c.close();
        return rooms;
    }

    public List<Office> queryOffice() {
        ArrayList<Office> offices = new ArrayList<Office>();
        Cursor c = db.rawQuery("SELECT * FROM office INNER JOIN room ON office._id=room._id", null);
        while (c.moveToNext()) {
            Office office = new Office(c.getInt(c.getColumnIndex("_id")),new Location(c.getInt(c.getColumnIndex("x")),c.getInt(c.getColumnIndex("y"))),c.getString(c.getColumnIndex("name")));
            offices.add(office);
        }
        c.close();
        return offices;
    }

    public Cursor queryOfficeCursor() {
        Cursor c = db.rawQuery("SELECT * FROM office INNER JOIN room ON office._id=room._id", null);
        return c;
    }

    public List<Lab> queryLab() {
        ArrayList<Lab> labs = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM lab INNER JOIN room ON lab._id=room._id", null);
        while (c.moveToNext()) {
            Lab lab = new Lab(c.getInt(c.getColumnIndex("_id")),new Location(c.getInt(c.getColumnIndex("x")),c.getInt(c.getColumnIndex("y"))),c.getString(c.getColumnIndex("name")));
            labs.add(lab);
        }
        c.close();
        return labs;
    }

    public Map<Integer, Integer> queryRoom_HW_Adj() {
        Cursor c = db.rawQuery("SELECT * FROM room_hw_adj", null);
        Map<Integer,Integer> map=new HashMap<>();
        while (c.moveToNext()) {
            Lab lab = new Lab(c.getInt(c.getColumnIndex("_id")),new Location(c.getInt(c.getColumnIndex("x")),c.getInt(c.getColumnIndex("y"))),c.getString(c.getColumnIndex("name")));
            map.put(c.getInt(c.getColumnIndex("room_id")),c.getInt(c.getColumnIndex("hw_id")));
        }
        c.close();
        return map;
    }

    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM room", null);
        return c;
    }

    public void closeDB() {
        db.close();
    }
}
