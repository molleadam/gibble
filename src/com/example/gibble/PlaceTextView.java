package com.example.gibble;

import java.util.List;

import com.easy.facebook.android.data.Checkin;
import com.example.gibble.util.SearchEngine;
import com.example.gibble.util.Utility;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class PlaceTextView extends MyTextView{

	SearchEngine searchEngine;
	
	public PlaceTextView(Context context,AttributeSet attrs){
		super(context,attrs);
		 init();
	}
	
	public void init(){
		super.init();
		if(Utility.nearbyPlace != null)
				this.setText(Utility.nearbyPlace);
			else 
				this.setText("scrivi luogo");
		}
		
	public void ShowDialogDetails(View v){
		
		
		TextView clickedTextView = (TextView) v;
		//Inizializzo il Dialog
		ModelDialog modelDialog = new ModelDialog(Utility.context,R.layout.model_dialog,"CAMBIA IL TESTO",clickedTextView);
		
		//Show della Dialog
		modelDialog.show();
	}
	

}
