package com.Techne.core.runtime;

import com.Techne.core.GameMap;
import com.Techne.core.GameMapGenerator;
import com.Techne.core.Tile;
import com.Techne.core.gameplay.Nation;
import com.Techne.renderer.GameCamera;
import com.Techne.renderer.MapChunkMesh;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.async.ThreadUtils;

public class TechneGame {
	
	/*
	 * GAME RENDERERS
	 */
	private MapChunkMesh[] chunks;
	//
	//Stage cena;
	
	/*
	 * GAME MAP PROPERTIES
	 */
	public static final int CHUNK_WIDTH  = 32;
	public static final int CHUNK_HEIGHT = 32;
	private int map_width;
	private int map_height;
	
	/*
	 * GAME INNER PROPERTIES
	 */
	private GameMap game_map;
	private Nation[] nations;
	public GameCamera camera;
	
	/******************************************************************
	 * @GAME_INITIALIZER
	 ******************************************************************
	 */
	
	public TechneGame() {
		this.map_width = 16;
		this.map_height = 8;
		initialize();
	}
	private void initialize() {
		
		game_map = new GameMap(CHUNK_WIDTH*map_width, CHUNK_HEIGHT*map_height);
		GameMapGenerator.generate(game_map);
		chunks = new MapChunkMesh[map_width*map_height];
		
		for(int x=0; x<map_width; x++) {
			for(int y=0; y<map_height; y++) {
				MapChunkMesh chunk = new MapChunkMesh(x*CHUNK_WIDTH, y*CHUNK_HEIGHT, CHUNK_WIDTH, CHUNK_HEIGHT);
				chunk.bindMap(game_map);
				chunks[x*map_height+y] = chunk;
			}
		}
		nations = GameMapGenerator.nations_out;
		
		camera = new GameCamera(800f, 480f);
		camera.zoom = 40;
		
		/*cena = new Stage();
		Button b = new Button();
		
		cena.addActor(b);*/
		//cena.
	}
	
	/******************************************************************
	 * @UTILS_GAME
	 ******************************************************************
	 */
	
	public MapChunkMesh getChunk(int x, int y) {
		if(x>=0&&x<map_width&&y>=0&&y<map_height) {
			return chunks[x*map_height+y];
		}
		return null;
	}
	public MapChunkMesh tileCoordToChunk(int x, int y) {
		return getChunk(x/CHUNK_WIDTH, y/CHUNK_HEIGHT);
	}
	public Vector3 gestureToScreen(int t_i) {
		Vector3 point = new Vector3(Gdx.input.getX(t_i), Gdx.input.getY(t_i), 0);
		point.scl(2.f/Gdx.graphics.getWidth(), -2.f/Gdx.graphics.getHeight(), 1);
		point.add(-1f, 1f, 0f);
		return point;
	}
	public Vector3 gestureToWorld(int t_i) {
		Vector3 point = new Vector3(Gdx.input.getX(t_i), Gdx.input.getY(t_i), 0);
		point.scl(2.f/Gdx.graphics.getWidth(), -2.f/Gdx.graphics.getHeight(), 1);
		point.add(-1f, 1f, 0f);
		return camera.projectPoint(point);
	}
	
	/******************************************************************
	 * @MECHANICS_GAME
	 ******************************************************************
	 */
	
	public boolean addNation(Nation nnation) {
		for(int i=0; i<nations.length; i++) {
			if(nations[i]==null) {
				nnation.id_index = (short)i;
				nations[i] = nnation;
				return true;
			}
		}
		return false;
	}
	public void render() {
		
		//Eval actual resolution screen based on window size (height must be fixed on 480)
		camera.resolution.set((Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight())/(80f/48f)*800f, 480f);
		//cena.setViewport(viewport);
		
		/***********************************************************
		 * @INPUT_GAME_HANDLING
		 ***********************************************************
		 */
		
		final float mSpeed = 48f;
		if(Gdx.input.isKeyPressed(Keys.W)) {
			camera.position.y -= mSpeed/camera.zoom;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			camera.position.y += mSpeed/camera.zoom;
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			camera.position.x += mSpeed/camera.zoom;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			camera.position.x -= mSpeed/camera.zoom;
		}
		if(Gdx.input.isKeyPressed(Keys.E)&&camera.zoom<80) {
			camera.zoom *= 1.05f;
		}
		if(Gdx.input.isKeyPressed(Keys.Q)&&camera.zoom>10) {
			camera.zoom *= 0.95f;
		}
		if(Gdx.input.isKeyPressed(Keys.F5)) {
			GameMapGenerator.generate(game_map);
			for(int i=0; i<chunks.length; i++) {
				chunks[i].toUpdate = true;
			}
		}
		
		if(Gdx.input.isTouched(0)) {
			Vector3 p = gestureToWorld(0);
			Tile tile = game_map.getTileSafe((int)p.x, (int)p.y);
			if(tile!=null)
				tile.ground = false;
			MapChunkMesh c = tileCoordToChunk((int)p.x, (int)p.y);
			if(c!=null)
				c.toUpdate = true;
			Vector3 t = gestureToScreen(0);
		}
		
		/***********************************************************
		 * @GAME_RENDER
		 ***********************************************************
		 */
		
		//Calculate center of view and use as relative
		Vector3 center = camera.projectPoint(new Vector3(0, 0, 0f));
		center.scl(1f/CHUNK_WIDTH);
		
		//Determines the number of chunks for rendering
		int rw = ((int)Math.ceil((camera.resolution.x/camera.zoom)/CHUNK_WIDTH));
		int rh = ((int)Math.ceil((camera.resolution.y/camera.zoom)/CHUNK_HEIGHT));
		
		//Determines each edge to pick chunks for rendering
		int beg_x = ((int)Math.round(center.x))-rw-1;
		int end_x = beg_x+rw*2+2;
		int beg_y = ((int)Math.round(center.y))-rh-1;
		int end_y = beg_y+rh*2+2;
		
		//Loops from each edge on chunks position and render each one
		MapChunkMesh chunk;
		for(int x=beg_x; x<end_x; x++) {
			for(int y=beg_y; y<end_y; y++) {
				//Checks if it is out of bound
				chunk = getChunk(x, y);
				if(chunk!= null) {
					chunk.render(camera.update());
				}
			}
		}

		/***********************************************************
		 * @INTERFACE_RENDER
		 ***********************************************************
		 */
		Matrix4 ui_matrix = camera.getMatrixUI();
		/*cena.act();
		cena.draw();*/
	}
}
