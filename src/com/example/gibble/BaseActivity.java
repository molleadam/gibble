package com.example.gibble;

import com.easy.facebook.android.apicall.GraphApi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseActivity extends Activity {

	private int layout;
	private String title_bar;
	private int descr;
	
	
	protected void onCreate(Bundle savedInstanceState,boolean setLayout) {
		super.onCreate(savedInstanceState);
		
		if(setLayout){
			setContentView(R.layout.layout);
			LinearLayout linearlayout1 = (LinearLayout) findViewById(R.id.linearlayout1);
			View template = getLayoutInflater().inflate(layout, null);
			linearlayout1.addView(template);
			
			//setto il titolo dell header
			TextView title_bar = (TextView) findViewById(R.id.title_bar);
			title_bar.setText(this.title_bar);
			
			//setto la descrizione della view
			//TextView description = (TextView) findViewById(R.id.activity_description);
			//description.setText(getResources().getString(this.descr));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.layout, menu);
		return true;
	}
	
	public void onBack(View v){
		onBackPressed();
		
	}
	
	public void setDescription(int description){
		this.descr = description;
	}
	
	public void setLayoutName(int name){
		this.layout = name;
	}
	
	public void setTitleBar(String name){
		this.title_bar = name;
	}

}
