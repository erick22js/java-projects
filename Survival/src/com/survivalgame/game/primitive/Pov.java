package com.survivalgame.game.primitive;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Pov {
	private Matrix4 modalMatrix;
	private Matrix4 matrix;
	public final Vector3 position = new Vector3(0, 0, 0);
	public final Vector3 rotation = new Vector3(0, 0, 0);

	public Pov(float fov, float aspectRatio, float near, float far) {
		modalMatrix = new Matrix4();
		modalMatrix.setToProjection(near, far, fov, aspectRatio);
		matrix = modalMatrix.cpy();
	}
	public Pov() {
		modalMatrix = new Matrix4();
		matrix = modalMatrix.cpy();
	}
	public Pov(Matrix4 mat) {
		modalMatrix = mat;
		matrix = modalMatrix.cpy();
	}
	public void update() {
		matrix = modalMatrix.cpy();
		matrix.rotateRad(1, 0, 0, rotation.x);
		matrix.rotateRad(0, 1, 0, rotation.y);
		matrix.rotateRad(0, 0, 1, rotation.z);
		//matrix.setToTranslation(0, 0, 0);
		matrix.translate(position);
		System.out.println(position.toString());
	}
	public Matrix4 getMatrix() {
		update();
		return matrix;
	}
	public float[] getMValues() {
		update();
		return matrix.val;
	}
}
