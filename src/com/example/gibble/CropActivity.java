package com.example.gibble;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CropActivity extends BaseActivity {

	Bitmap bitmap;
	Uri selectedImage;
	boolean measuredFirstTime = true;
	ScrollView sv;
	HorizontalScrollView sh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setLayoutName(R.layout.activity_crop);
		this.setTitleBar("Ritaglia");
		this.setDescription(R.string.crop_activity_description);
		super.onCreate(savedInstanceState,true);
		
		selectedImage = Uri.parse(getIntent().getExtras().getString("selected_image"));
		
		sv = (ScrollView) findViewById(R.id.scrollview);
		sh = (HorizontalScrollView) findViewById(R.id.horizontalScrollview);
		ViewTreeObserver vto = sv.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				if(measuredFirstTime){
					adjustBitmap();
					ImageView iv = (ImageView) findViewById(R.id.imageView);
					iv.setImageBitmap(bitmap);
					measuredFirstTime = false;
				}
				
			}
		});
		
		
		 
		try {
			bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crop, menu);
		return true;
	}
	
	
	public void onCrop(View v){
		//ritaglio il bitmap
		
		int height;
		int width;
		int x;
		int y;
		
		x = sh.getScrollX();
		y = sv.getScrollY();
		width = sv.getWidth();
		height = sv.getHeight();
		Bitmap croppeBitmap = Bitmap.createBitmap(bitmap, x, y, width, height);
		
		
		Intent i = new Intent(this, SelectskinActivity.class);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		croppeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bs);
		i.putExtra("byteArray", bs.toByteArray());
		startActivity(i);
	}
	
	private void adjustBitmap(){
		
		//verifico l'orientamento dell'immagine.
		
		int rotate = getOrientation(getApplicationContext(), selectedImage);	
		Matrix matrix = new Matrix();
		matrix.postRotate(rotate);
		this.bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),matrix,true);
		
		
		
		//RESIZING DELL'IMMAGINE
		int width;
		int height;
		float scale;
		
		if(bitmap.getWidth() > bitmap.getHeight()){
			height = sv.getHeight();
			scale = (float)height/(float)bitmap.getHeight();
			width = (int) (bitmap.getWidth()*scale);
		}else{
			width = sv.getWidth();
			scale = (float)width/(float)bitmap.getWidth();
			height = (int) (bitmap.getHeight()*scale);
		}
		this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
		
	}
	
	public static int getOrientation(Context context,Uri photoUri) {
	    /* it's on the external media. */
	    
		Cursor cursor = context.getContentResolver().query(photoUri,
	            new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

	    if (cursor.getCount() != 1) {
	        return -1;
	    }

	    cursor.moveToFirst();
	    return cursor.getInt(0);
	}

}
