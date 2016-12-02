package com.xia.walkingnavigator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by Xia on 11/7/2016.
 * Create a DB
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "floor.db";
    private static final int DATABASE_VERSION = 1;
    private static FloorMap map;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create tables
        db.execSQL("CREATE TABLE IF NOT EXISTS room (_id INTEGER PRIMARY KEY, x INTEGER, y INTEGER, name VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS office (_id INTEGER PRIMARY KEY REFERENCES room(_id), name VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS lab (_id INTEGER PRIMARY KEY REFERENCES room(_id), name VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS restroom (_id INTEGER PRIMARY KEY REFERENCES room(_id), gender VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS hallway (_id INTEGER PRIMARY KEY, direction VARCHAR, start_x INTEGER, start_y INTEGER, end_x INTEGER, end_y INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS elevator (_id INTEGER PRIMARY KEY, x INTEGER, y INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS room_hw_adj (room_id INTEGER REFERENCES room(_id), hw_id INTEGER REFERENCES hallway(_id), x INTEGER, y INTEGER, CONSTRAINT cons PRIMARY KEY (room_id,hw_id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS hw_adj (hw_id1 INTEGER REFERENCES hallway(_id), hw_id2 INTEGER REFERENCES hallway(_id), x INTEGER, y INTEGER, CONSTRAINT cons PRIMARY KEY (hw_id1,hw_id2))");
//        db.execSQL("CREATE TABLE IF NOT EXISTS elev_hw_adj (elev_id INTEGER REFERENCES elevator(_id), hw_id INTEGER REFERENCES hallway(_id), CONSTRAINT cons PRIMARY KEY (elev_id,hw_id))");
//        db.execSQL("CREATE TABLE IF NOT EXISTS office_adj (office_id INTEGER PRIMARY KEY REFERENCES office(_id), left_id INTEGER REFERENCES office(_id), right_id INTEGER REFERENCES office(_id))");
//        db.execSQL("CREATE TABLE IF NOT EXISTS lab_adj (lab_id INTEGER PRIMARY KEY REFERENCES lab(_id), left_id INTEGER REFERENCES lab(_id), right_id INTEGER REFERENCES office(_id))");
//        db.execSQL("CREATE TABLE IF NOT EXISTS restroom_adj (f_id INTEGER PRIMARY KEY REFERENCES restroom(_id), m_id INTEGER REFERENCES restroom(_id))");

        buildDB(db);
    }

    public void buildDB(SQLiteDatabase db){
        map=new FloorMap(); //create a map object
        map.buildMap();

        //add data to SQL
        addRoom(map.officeList,db);
        addRoom(map.labList,db);
        addRoom(map.restRoomsList,db);

        addElevator(map.elevatorList,db);
        addHallWay(map.hallWayList,db);
        addRoomHWAdj(map.room_hw_adj,db);
        addHWAdj(map.hw_adj,db);

/*        Cursor c=db.rawQuery("SELECT * FROM room",null);
        while (c.moveToNext()) {
            Log.d("DBHelper",c.getString(c.getColumnIndex("name")));
        }
        c.close();*/
    }

    public void addRoomHWAdj(List<Adjacent> roomhwadj, SQLiteDatabase db){
        db.beginTransaction();
        try{
            for(Adjacent adj:roomhwadj){
                db.execSQL("INSERT INTO room_hw_adj VALUES(?,?,?,?)", new Object[]{adj.id1,adj.id2,adj.location.x,adj.location.y});
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void addHWAdj(List<Adjacent> hwadj,SQLiteDatabase db){
        db.beginTransaction();
        try{
            for(Adjacent adj:hwadj){
                db.execSQL("INSERT INTO hw_adj VALUES(?,?,?,?)", new Object[]{adj.id1,adj.id2,adj.location.x,adj.location.y});
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void addRoom(List<Room> rooms, SQLiteDatabase db) {
        db.beginTransaction();
        try {
            for (Room room : rooms) {
                db.execSQL("INSERT INTO room VALUES(?,?,?,?)", new Object[]{room.id,room.location.x,room.location.y,room.name});
                if(room instanceof Office)
                    db.execSQL("INSERT INTO office VALUES(?,?)",new Object[]{room.id,((Office) room).name});
                else if(room instanceof Lab)
                    db.execSQL("INSERT INTO lab VALUES(?,?)",new Object[]{room.id,((Lab) room).name});
                else if(room instanceof RestRoom)
                    db.execSQL("INSERT INTO restroom VALUES(?,?)",new Object[]{room.id,((RestRoom) room).gender});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void addHallWay(List<HallWay> hallWayss,SQLiteDatabase db) {
        db.beginTransaction();  //开始事务
        try {
            for (HallWay hallWay : hallWayss) {
                db.execSQL("INSERT INTO hallway VALUES(?,?,?,?,?,?)", new Object[]{hallWay.id,hallWay.direction,hallWay.start.x,hallWay.start.y,hallWay.end.x,hallWay.end.y});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    public void addElevator(List<Elevator> elevators,SQLiteDatabase db){
        db.beginTransaction();  //开始事务
        try {
            for (Elevator elevator : elevators) {
                db.execSQL("INSERT INTO elevator VALUES(?,?,?)", new Object[]{elevator.id,elevator.location.x,elevator.location.y});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
