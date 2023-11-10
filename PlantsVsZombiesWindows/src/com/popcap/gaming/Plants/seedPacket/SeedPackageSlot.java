package com.popcap.gaming.Plants.seedPacket;

import com.badlogic.gdx.Gdx;
import com.popcap.gaming.ActorObject;
import com.popcap.gaming.Input;
import com.popcap.gaming.ViewObject;
import com.popcap.gaming.Plants.Peashooter;
import com.popcap.gaming.Plants.Plant;
import com.popcap.gaming.Resources.Resources;

public class SeedPackageSlot extends ViewObject{
	String id;
	public SeedPackageSlot(String id) {
		this.id = id;
		this.frame = SeedPackageTextures.frames.get(id);
		this.rect[2] = frame[2];
		this.rect[3] = frame[3];
	}
	public void render() {
		prepareForDraw();
		Resources.batchRender.draw(
				SeedPackageTextures.source, 
				0, 0, 
				frame[0], frame[1], 
				frame[2], frame[3]
				);
	}
	public Plant getPlant() {
		switch(id) {
			case "peashooter":
				return new Peashooter();
			default:
				return null;
		}
	}
}
