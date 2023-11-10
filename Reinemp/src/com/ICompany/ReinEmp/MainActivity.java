package com.ICompany.ReinEmp;

import com.ICompany.ReinEmp.game.MainReinemp;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class MainActivity {

	public static void main(String[] args) {
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(800, 480);
		MainReinemp mainGame = new MainReinemp();
		Lwjgl3Application app = new Lwjgl3Application(mainGame, config);
	}
}
