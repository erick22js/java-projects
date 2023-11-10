package com.Techne.renderer.Layout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class Layout extends Frame{
	
	//Visual properties
	protected SpriteBatch sprite_batch;
	protected ShapeRenderer shape_batch;
	protected BitmapFont text_batch;
	
	//Main properties
	public Vector2 resolution = new Vector2(1f, 1f);
	//public float aspect_ratio = 1; //Height by Width Ratio => Height/Width
	
	public Layout(StyleSkin skin) {
		sprite_batch = new SpriteBatch();
		shape_batch = new ShapeRenderer();
		text_batch = new BitmapFont();
		//sprite_batch.setTransformMatrix(emptyMatrix);
		
		bound_box.set(-1f, -1f, 2, 2);
		background_color.a = 0f;
		
		this.style = skin;
	}
	public void render() {
		this.render(emptyMatrix, this);
	}
	@Override
	protected Matrix4 render(Matrix4 pMatrix, Layout pLayout) {
		// TODO Auto-generated method stub
		return super.render(pMatrix, pLayout);
	}
}
