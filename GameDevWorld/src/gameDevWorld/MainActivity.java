package gameDevWorld;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import gameDevWorld.gameplay.GameActivity;

public class MainActivity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lwjgl3ApplicationConfiguration appConfig = new Lwjgl3ApplicationConfiguration();
		Lwjgl3Application app = new Lwjgl3Application(new GameActivity(), appConfig);
	}

}
