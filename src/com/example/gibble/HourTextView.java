package com.example.gibble;

import android.content.Context;
import android.text.format.Time;
import android.util.AttributeSet;

public class HourTextView extends MyTextView{

	public HourTextView(Context context,AttributeSet attrs){
		super(context,attrs);
		 init();
	}
	
	public void init(){
		super.init();
		
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		this.setText(today.format("%H:%M:%S").toString());
	}
}
