package com.popcap.gaming;

import com.badlogic.gdx.Gdx;

public class Input {
	
	public static boolean holding = false;
	public static boolean tapped = false;
	public static boolean released = false;
	
	private static boolean cooldown = false;
	
	public static void update() {
		holding = Gdx.input.isTouched();
		
		if(cooldown&&!holding) {
			released = true;
			cooldown = false;
		}else {
			released = false;
		}
		/*
		if(!holding)
			cooldown = false;
		*/
		if(!cooldown&&holding) {
			tapped = true;
			cooldown = true;
		}else {
			tapped = false;
		}
	}
	
	public static float eventX() {
		float x = Gdx.input.getX();
		x = (x/Gdx.graphics.getWidth())*Device.RESOLUTION_WIDTH;
		return x;
	}
	public static float eventY() {
		float y = Gdx.input.getY();
		y = -(y/Gdx.graphics.getHeight())+1;
		y*=Device.RESOLUTION_HEIGHT;
		return y;
	}
}
