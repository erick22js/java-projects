package com.Techne.renderer.Layout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Panel9Chunk extends Container{
	
	public Panel9Chunk() {
		
	}
	protected void render9Chunks(Texture atlas, Rectangle src, Vector2 size, Matrix4 pMatrix, Layout pLayout) {
		resolutionVec2.set(pLayout.resolution);
		resolutionVec2.scl(matrix.val[0]/2f, matrix.val[5]/2f);
		
		Vector2 c_size = new Vector2(size);
		c_size.scl(1f/resolutionVec2.x, 1f/resolutionVec2.y);
		float width = 1f-2*c_size.x;
		float height = 1f-2*c_size.y;
		
		pLayout.sprite_batch.begin();
		pLayout.sprite_batch.setProjectionMatrix(matrix);
		
		//Corner Top-Left
		pLayout.sprite_batch.draw(atlas, 0, 0, c_size.x, c_size.y, (int)src.x, (int)src.y, (int)src.width, (int)src.height, false, true);
		//Corner Bottom-Left
		pLayout.sprite_batch.draw(atlas, 0, c_size.y+height, c_size.x, c_size.y, (int)src.x, (int)src.y+2*(int)src.height, (int)src.width, (int)src.height, false, true);
		//Corner Top-Right
		pLayout.sprite_batch.draw(atlas, c_size.x+width, 0, c_size.x, c_size.y, (int)src.x+2*(int)src.width, (int)src.y, (int)src.width, (int)src.height, false, true);
		//Corner Bottom-Right
		pLayout.sprite_batch.draw(atlas, c_size.x+width, c_size.y+height, c_size.x, c_size.y, (int)src.x+2*(int)src.width, (int)src.y+2*(int)src.height, (int)src.width, (int)src.height, false, true);
		
		//Border Left
		pLayout.sprite_batch.draw(atlas, 0, c_size.y, c_size.x, height, (int)src.x, (int)src.y+1*(int)src.height, (int)src.width, (int)src.height, false, true);
		//Border Bottom
		pLayout.sprite_batch.draw(atlas, c_size.x, c_size.y+height, width, c_size.y, (int)src.x+1*(int)src.width, (int)src.y+2*(int)src.height, (int)src.width, (int)src.height, false, true);
		//Border Right
		pLayout.sprite_batch.draw(atlas, c_size.x+width, c_size.y, c_size.x, height, (int)src.x+2*(int)src.width, (int)src.y+1*(int)src.height, (int)src.width, (int)src.height, false, true);
		//Border Top
		pLayout.sprite_batch.draw(atlas, c_size.x, 0, width, c_size.y, (int)src.x+1*(int)src.width, (int)src.y, (int)src.width, (int)src.height, false, true);
		
		//Center
		pLayout.sprite_batch.draw(atlas, c_size.x, c_size.y, width, height, (int)src.x+1*(int)src.width, (int)src.y+1*(int)src.height, (int)src.width, (int)src.height, false, true);
		
		pLayout.sprite_batch.end();
	}
	@Override
	protected Matrix4 render(Matrix4 pMatrix, Layout pLayout) {
		// TODO Auto-generated method stub
		return super.render(pMatrix, pLayout);
	}
}
