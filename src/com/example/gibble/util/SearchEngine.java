package com.example.gibble.util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.Checkin;
import com.easy.facebook.android.error.EasyFacebookError;
import com.example.gibble.ChangePlaceActivity;
import com.example.gibble.SelectskinActivity;

public class SearchEngine extends Thread implements LocationListener {

	GraphApi graphApi;
	private String latitude = "41.490609467954";
	private String longitude = "13.829706275661";
	private String radius;
	private int limit;
	private Context context;
	private Activity activity;
	private LocationManager locationManager;
	private String provider;
	private boolean enabledSearch;
	private String name;
	List<Checkin> list;
	
	public SearchEngine(String radius,int limit,GraphApi graphApi){
		this.limit = limit;
		this.radius = radius;
		this.graphApi = graphApi;
		this.context = Utility.context;
		init();
		
	}
	
	public void run(){
		
		while(true){
			if(enabledSearch){
				list = this.search(name);
				activity.runOnUiThread(new Runnable() {  
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                		((ChangePlaceActivity) activity).onSearchPlaceSuccess(list);
                    }
				});
				this.enabledSearch = false;
			}
		}
	}
	
	public void setActivity(Activity activity){
		this.activity = activity;
	}
	public void init(){
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		provider = locationManager.getBestProvider(new Criteria(), false);
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
		      System.out.println("Provider " + provider + " has been selected.");
		      onLocationChanged(location);
		    } else {
		      latitude ="Location not available";
		      longitude = "Location not available";
		    }
		this.start();

	}
	
	public void startSearch(String name){
		this.name = name;
		this.enabledSearch = true;
	}
	
	 public List<Checkin> search(String name){
		
		if(name.isEmpty()){
			name = "*";
			System.out.println(name);
			}
		List<Checkin> list = null;
		try {
			
			list = graphApi.searchLocation(name,latitude,longitude, radius, limit);
		} catch (EasyFacebookError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = String.valueOf(location.getLatitude());
		longitude = String.valueOf(location.getLongitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
