package com.survivalgame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.survivalgame.game.MainGame;

public class MainActivity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		
		Lwjgl3Application app = new Lwjgl3Application(new MainGame(), cfg);
		
	}

}
