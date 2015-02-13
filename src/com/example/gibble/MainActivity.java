package com.example.gibble;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends BaseActivity {
	private static final int CAMERA_REQUEST = 1888; 
	private static final int GALLERY_REQUEST = 1889; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setLayoutName(R.layout.activity_main);
		this.setTitleBar("Carica");
		this.setDescription(R.string.main_activity_description);
		super.onCreate(savedInstanceState,true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	public void changePlace(View v){
		Intent intent = new Intent(this,ChangePlaceActivity.class);
		startActivity(intent);
	}
	
	public void onNext(View v){
		Intent intent = new Intent(this,CropActivity.class);
		startActivity(intent);
	}

	public void onUpload(View v ){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
	}
	
	public void onShot(View V){
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
        startActivityForResult(cameraIntent, CAMERA_REQUEST); 
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		if ((requestCode == CAMERA_REQUEST || requestCode == GALLERY_REQUEST) && resultCode == RESULT_OK){   
			Uri selectedImageUri = data.getData();
			Intent intent = new Intent(this,CropActivity.class);
			intent.putExtra("selected_image", selectedImageUri.toString());
			
			startActivity(intent);
		}
        
	} 
	
	public void onBack(View v){
	    moveTaskToBack(true);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        moveTaskToBack(true);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
