package com.Techne.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameCamera {
	
	private Matrix4 matrix;
	
	public Vector2 position;
	//public Vector2 scale;
	public Vector2 resolution;
	public float zoom;
	public float rotation;
	
	public GameCamera(float width, float height) {
		position = new Vector2(0, 0);
		zoom = 1;//scale = new Vector2(1, 1);
		resolution = new Vector2(width, height);
		rotation = 0;
		
		matrix = new Matrix4();
	}
	public float[] update() {
		matrix.set(new float[] {
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1,
		});
		matrix.rotate(0, 0, 1, rotation);
		matrix.scale(1f/resolution.x, 1f/resolution.y, 1);
		matrix.scale(zoom, zoom, 1);
		matrix.translate(position.x, position.y, 0);
		return matrix.val;
	}
	public Matrix4 getMatrix() {
		update();
		return matrix;
	}
	public Matrix4 getMatrixUI() {
		Matrix4 matrix = new Matrix4();
		matrix.scale(1f/resolution.x, 1f/resolution.y, 1);
		return matrix;
	}
	public Vector3 projectPoint(Vector3 point) {
		update();
		point = point.cpy();
		point.prj(matrix.inv());
		return point;
	}
}
