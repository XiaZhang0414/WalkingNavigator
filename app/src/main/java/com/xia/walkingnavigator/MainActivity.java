package com.xia.walkingnavigator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

/*    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView mTextView;
    private TextView direction;*/
//    SubsamplingScaleImageView imageView;
    DrawableImageView imageView;
    Spinner rooms;
    private ImageView loc_dot;

    private int mStep=0;
    private long prev_time=0;
    private long time=0;
    private int instruction_idx=0;
    private String[] instructions={"Go straight, walking 25 steps", "Turn left, walking 30 steps", "You arrived"};
    private int[] steps={25,30};
    private boolean countingFlag=true;

    private DBManager mgr;

    private int source;
    private int dest;
    private MapGraph mapGraph;

    /**********
     * 1. initialize components
     * 2. load map
     * 3. build spinner list
     * 4. set step sensor manager
     * 5. set a dummy current location (for test)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        mTextView=(TextView)findViewById(R.id.text);
        direction=(TextView)findViewById(R.id.direction);*/
        imageView = (DrawableImageView) findViewById(R.id.imageView);
        rooms = (Spinner) findViewById(R.id.rooms);

        imageView.setImage(ImageSource.resource(R.drawable.map));
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("Position: ", "(" + motionEvent.getX() + ", " + motionEvent.getY() + ")");
                return false;
            }
        });

        if (savedInstanceState != null) {
            mStep = savedInstanceState.getInt("mStep");
            prev_time = savedInstanceState.getLong("prev_time");
            time = savedInstanceState.getLong("time");
            instruction_idx = savedInstanceState.getInt("instruction index");
            countingFlag = savedInstanceState.getBoolean("counting flag");
        }

        /**********
         Setting step sensor
         ***********/
/*        mTextView.setText(Integer.toString(mStep));
        direction.setText(instructions[instruction_idx]);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorManager.registerListener(mSensorEventListener,mSensor,mSensorManager.SENSOR_DELAY_FASTEST);*/

        //builde DB
        mgr = DBManager.getDBManager(this);
        mapGraph=new MapGraph(mgr);
        mapGraph.buildGraph();
        source=3;

        //set drop down list
        List<String> roomNo = new ArrayList<>();
        final List<Room> roomList=mgr.queryRoom();
        for (Room s : roomList)
            roomNo.add(s.name);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomNo);
        rooms.setAdapter(adapter);
        rooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dest=roomList.get(i).id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imageView.setCurrentLocation(new Location(93, 120)); //the curent location
        imageView.invalidate();
    }

    public void getRoute(View view) {
//        imageView.setCurrentLocation(new Location(93, 120));
        /*Location[] path=new Location[4];
        path[0]=new Location(93,120);
        path[1]=new Location(130,120);
        path[2]=new Location(130,22);
        path[3]=new Location(63,22);*/
        imageView.setPath(mapGraph.getPath(source,dest));
        imageView.invalidate();
    }

/*    private SensorEventListener mSensorEventListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            time= event.timestamp;
            if(prev_time==0||time-prev_time>=200000000) {
                mStep++;
                if(countingFlag)
                    mTextView.setText(Integer.toString(mStep));
                prev_time=time;

                if(countingFlag&&mStep==steps[instruction_idx]) {
                    direction.setText(instructions[++instruction_idx]);
                    mStep=0;

                    if(instruction_idx==instructions.length-1)
                        countingFlag=false;
                }
            }
        }
    };*/

    //deal with variables when screen configuration changes
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("mStep", mStep);
        savedInstanceState.putLong("prev_time", prev_time);
        savedInstanceState.putLong("time", time);
        savedInstanceState.putInt("instruction index",instruction_idx);
        savedInstanceState.putBoolean("counting flag",countingFlag);
    }
}
