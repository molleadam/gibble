package com.example.gibble.skin;
import com.example.gibble.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BaseSkin {
	protected int skin;
	
	private Bitmap bitmap;
	private RelativeLayout container;
	protected Activity context;
	
	private Typeface typeface = null;
	private final Config BITMAP_CONFIG = android.graphics.Bitmap.Config.ARGB_8888;
	
	
	public BaseSkin(int skin,RelativeLayout relativelayout,Activity context){
		this.container = relativelayout;
		this.skin = skin;
		this.context = context;
		show();
		
	}
	
	
	public RelativeLayout getContainer() {
		return container;
	}
	public void setContainer(RelativeLayout container) {
		this.container = container;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getSkinName() {
		return skin;
	}
	public void setSkinName(int skin) {
		this.skin = skin;
	}
	
	
	//questo metodo torna l'orario corrente
	public String getHourString(){
		String hour = "";
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		
		hour = today.format("%k:%M");
		return hour;
	}
	
	public String getDateString(){
		String date = "";
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		
		date = today.monthDay+"/"+today.month+"/"+today.year;
		return date;
	}
	
	public Bitmap draw(){
		
		//converto il container il bitmap
		Bitmap bitmap = Bitmap.createBitmap(container.getWidth(), container.getHeight(),BITMAP_CONFIG);
		Canvas canvas = new Canvas(bitmap);
		container.draw(canvas);
		return bitmap;
	}
	
	public void show(){
		
		LayoutInflater inflater =  context.getLayoutInflater();
		View child = inflater.inflate(this.skin, null);
		child = (ViewGroup) child;
		android.view.ViewGroup.LayoutParams params = new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,container.getHeight());
		child.setLayoutParams(params);
		container.addView(child);
	}
	
	
}
