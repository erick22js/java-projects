package com.Techne.renderer.Layout;

import com.badlogic.gdx.math.Matrix4;

public class Button extends Panel9Chunk{
	
	//Main properties
	private boolean is_hold = false;
	
	public Button() {
		
	}
	public boolean isHold() {
		return is_hold;
	}
	@Override
	protected boolean dispatchEventTouch(Matrix4 pMatrix, TouchEvent ev, float x, float y) {
		// TODO Auto-generated method stub
		boolean inBounds = super.dispatchEventTouch(pMatrix, ev, x, y);
		is_hold = is_hold?(ev==TouchEvent.DOWN||ev==TouchEvent.MOVE):(ev==TouchEvent.DOWN||ev==TouchEvent.MOVE)&&inBounds;
		return inBounds;
	}
	@Override
	protected Matrix4 render(Matrix4 pMatrix, Layout pLayout) {
		super.render(pMatrix, pLayout);
		if(is_hold)
			render9Chunks(style.style_atlas, style.buttonon_9chunk_src, style.buttonon_9chunk_size, pMatrix, pLayout);
		else
			render9Chunks(style.style_atlas, style.button_9chunk_src, style.button_9chunk_size, pMatrix, pLayout);
		//is_hover = false;
		renderChilds(pMatrix, pLayout);
		return matrix;
	}
}
