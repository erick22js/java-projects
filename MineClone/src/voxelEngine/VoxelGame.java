package voxelEngine;

import org.lwjgl.opengl.GL;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import voxelEngine.lowLevel.Mesh;
import voxelEngine.lowLevel.Pov;
import voxelEngine.lowLevel.ShaderProgram;

public class VoxelGame extends Game{
	
	final int worldW = 8;
	final int worldD = 8;
	
	Chunk[][][] chunks;
	FreelyCameraController cam;
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glEnable(GL20.GL_CULL_FACE);
		Gdx.gl20.glDepthFunc(GL20.GL_LEQUAL);
		
		cam = new FreelyCameraController(60, 800f/480f, 0.0625f, 512f);
		cam.motionSpeed = .125f;
		cam.getPov().position.set(-.5f, -67.5f, -2f);
		chunks = new Chunk[worldW][1][worldD];
		for(int x=0; x<worldW; x++)
			for(int y=0; y<1; y++)
				for(int z=0; z<worldD; z++){
					Chunk ck = new Chunk(cam.getPov(),
							TerrainGenerator.genChunk(x, y, z)
							);
					ck.setPosition(new Vector3(x, y, z));
					chunks[x][y][z] = ck;
				}
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		
		cam.executeDebugMoviment(Gdx.input);
		for(int x=0; x<worldW; x++)
			for(int y=0; y<1; y++)
				for(int z=0; z<worldD; z++){
					chunks[x][y][z].draw();
				}
	}
}
