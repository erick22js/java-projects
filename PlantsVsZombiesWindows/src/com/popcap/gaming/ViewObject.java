package com.popcap.gaming;

import com.badlogic.gdx.Gdx;

public class ViewObject extends ActorObject{
	
	protected int[] frame = new int[] {0,0,0,0};
	protected float[] rect = new float[] {0,0,0,0};
	
	public void render() {
		prepareForDraw();
		rect[0] = this.x;
		rect[1] = this.y;
	}
	public boolean picked() {
		return holdOnRect()&&Input.tapped;
	}
	public boolean holdOnRect() {
		float tx = Input.eventX();
		float ty = Input.eventY();
		boolean xVer = tx>=x&&tx<=(x+rect[2]*scaleX);
		boolean yVer = ty>=y&&ty<=(y+rect[3]*scaleY);
		return xVer&&yVer;
	}
}
