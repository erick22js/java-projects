package com.circuitsimulator;

import org.jsonJava.JSONArray;
import org.jsonJava.JSONObject;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.circuitsimulator.woltex.core.WoltexController;
import com.circuitsimulator.woltex.core.WoltexEnviorment;
import com.circuitsimulator.woltex.core.WoltexIO;
import com.circuitsimulator.woltex.core.circuit.WoltexComponent;
import com.circuitsimulator.woltex.core.circuit.components.AND_Component;
import com.circuitsimulator.woltex.core.circuit.components.OR_Component;
import com.circuitsimulator.woltex.renderer.ComponentShape;

public class WoltexGame extends Game{
	
	ComponentShape cs;
	
	WoltexController cam;
	WoltexEnviorment env;

	WoltexComponent component;
	
	@Override
	public void create() {
		
		cam = new WoltexController();
		env = new WoltexEnviorment(cam);
		cam.zoom = .125f;
		cam.screen_ratio = 800f/480f;
		
		component = new AND_Component();
		env.addComponent(component);
		env.addComponent(new AND_Component());
		
	}
	
	float flip = 0;
	
	@Override
	public void render() {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT|Gdx.gl.GL_DEPTH_BUFFER_BIT);
		
		
		if(Gdx.input.isKeyPressed(Keys.D)) {
			cam.position.x -= .1f;
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			cam.position.x += .1f;
		}
		if(Gdx.input.isKeyPressed(Keys.W)) {
			cam.position.y -= .1f;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			cam.position.y += .1f;
		}
		cam.update();
		//component.position.x += .01f;
		//flip += .02f;
		component.rotation += 1; //+= .01f;
		
		env.render();
		env.shaperen.begin(ShapeType.Filled);
		env.shaperen.setProjectionMatrix(new Matrix4());
		Vector3 pos = new Vector3(1f, 0f, 0f);
		pos.mul(component.pins[1].local);
		env.shaperen.setColor(1, 0, 0, 1);
		env.shaperen.ellipse(pos.x-.2f, pos.y-.2f, .4f, .4f, 16);
		env.shaperen.end();
		
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
	
}
