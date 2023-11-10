package com.Techne.renderer.Layout;

import com.badlogic.gdx.math.Matrix4;

public class Panel extends Panel9Chunk{
	
	public Panel() {
		super();
	}
	
	@Override
	protected Matrix4 render(Matrix4 pMatrix, Layout pLayout) {
		// TODO Auto-generated method stub
		super.render(pMatrix, pLayout);
		render9Chunks(style.style_atlas, style.panel_9chunk_src, style.panel_9chunk_size, pMatrix, pLayout);
		renderChilds(pMatrix, pLayout);
		return matrix;
	}
}
