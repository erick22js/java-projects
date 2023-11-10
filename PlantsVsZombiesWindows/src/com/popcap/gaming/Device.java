package com.popcap.gaming;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class Device {
	
	public static boolean mobile;
	public static int RESOLUTION_WIDTH = 1000;
	public static int RESOLUTION_HEIGHT = 600;
	public static Matrix4 DefaultView = new Matrix4().set(new Matrix3().set(new float[] {
			1f/(Device.RESOLUTION_WIDTH*.5f), 0.0f, 0.0f,
			0.0f, 1f/(Device.RESOLUTION_HEIGHT*.5f), 0.0f,
			-1f, -1f, 1.0f,
	}));
	public static Matrix4 ResetView = new Matrix4();
	
}
