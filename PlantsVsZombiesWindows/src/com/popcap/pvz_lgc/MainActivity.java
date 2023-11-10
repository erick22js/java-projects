package com.popcap.pvz_lgc;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.popcap.gaming.Device;
import com.popcap.gaming.Gaming;


public class MainActivity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Lwjgl3ApplicationConfiguration app = new Lwjgl3ApplicationConfiguration();
		app.setWindowedMode(1200, 600);
		//app.setIdleFPS(30);
		Device.mobile = false;
		new Lwjgl3Application(new Gaming(), app);
		
		
	}

}
