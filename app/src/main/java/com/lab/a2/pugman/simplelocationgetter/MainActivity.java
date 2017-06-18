package com.lab.a2.pugman.simplelocationgetter;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import pugman.com.simplelocationgetter.SimpleLocationGetter;

public class MainActivity extends AppCompatActivity implements SimpleLocationGetter.OnLocationGetListener{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SimpleLocationGetter getter = new SimpleLocationGetter(this, this);
		getter.getLastLocation();
	}

	@Override
	public void onLocationReady(Location location){
		Log.d("LOCATION", "onLocationReady: lat="+location.getLatitude() + " lon="+location.getLongitude());
	}

	@Override
	public void onError(String error){
		Log.e("LOCATION", "Error: "+error);
	}
}
