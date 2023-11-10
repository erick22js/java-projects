package com.popcap.gaming.Plants.seedPacket;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.popcap.gaming.Resources.DataImages;

public class SeedPackageTextures {
	public static Texture source;
	public static HashMap<String, int[]> frames;
	
	public static void preload() {
		source = DataImages.seedPackageAssembled;
		frames = new HashMap<String, int[]>();
		frames.put("peashooter", new int[] {
				0, 0, 65, 90
		});
	}
	
}
