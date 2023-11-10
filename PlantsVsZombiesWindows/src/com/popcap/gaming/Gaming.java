package com.popcap.gaming;

import org.jsonJava.JSONArray;
import org.jsonJava.JSONObject;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexData;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.popcap.gaming.Levels.GameLevel;
import com.popcap.gaming.Levels.LevelModule;
import com.popcap.gaming.Plants.Peashooter;
import com.popcap.gaming.Plants.Plant;
import com.popcap.gaming.Plants.seedPacket.SeedPackageTextures;
import com.popcap.gaming.Reanim.ReanimObject;
import com.popcap.gaming.Resources.DataImages;
import com.popcap.gaming.Resources.DataPlants;
import com.popcap.gaming.Resources.DataReanims;
import com.popcap.gaming.Resources.Resources;

public class Gaming implements ApplicationListener{
	
	Batch batch;
	ShaderProgram pieceProgram;
	
	ReanimObject peashooter;
	
	Matrix4 matrix;
	
	GameLevel levelTest;
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		Resources.batchRender = batch;//initializeBatch(batch);
		matrix = new Matrix4();
		
		DataImages.preloadBackgrounds();
		DataImages.preloadMiscelaneous();
		DataReanims.preloadPlantsReanim();
		//Reanim.loadReanims();
		
		SeedPackageTextures.preload();
		
		pieceProgram = new ShaderProgram(Resources.loadAsset("shaders/vertex.glsh"), Resources.loadAsset("shaders/fragment.glsh"));
		Resources.reanimProgram = pieceProgram;
		
		new ReanimObject(DataPlants.Reanim_peashooter);
		
		levelTest = new GameLevel(new LevelModule("frontDay", ""));
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	int x = 0;
	int rot = 0;
	float point_time = 0;
	float xa = 100;
	float repeated = 0;
	@Override
	public void render() {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, .3f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		Input.update();
		batch.setShader(pieceProgram);
		pieceProgram.setUniformMatrix("obj_projTrans", new Matrix4());
		batch.setProjectionMatrix(Device.DefaultView.cpy());
		
		
		levelTest.execute(Gdx.graphics.getDeltaTime());
		
		
		batch.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
}
