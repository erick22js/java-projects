package com.popcap.gaming.Resources;

import org.jsonJava.JSONArray;
import org.jsonJava.JSONObject;
import com.badlogic.gdx.graphics.Texture;

public class DataReanims {
	
	public static Texture peashooter_main;
	
	
	public static void preloadPlantsReanim() {
		peashooter_main = new Texture(Resources.loadAsset("reanim/PeaShooterSingle.png"));
	}
}
