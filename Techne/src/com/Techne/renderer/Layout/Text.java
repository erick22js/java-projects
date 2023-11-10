package com.Techne.renderer.Layout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

public class Text extends Frame{
	
	//Main properties
	public String text = "";
	
	public Text() {
		super();
		background_color.a = 0f;
	}
	
	@Override
	protected Matrix4 render(Matrix4 pMatrix, Layout pLayout) {
		super.render(pMatrix, pLayout);
		
		pLayout.sprite_batch.begin();
		pLayout.sprite_batch.setProjectionMatrix(matrix);
		//pLayout.text_batch.
		//pLayout.text_batch.draw(batch, str, x, y, targetWidth, halign, wrap)
		pLayout.text_batch.draw(pLayout.sprite_batch, text, 0, 0, .1f, 0, false);
		pLayout.sprite_batch.end();
		
		return matrix;
	}
}
