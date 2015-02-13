package com.example.gibble;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.easy.facebook.android.data.Checkin;
import com.easy.facebook.android.error.EasyFacebookError;
import com.example.gibble.skin.*;
import com.example.gibble.util.Utility;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SelectskinActivity extends BaseActivity{
	Bitmap bitmap;
	ImageView previewThumbnail;
	RelativeLayout relativeLayout;
	Dialog modelDialog;
	EditText editText ;
	TextView clickedTextView;
	BaseSkin skin = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setLayoutName(R.layout.activity_selectskin);
		this.setTitleBar("Condividi");
		this.setDescription(R.string.share_activity_decription);
		super.onCreate(savedInstanceState,true);
		
		previewThumbnail = (ImageView) findViewById(R.id.imageCroppedView);
		relativeLayout = (RelativeLayout) findViewById(R.id.layoutImageContainer);
		if(getIntent().hasExtra("byteArray")) {
		    
		    bitmap = BitmapFactory.decodeByteArray(
		        getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);        
		    previewThumbnail.setImageBitmap(bitmap);
		}
		
		skin = new BaseSkin(R.layout.skin1,relativeLayout,this);
		
		
	}
	
	public void onShare(View v){
		//salvo il bitmap nell sdcard
		boolean imageSaved = false;
		Uri savedImageUri = null;
		
		skin.setBitmap(bitmap);
		bitmap = skin.draw();
		
		if (bitmap != null && !bitmap.isRecycled()) {
			File storagePath = new File(Environment.getExternalStorageDirectory() + "/Gibble/");
			storagePath.mkdirs();
			String[] listFile = storagePath.list();
			String imageName = "Image-"+listFile.length+".jpg";
			FileOutputStream out = null;
			File imageFile = new File(storagePath, String.format("%s.jpg",imageName));
			try {
				out = new FileOutputStream(imageFile);
				imageSaved = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
			} catch (Exception e) {}
			
			ContentValues values = new ContentValues(3);
			values.put(Images.Media.TITLE, imageName);
			values.put(Images.Media.MIME_TYPE, "image/jpeg");
			values.put("_data", imageFile.getAbsolutePath()) ;
			savedImageUri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
			
		}
		
		//avvio la condivisione
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setType("image/*");
		
		intent.putExtra(Intent.EXTRA_STREAM, savedImageUri);
		startActivity(Intent.createChooser(intent,"Condividi con"));
		
		} 	
	
	public void onChangeSkin(View v){
		
		switch(v.getId()){
			case R.id.skin1 :
				skin.setSkinName(R.layout.skin1);
				break;
			case R.id.skin2 :
				skin.setSkinName(R.layout.skin2);
				break;
			case R.id.skin3 :
				skin.setSkinName(R.layout.skin3);
				break;
			case R.id.skin4 :
				skin.setSkinName(R.layout.skin4);
				break;
				
			case R.id.skin5:
				skin.setSkinName(R.layout.skin5);
				break;
		}
		if(relativeLayout.getChildCount() >1)
			relativeLayout.removeViews(2, 1);
		skin.show();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share, menu);
		return true;
	}
	
	
	public void ShowDialogDetails(View v){
		
		
		clickedTextView = (TextView) v;
		//Inizializzo il Dialog
		ModelDialog modelDialog = new ModelDialog(this,R.layout.model_dialog,"CAMBIA IL TESTO",clickedTextView);
		
		//Show della Dialog
		modelDialog.show();
	}
	
	public void changePlace(View v){
		clickedTextView = (TextView) v;
		
		if(!isOnline()){
			ModelDialog modeldialog = new ModelDialog(this,R.layout.model_dialog,"CAMBIA IL TESTO",clickedTextView);
			modeldialog.show();
		}else{
			Intent intent = new Intent(this,ChangePlaceActivity.class);
			startActivityForResult(intent, 1);
			}
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected() && Utility.graphApi != null) {
	        return true;
	    }
	    return false;
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode ==  1 && resultCode == RESULT_OK){
			clickedTextView.setText(data.getStringExtra("place_name"));
			Utility.nearbyPlace = data.getStringExtra("place_name");
		}
	}
	
	
}
