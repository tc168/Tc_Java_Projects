package com.muo.hellowold;

import android.app.Activity;
import android.os.Bundle;

import android.view.*;
import android.widget.*;

public class HelloWorldActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       View test = new DrawApoint(this);
   this.

       // System.out.print("work");
        setContentView(test);


    }
}