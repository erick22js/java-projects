package com.Techne;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class MainActivity {

	public static void main(String[] args) {
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(800, 480);
		config.setTitle("Techne");
		
		Lwjgl3Application app = new Lwjgl3Application(new TechneMGame(), config);
		
	}

}
