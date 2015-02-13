package com.example.gibble;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView{

	Context context;
	String ttfName;
	
	public MyTextView(Context context,AttributeSet attrs){
		super(context,attrs);
		this.context = context;
		this.ttfName = attrs.getAttributeValue( "http://schemas.android.com/apk/lib/com.example.gibble", "ttf_name");
		
		init();
	}
	
	protected void init() {
        Typeface font = Typeface.createFromAsset(context.getAssets(), ttfName);
        setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf) {
    	// TODO Auto-generated method stub
        super.setTypeface(tf);
    }
}
