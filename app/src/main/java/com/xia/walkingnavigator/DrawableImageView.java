package com.xia.walkingnavigator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.List;

/**
 * Created by Xia on 11/20/2016.
 */

public class DrawableImageView extends SubsamplingScaleImageView {
    final int map_width=153;
    final int map_height=138;
    Location location;
    List<Location> path;

    public DrawableImageView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public void setCurrentLocation(Location location){
        this.location=location;
    }

    public void setPath(List<Location> path){
        this.path=path;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int height = this.getMeasuredHeight();
        int width = this.getMeasuredWidth();

        int x=(int)(width*(double)location.x/map_width);
        int y=(int)(height*(double)location.y/map_height);

        Paint p=new Paint();
        p.setColor(Color.BLUE);
        canvas.drawCircle(x,y,10,p);

        if(path!=null){
//            Log.d("onDraw","Drawing line");
            Location prev=path.get(0);
            p.setColor(Color.GREEN);
            p.setStrokeWidth(10);

            for(int i=1;i<path.size();i++){
                Location cur=path.get(i);
                canvas.drawLine((int)(width*(double)prev.x/map_width),(int)(height*(double)prev.y/map_height),(int)(width*(double)cur.x/map_width),(int)(height*(double)cur.y/map_height),p);
                prev=cur;
            }

            path=null;
        }
    }
}
