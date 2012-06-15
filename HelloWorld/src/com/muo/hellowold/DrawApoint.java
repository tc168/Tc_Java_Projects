package com.muo.hellowold;
import android.content.*;
import android.graphics.*;
import android.view.*;


import java.util.*;

public class DrawApoint extends View {
public Paint paint;
static float touchx;
static float touchy;

public static int counter;
public static Vector<Float> changea = new Vector<Float>();
float[] pt  = new float [1000];
	public DrawApoint(Context context) {
		super(context);
		paint = new Paint();
		this.setFocusableInTouchMode(true);
		// TODO Auto-generated constructor stub
	}
@Override
	protected void onDraw(Canvas canvas){
	paint.setColor(Color.GREEN);	
	
	for (int ctr = 0 ; ctr <= counter-1;ctr += 2){
	//canvas.drawPoint(pt[ctr], pt[ctr+1], paint);
		
	canvas.drawCircle(pt[ctr], pt[ctr+1], 3, paint);
	canvas.rotate( counter, canvas.getWidth()/2 ,canvas.getHeight()/2);
	
	
	}
	
			
			
	
	//canvas.drawPath(changea.toArray(), paint);
	canvas.drawLine(touchx, touchy, canvas.getWidth()/2, canvas.getHeight()/2, paint);
	canvas.drawText("tc168", touchx, touchy, paint);

	//canvas.drawCircle(pt[ctr], pt[ctr+1], 10, paint);
	
	
	//canvas.drawPoints(pts, paint);
	//canvas.drawLines(pt, paint);
	try{
		Thread.sleep(30);
	}catch (InterruptedException e){}
	invalidate();
}
@Override 
public boolean onTouchEvent(MotionEvent event){
	pt[counter++]=event.getX();
	pt[counter++]=event.getY();
	
	touchx = event.getX();
	touchy= event.getY();

	return true;
}
}
