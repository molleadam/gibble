package com.example.gibble;

import java.util.List;

import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.Checkin;
import com.easy.facebook.android.error.EasyFacebookError;


import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ModelDialog extends Dialog{

	EditText editText = null;
	Button button = null;
	TextView clickedElement = null;
	
	
	public ModelDialog(Context context,int layout,String title,TextView clickedElement){
		super(context);
		this.setContentView(R.layout.model_dialog);
		this.setTitle(title);
		this.clickedElement = clickedElement;
		
		editText= (EditText) findViewById(R.id.modelDialogText);
		editText.setText(clickedElement.getText());
		
		//set Del button
		button = (Button) this.findViewById(R.id.button1);
		//aggiungo il listener dell’onclick per la chiusura della dialog
		button.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ModelDialog.this.clickedElement.setText(editText.getText().toString());
				ModelDialog.this.cancel();
			}
		});
	}
	
	
	
}
