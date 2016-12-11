package com.xia.walkingnavigator;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

        private SensorManager mSensorManager;
        private Sensor mSensor;
        private TextView mStepView;
        private TextView direction;
    DrawableImageView imageView;
    Spinner rooms;

    private int mStep = 0;
    private long prev_time = 0;
    private long time = 0;
    private int instruction_idx = 0;
    private char dir;

    private List<String> instructions;
    private List<Integer> stepss;
    private List<Character> dirs;
    private List<Location> path;
    private Location curloc;
    private boolean countingFlag = false;

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
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStepView=(TextView)findViewById(R.id.step);
        direction=(TextView)findViewById(R.id.direction);
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
//        mTextView.setText(Integer.toString(mStep));

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorManager.registerListener(mSensorEventListener,mSensor,mSensorManager.SENSOR_DELAY_FASTEST);

        //builde DB
        mgr = DBManager.getDBManager(this);
        mapGraph = new MapGraph(mgr);
        mapGraph.buildGraph();
        source = 3;

        //set drop down list
        List<String> roomNo = new ArrayList<>();
        final List<Room> roomList = mgr.queryRoom();
        for (Room s : roomList)
            roomNo.add(s.name);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomNo);
        rooms.setAdapter(adapter);
        rooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dest = roomList.get(i).id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        curloc=new Location(93,120);
        imageView.setCurrentLocation(curloc); //the curent location
        imageView.invalidate();
    }

    public void getRoute(View view) {
//        imageView.setCurrentLocation(new Location(93, 120));
        path = mapGraph.getPath(source, dest);
        source=dest;
        imageView.setPath(path);
        imageView.invalidate();

        createInstructions(path);
        for(String s:instructions)
            Log.d("MainActivity",s);
        instruction_idx=0;
        dir=dirs.get(instruction_idx);
        countingFlag=true;
        direction.setText(instructions.get(instruction_idx));
    }

    public void createInstructions(List<Location> path) {
        List<String> listStr = new ArrayList<>();
        List<Integer> listSteps=new ArrayList<>();
        List<Character> directions=new ArrayList<>();

        for (int i = 0; i < path.size(); i++) {
            String s = "";
            int steps=0;
            if (i == 0) {
                Location cur = path.get(i);
                Location next = path.get(i + 1);
                if (cur.x == next.x) {
                    if (cur.y > next.y) {
                        s += "Go North, ";
                        directions.add('N');
                    }
                    else {
                        s += "Go South, ";
                        directions.add('S');
                    }
                    steps=Math.abs(cur.y - next.y);
                    s += "Walking " + steps + " steps";
                } else if (cur.y == next.y) {
                    if (cur.x > next.x) {
                        s += "Go West, ";
                        directions.add('W');
                    }
                    else {
                        s += "Go East, ";
                        directions.add('E');
                    }
                    steps=Math.abs(cur.x - next.x);
                    s += "Walking " + steps + " steps";
                }

                listSteps.add(steps);
                listStr.add(s);
            }else if(i==path.size()-1){
                listStr.add("You Arrive");
            }else{
                Location cur=path.get(i);
                Location prev=path.get(i-1);
                Location next=path.get(i+1);

                Location dir_prev=new Location(cur.x-prev.x,cur.y-prev.y);
                Location dir_next=new Location(next.x-cur.x,next.y-cur.y);

                if((dir_prev.x>0&&dir_next.y<0)||(dir_prev.x<0&&dir_next.y>0)||(dir_prev.y>0&&dir_next.x>0)||(dir_prev.y<0&&dir_next.x<0)){
                    s+="Turn Left, ";
                }else{
                    s+="Turn Right, ";
                }

                if(dir_next.x>0)
                    directions.add('E');
                else if(dir_next.x<0)
                    directions.add('W');
                else if(dir_next.y>0)
                    directions.add('S');
                else
                    directions.add('N');

                steps=Math.max(Math.abs(next.x-cur.x),Math.abs(next.y-cur.y));
                s+="Walking "+steps+" steps";
                listSteps.add(steps);
                listStr.add(s);
            }
        }

        instructions=listStr;
        stepss=listSteps;
        dirs=directions;
    }

    private SensorEventListener mSensorEventListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            time= event.timestamp;
            if(prev_time==0||time-prev_time>=100000000) {
                mStep++;
                if(countingFlag) {
                    mStepView.setText(Integer.toString(mStep));
                    imageView.setPath(path);
                    switch(dir) {
                        case 'E':
                            imageView.setCurrentLocation(new Location(curloc.x + mStep, curloc.y)); //the curent location
                            break;
                        case 'W':
                            imageView.setCurrentLocation(new Location(curloc.x - mStep, curloc.y));
                            break;
                        case 'N':
                            imageView.setCurrentLocation(new Location(curloc.x, curloc.y - mStep));
                            break;
                        case 'S':
                            imageView.setCurrentLocation(new Location(curloc.x, curloc.y + mStep));
                    }
                    imageView.invalidate();
                }
                prev_time=time;

                if(countingFlag&&mStep==stepss.get(instruction_idx)) {
                    direction.setText(instructions.get(++instruction_idx));
                    curloc=path.get(instruction_idx);
                    if(instruction_idx<dirs.size())
                        dir=dirs.get(instruction_idx);
                    mStep=0;
                    mStepView.setText(Integer.toString(mStep));

                    if(instruction_idx==instructions.size()-1)
                        countingFlag=false;
                }
            }
        }
    };

    //deal with variables when screen configuration changes
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("mStep", mStep);
        savedInstanceState.putLong("prev_time", prev_time);
        savedInstanceState.putLong("time", time);
        savedInstanceState.putInt("instruction index",instruction_idx);
        savedInstanceState.putBoolean("counting flag",countingFlag);
    }
}
