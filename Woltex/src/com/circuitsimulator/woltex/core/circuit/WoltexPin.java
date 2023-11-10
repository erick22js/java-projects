package com.circuitsimulator.woltex.core.circuit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.circuitsimulator.woltex.utils.FastMath;

public class WoltexPin {
	
	public WoltexComponent connect;
	public boolean isInput;
	
	public Matrix4 local;
	public Vector2 position;
	public float rotation;
	
	public WoltexPin(WoltexComponent connect, boolean isInput, float offsetX, float offsetY, float rotation) {
		this.connect = connect;
		this.isInput = isInput;
		position = new Vector2(offsetX, offsetY);
		this.rotation = rotation;
		local = new Matrix4();
	}
	public Vector2 getPinGPosition() {
		return new Vector2(
				(float)(position.x+Math.cos(rotation))
				, (float)(position.y+Math.sin(rotation))
				);
	}
	public void render(ShapeRenderer renderer, Matrix4 matrix) {
		local = matrix.cpy();
		local.translate(position.x, position.y, 0);
		local.rotateRad(0, 0, 1, rotation);
		Gdx.gl.glLineWidth(5);
		renderer.setProjectionMatrix(local);
		
		renderer.begin(ShapeType.Line);
		renderer.line(0, 0, 1, 0);
		if(isInput)
			renderer.ellipse(.8f, -.2f, .4f, .4f, 12);
		renderer.end();
		if(!isInput) {
			renderer.begin(ShapeType.Filled);
			renderer.ellipse(.8f, -.2f, .4f, .4f, 12);
			renderer.end();
		}
		renderer.end();
	}
}
