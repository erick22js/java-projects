package com.idcorporation.redoom64;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.idcorporation.redoom64.gameplay.Main;

public class MainActivity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		Lwjgl3Application app = new Lwjgl3Application(new Main(), config);
		
	}

}
