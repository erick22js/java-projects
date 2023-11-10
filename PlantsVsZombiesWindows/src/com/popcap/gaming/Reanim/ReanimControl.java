package com.popcap.gaming.Reanim;

import java.util.HashMap;

public class ReanimControl {
	Object[] presets;
	public HashMap <String, Integer[]> animations;
	public int length;
	public ReanimControl(Object[] animationsSetter) {
		presets = animationsSetter;
		animations = new HashMap <String, Integer[]>();
	}
	public void compile() {
		//int first = 0;
		System.out.println(presets[2]);
		for(int i=0; i<presets.length; i+=3) {
			Integer first = (Integer)presets[i+1];
			Integer end = (Integer)presets[i+2];
			end = end < 0? length: end;
			animations.put((String)presets[i],
					new Integer[] {first, end});
		}
	}
}
