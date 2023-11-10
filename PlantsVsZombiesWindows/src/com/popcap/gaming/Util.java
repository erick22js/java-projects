package com.popcap.gaming;

public class Util {
	public static float random(float begin, float end) {
		float dif = end-begin;
		return (float)(begin+dif*Math.random());
	}
}
