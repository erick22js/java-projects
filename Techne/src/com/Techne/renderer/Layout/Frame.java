package com.Techne.renderer.Layout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

public class Frame extends Container{
	
	//Visual propeties
	public Color background_color;
	public boolean background_filled = true;
	public boolean visible;
	
	public Frame() {
		super();
		background_color = new Color(1f, 1f, 1f, 1f);
		visible = true;
		bound_box.set(0, 0, 1, 1);
	}
	
	@Override
	protected Matrix4 render(Matrix4 pMatrix, Layout pLayout) {
		super.render(pMatrix, pLayout);
		if(background_color.a>0f) {
			pLayout.shape_batch.setProjectionMatrix(matrix);
			pLayout.shape_batch.begin(background_filled?ShapeType.Filled:ShapeType.Line);
			pLayout.shape_batch.setColor(background_color.r, background_color.g, background_color.b, 1f);
			pLayout.shape_batch.rect(0, 0, 1, 1);
			pLayout.shape_batch.end();
		}
		renderChilds(pMatrix, pLayout);
		return matrix;
	}
}
