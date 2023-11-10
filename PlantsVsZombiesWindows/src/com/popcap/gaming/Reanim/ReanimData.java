package com.popcap.gaming.Reanim;

import com.badlogic.gdx.graphics.Texture;

public class ReanimData {
	public int[][] frames;
	public Texture texture;
	public ReanimControl animation;
	public int length;
	private ReanimData(int[][] framesn, Texture tex, ReanimControl anim) {
		frames = framesn;
		texture = tex;
		animation = anim;
		length = frames.length;
		anim.length = length;
		anim.compile();
	}
	public static ReanimData uncodeAnim(String src, Texture tex, ReanimControl anim){
		int[][] data;
		String[] values = src.split("=");
		System.out.println(values.length);
		int reader = 0;
		int size = Integer.parseInt(values[0]);
		data = new int[size][];
		reader++;
		for(int fr=0; fr<data.length; fr++) {
			data[fr] = new int[] {
					Integer.parseInt(values[reader]),
					Integer.parseInt(values[reader+1]),
					Integer.parseInt(values[reader+2]),
					Integer.parseInt(values[reader+3]),
					Integer.parseInt(values[reader+4]),
					Integer.parseInt(values[reader+5]),
					Integer.parseInt(values[reader+6]),
					Integer.parseInt(values[reader+7])
			};
			reader+=8;
		}
		return new ReanimData(data, tex, anim);
	}
}
