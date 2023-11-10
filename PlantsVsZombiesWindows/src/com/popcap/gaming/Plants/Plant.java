package com.popcap.gaming.Plants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.popcap.gaming.ActorObject;
import com.popcap.gaming.Device;
import com.popcap.gaming.Reanim.ReanimObject;
import com.popcap.gaming.Resources.Resources;

public class Plant extends ActorObject{
	
	public float resistance;
	public float height;
	public float altitude;
	public float range;
	
	public String state;
	public int imitated;
	
	public float slot;
	
	ReanimObject[] anims;
	
	
	public Plant() {
		imitated = 0;
		scaleX = scaleY = .6f;
	}
	
	public void execute() {
		
	}
	
	public void renderBodies() {
		for(int i=0; i<anims.length; i++) {
			prepareForDraw();
			Resources.batchRender.getShader().setUniformf("gray", imitated);
			anims[i].execute(Gdx.graphics.getDeltaTime()*20);
			anims[i].endedAnimationAndReset();
			anims[i].render();
			Resources.batchRender.flush();
		}
	}
}
