package com.popcap.pvz_lgc;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.popcap.gaming.Device;
import com.popcap.gaming.Gaming;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class MainActivity extends AndroidApplication {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		Device.mobile = true;
		
		initialize(new Gaming(), cfg);
	}
}