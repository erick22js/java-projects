package com.circuitsimulator;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class MainActivity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(800, 480);
		Lwjgl3Application app = new Lwjgl3Application(new WoltexGame(), config);
	}
	
}
