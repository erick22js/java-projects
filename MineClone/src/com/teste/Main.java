package com.teste;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import voxelEngine.VoxelGame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Lwjgl3ApplicationConfiguration appConfig = new Lwjgl3ApplicationConfiguration();
		appConfig.setWindowedMode(800, 480);
		Lwjgl3Application app = new Lwjgl3Application(new VoxelGame(), appConfig);
		
	}

}
