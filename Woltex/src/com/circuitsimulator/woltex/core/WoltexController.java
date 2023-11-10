package com.circuitsimulator.woltex.core;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class WoltexController {
	//Control variables
	public Vector2 position;
	public float zoom = 1;
	public float screen_ratio = 1;
	
	//Utils
	public Matrix4 combined;
	
	public WoltexController() {
		combined = new Matrix4();
		position = new Vector2(0, 0);
	}
	
	public Matrix4 update() {
		combined = combined.idt();
		combined.scale(zoom, zoom*screen_ratio, 1);
		combined.translate(position.x, position.y, 0);
		return combined;
	}
}
