package com.popcap.gaming.Resources;

import com.badlogic.gdx.graphics.Texture;

public class DataImages {
	//**************************************************Backgrounds**************************
	
	public static Texture backgroundFrontDay;
	public static Texture backgroundFrontDayGameoverOverlay;
	public static Texture backgroundFrontDayGameoverMask;
	public static Texture backgroundFrontNight;
	public static Texture backgroundFrontNightGameoverOverlay;
	public static Texture backgroundFrontNightGameoverMask;
	public static Texture backgroundPoolDay;	
	public static Texture backgroundPoolDayGameoverOverlay;	
	public static Texture backgroundPoolDayGameoverMask;
	public static Texture backgroundPoolNight;
	public static Texture backgroundPoolNightGameoverOverlay;
	public static Texture backgroundPoolNightGameoverMask;
	public static Texture backgroundRoofDay;
	public static Texture backgroundRoofDayGameoverMask;
	public static Texture backgroundRoofNight;
	public static Texture backgroundRoofNightGameoverMask;
	
	//************************************************Miscelaneous**************************
	
	public static Texture seedPackageAssembled;
	
	public static void preloadBackgrounds() {
		backgroundFrontDay = new Texture(Resources.loadAsset("images/background1.jpg"));
		backgroundFrontDayGameoverOverlay = new Texture(Resources.loadAsset("images/background1_gameover_interior_overlay.png"));
		backgroundFrontDayGameoverMask = new Texture(Resources.loadAsset("images/background1_gameover_mask.png"));
		backgroundFrontNight = new Texture(Resources.loadAsset("images/background2.jpg"));
		backgroundFrontNightGameoverOverlay = new Texture(Resources.loadAsset("images/background2_gameover_interior_overlay.png"));
		backgroundFrontNightGameoverMask = new Texture(Resources.loadAsset("images/background2_gameover_mask.png"));
		backgroundPoolDay = new Texture(Resources.loadAsset("images/background3.jpg"));	
		backgroundPoolDayGameoverOverlay = new Texture(Resources.loadAsset("images/background3_gameover_interior_overlay.png"));	
		backgroundPoolDayGameoverMask = new Texture(Resources.loadAsset("images/background3_gameover_mask.png"));
		backgroundPoolNight = new Texture(Resources.loadAsset("images/background4.jpg"));
		backgroundPoolNightGameoverOverlay = new Texture(Resources.loadAsset("images/background4_gameover_interior_overlay.png"));
		backgroundPoolNightGameoverMask = new Texture(Resources.loadAsset("images/background4_gameover_mask.png"));
		backgroundRoofDay = new Texture(Resources.loadAsset("images/background5.jpg"));
		backgroundRoofDayGameoverMask = new Texture(Resources.loadAsset("images/background5_gameover_mask.png"));
		backgroundRoofNight = new Texture(Resources.loadAsset("images/background6boss.jpg"));
		backgroundRoofNightGameoverMask = new Texture(Resources.loadAsset("images/background6_gameover_mask.png"));
	}
	
	public static void preloadMiscelaneous() {
		seedPackageAssembled = new Texture(Resources.loadAsset("images/SeedPacket_Assembled.png"));
	}
}
