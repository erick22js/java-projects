package com.survivalgame.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;

import com.survivalgame.game.primitive.Pov;

public class FreelyCameraController {
	
	private Pov pov;
	
	public float motionSpeed = 2;
	
	public FreelyCameraController(float fov, float ratio, float near, float far) {
		pov = new Pov(fov, ratio, near, far);
	}
	public void MoveFoward(float speed) {
		MoveDirection(new Vector3(0, 0, 0).add(pov.rotation), speed);
	}
	public void MoveBackyard(float speed) {
		MoveDirection(new Vector3((float) Math.PI, (float) Math.PI, 0).add(pov.rotation), speed);
	}
	public void MoveLeft(float speed) {
		StrafeDirection(new Vector3(0, (float) Math.PI*-.5f, 0).add(pov.rotation), speed);
	}
	public void MoveRight(float speed) {
		StrafeDirection(new Vector3(0, (float) Math.PI*.5f, 0).add(pov.rotation), speed);
	}
	public void TranslateAxis(Vector3 axis) {
		pov.position.add(axis);
	}
	public void MoveDirection(Vector3 direction, float speed) {
		float dirxsin = Math.abs((float) Math.sin(direction.x));
		float dirxcos = Math.abs((float) Math.cos(direction.x));
		pov.position.x += -Math.sin(direction.y)*speed*dirxcos;
		pov.position.y += Math.sin(direction.x)*speed;
		pov.position.z += Math.cos(direction.y)*speed*dirxcos;
		pov.update();
	}
	public void StrafeDirection(Vector3 direction, float speed) {
		float dirxsin = Math.abs((float) Math.sin(direction.x));
		float dirxcos = Math.abs((float) Math.cos(direction.x));
		pov.position.x += -Math.sin(direction.y)*speed;
		pov.position.z += Math.cos(direction.y)*speed;
		pov.update();
	}
	public void RotateAxis(Vector3 axis) {
		pov.rotation.add(axis);
		pov.update();
	}
	public void executeDebugMoviment(Input input) {
		if(input.isKeyPressed(Keys.W)) {
			MoveFoward(motionSpeed);
		}
		if(input.isKeyPressed(Keys.S)) {
			MoveBackyard(motionSpeed);
		}
		if(input.isKeyPressed(Keys.A)) {
			MoveLeft(motionSpeed);
		}
		if(input.isKeyPressed(Keys.D)) {
			MoveRight(motionSpeed);
		}
		if(input.isKeyPressed(Keys.LEFT)) {
			RotateAxis(new Vector3(0, -0.05f, 0));
		}
		if(input.isKeyPressed(Keys.RIGHT)) {
			RotateAxis(new Vector3(0, 0.05f, 0));
		}
		if(input.isKeyPressed(Keys.UP)) {
			RotateAxis(new Vector3(-0.05f, 0, 0));
		}
		if(input.isKeyPressed(Keys.DOWN)) {
			RotateAxis(new Vector3(0.05f, 0, 0));
		}
	}
	public Pov getPov() {
		return pov;
	}
}
