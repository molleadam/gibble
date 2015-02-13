package com.example.gibble;

import java.util.List;

import com.easy.facebook.android.data.Checkin;
import com.example.gibble.util.PlaceAdapter;
import com.example.gibble.util.SearchEngine;
import com.example.gibble.util.Utility;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class ChangePlaceActivity extends BaseActivity {

	SearchEngine searchEngine;
	EditText editText;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setLayoutName(R.layout.activity_change_place);
		this.setTitleBar("Cerca Luogo");
		super.onCreate(savedInstanceState,true);
		
		searchEngine = Utility.searchEngine;
		searchEngine.setActivity(this);
		editText = (EditText) findViewById(R.id.editTextPlace);
		listView = (ListView) findViewById(R.id.listview);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Checkin checkin = (Checkin) adapter.getItemAtPosition(position);
				comeBackResult(checkin);
			}
		});
		search("");
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				// TODO Auto-generated method stub
				search(editable.toString());
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_place, menu);
		return true;
	}
	
	public void search(String searchString){
		searchEngine.startSearch(searchString);
	}
	
	public void comeBackResult(Checkin checkin){
		Intent returnIntent = new Intent();
		returnIntent.putExtra("place_name",checkin.getName());
		returnIntent.putExtra("place_category",checkin.getCategory());
		setResult(RESULT_OK,returnIntent);     
		finish();
	}
	
	public void onSearchPlaceSuccess(List<Checkin> list){
		if(list!= null && !list.isEmpty()){
			PlaceAdapter adapter = new PlaceAdapter(ChangePlaceActivity.this.getApplicationContext(),R.layout.place_row,list,R.id.textViewName,R.id.textViewCategory);
			listView.setAdapter(adapter);
		}
	}

}
