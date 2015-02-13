package com.example.gibble;

import java.util.List;

import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.Checkin;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;
import com.easy.facebook.android.facebook.LoginListener;
import com.example.gibble.util.SearchEngine;
import com.example.gibble.util.Utility;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class FacebookLoginActivity extends BaseActivity implements LoginListener {

	private FBLoginManager fbLoginManager;
	public  String KODEFUNFBAPP_ID = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,false);
		setContentView(R.layout.activity_facebook_login);
		Utility.context = this;
		KODEFUNFBAPP_ID = getResources().getString(R.string.facebook_app_id);
		connectToFacebook();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.facebook_login, menu);
		return true;
	}


	public void connectToFacebook(){

		  //read about Facebook Permissions here:
		  //http://developers.facebook.com/docs/reference/api/permissions/
		  String permissions[] = {
		    "user_about_me",
		    "user_activities",
		    "user_birthday",
		    "user_checkins",
		    "user_education_history",
		    "user_events",
		    "user_groups",
		    "user_hometown",
		    "user_interests",
		    "user_likes",
		    "user_location",
		    "user_notes",
		    "user_online_presence",
		    "user_photo_video_tags",
		    "user_photos",
		    "user_relationships",
		    "user_relationship_details",
		    "user_religion_politics",
		    "user_status",
		    "user_videos",
		    "user_website",
		    "user_work_history",
		    "email",

		    "read_friendlists",
		    "read_insights",
		    "read_mailbox",
		    "read_requests",
		    "read_stream",
		    "xmpp_login",
		    "ads_management",
		    "create_event",
		    "manage_friendlists",
		    "manage_notifications",
		    "offline_access",
		    "publish_checkins",
		    "publish_stream",
		    "rsvp_event",
		    "sms",
		    //"publish_actions",

		    "manage_pages"

		  };

		  fbLoginManager = new FBLoginManager(this,
				    R.layout.activity_facebook_login, 
				    KODEFUNFBAPP_ID, 
				    permissions);

		  if(fbLoginManager.existsSavedFacebook()){
		   fbLoginManager.loadFacebook();
		  }
		  else{
		   fbLoginManager.login();
		  }
		 }

		 @Override
		 protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data){
		  fbLoginManager.loginSuccess(data);
		 }

		 public void loginSuccess(Facebook facebook) {
			 Utility.graphApi = new GraphApi(facebook);
			 Utility.searchEngine = new SearchEngine("15000",15,Utility.graphApi);
			 
			 List<Checkin> list= Utility.searchEngine.search("*");
				if(list!= null && !list.isEmpty())
					Utility.nearbyPlace = list.get(0).getName();
				
			 
			 Intent intent = new Intent(this,MainActivity.class);
			 startActivity(intent);
	 	}

		 public void logoutSuccess() {
		  fbLoginManager.displayToast("Logout Success!");
		 }

		 public void loginFail() {
		  fbLoginManager.displayToast("Login Epic Failed!");
		 }





}
