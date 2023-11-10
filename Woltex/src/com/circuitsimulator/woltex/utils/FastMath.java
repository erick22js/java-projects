package com.circuitsimulator.woltex.utils;

public class FastMath {
	public static byte fastInvertXRotation(int rotation) {
		return (byte)((((rotation&1)^((rotation>>1)&1))*-2)+1);
	}
	public static byte fastInvertYRotation(int rotation) {
		return (byte)((rotation>>1)*-2+1);
	}
}
