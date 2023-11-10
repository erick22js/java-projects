package com.survivalgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.survivalgame.game.objects.FreelyCameraController;
import com.survivalgame.game.primitive.Mesh;
import com.survivalgame.game.primitive.Pov;
import com.survivalgame.game.primitive.ShaderProgram;
import com.survivalgame.game.renderer.Chunk;
import com.survivalgame.game.terrain.TerrainGenerator;

public class MainGame extends Game{
	
	Texture hutex;
	
	FreelyCameraController camp;
	Chunk chunqui;
	
	@Override
	public void create() {
		
		//Setup all properties and functions global
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glEnable(GL20.GL_CULL_FACE);
		Gdx.gl20.glDepthFunc(GL20.GL_LEQUAL);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		
		hutex = new Texture(Gdx.files.internal("assets/textures/human.png"));
		
		camp = new FreelyCameraController(60, Gdx.graphics.getWidth()/(Gdx.graphics.getHeight()+0f), 0.001f, 1000f);
		camp.motionSpeed = .125f;
		camp.getPov().position.set(-.5f, -67.5f, -2f);
		chunqui = new Chunk(camp.getPov(), TerrainGenerator.genChunk(0, 0, 0));
		chunqui.setPosition(new Vector3(0, 0, 0));
	}
	
	@Override
	public void render() {
		
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT|Gdx.gl.GL_DEPTH_BUFFER_BIT);
		
		camp.executeDebugMoviment(Gdx.input);
		chunqui.draw();
		
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
