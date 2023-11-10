package com.ICompany.ReinEmp.game.core;

import com.ICompany.ReinEmp.game.renderer.ChunkMapRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MainGameManager {
	
	
	//In-Game Modules
	public OrthographicCamera camera;
	
	private ChunkMap[][] chunks;
	
	public MainGameManager() {
		
		//Initializes
		camera = new OrthographicCamera(8, 4.8f);//(800, 480);
		
		//Pre-generate map (chunk)
		/*ChunkTile[] tiles = new ChunkTile[ChunkMap.CHUNK_HEIGHT*ChunkMap.CHUNK_WIDTH];
		for(int i=0; i<tiles.length; i++)
			tiles[i] = new ChunkTile((short)1, (short)2, (byte)0);*/
		chunks = WorldGenerator.genMap(2, 2);//new ChunkMap[][]{new ChunkMap[] {new ChunkMap(0, 0, tiles)}};
		
	}
	
	public void execute() {
		
		if(Gdx.input.isTouched(0)) {
			Vector2 block = screenToBlocks(Gdx.input.getX(0), Gdx.input.getY(0));
			chunks[0][0].getTile((int)block.x, (int)block.y).ground = 0;
		}
		
		
		camera.update();
		ChunkMapRenderer.tiles.bind(0);
		ChunkMapRenderer.program.useShader();
		//Gdx.gl20.glUniform1i(chunks[0][0].renderer.program.getUniform("tex"), 0);
		//Gdx.gl20.glUniform3f(map.renderer.program.getUniform("base_color"), 1.f, 1.f, 1.f);
		Gdx.gl20.glUniformMatrix4fv(chunks[0][0].renderer.program.getUniform("global_matrix"), 1, true, camera.combined.val, 0);
		chunks[0][0].renderer.updateMesh();
		chunks[0][0].render();
		
	}
	
	public Vector2 screenToBlocks(float x, float y) {
		Vector3 vec = new Vector3(x, y, 0);
		vec = camera.unproject(vec);
		return new Vector2(vec.x, vec.y);
	}
}
