package com.survivalgame.game.renderer;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.survivalgame.game.constants.PrefabCube;
import com.survivalgame.game.primitive.Mesh;
import com.survivalgame.game.primitive.Pov;
import com.survivalgame.game.primitive.ShaderProgram;

public class Chunk {
	private Mesh mesh;
	private float[] vertices;
	private short[] indices;
	private int[] blocks;
	private Vector3 positionChunk;
	private Vector3 indexChunk;
	
	private Pov pov;
	private int buildIndex = 0;
	private int buildVertex = 0;
	private int buildQuadInd = 0;
	
	/*
	 * *@Global block program
	 */
	
	public static final ShaderProgram mainProgram = new ShaderProgram(
			"attribute vec3 a_position;\r\n" + 
			"attribute vec3 a_color;\r\n" + 
			"\r\n" + 
			"uniform mat4 povMatrix;\r\n" + 
			"\r\n" + 
			"varying vec3 v_color;\r\n" + 
			"void main(){\r\n" + 
			"	v_color = a_color;\r\n" + 
			"	gl_Position = povMatrix*vec4(a_position, 1.0);\r\n" + 
			"}\r\n" + 
			""
		,
			"precision mediump float;\r\n" + 
			"\r\n" + 
			"varying vec3 v_color;\r\n" + 
			"void main(){\r\n" + 
			"	gl_FragColor = vec4(v_color, 1.0);\r\n" + 
			"}\r\n" + 
			""
		,
			new String[] {"a_position","a_color"}
		,
			new int[] {3, 3}
		,
			new String[] {"povMatrix",}
		);
	public static int[] chunkSize = new int[] {16, 6, 16};
	
	/*
	 * *@Chunk functions
	 */
	
	public Chunk(Pov pov, int[] blocks) {
		this.pov = pov;
		this.positionChunk = new Vector3();
		mesh = new Mesh();
		updateTerrain(blocks);
	}
	
	/*
	 * * @Mesh builder and terrain updater
	 */
	public void updateTerrain(int[] blocks) {
		
		this.blocks = blocks;
		
		vertices = new float[chunkSize[0]*chunkSize[1]*chunkSize[2]* 6*6*4];
		indices = new short[chunkSize[0]*chunkSize[1]*chunkSize[2]* 6*2*3];
		buildVertex = 0;
		buildIndex = 0;
		buildQuadInd = 0;
		
		for(int x=0; x<chunkSize[0]; x++)
			for(int y=0; y<chunkSize[1]; y++)
				for(int z=0; z<chunkSize[2]; z++)
					createBlock(x, y, z);
		
		float[] nverts = new float[buildVertex];
		short[] ninds = new short[buildIndex];
		System.arraycopy(vertices, 0, nverts, 0, buildVertex);
		System.arraycopy(indices, 0, ninds, 0, buildIndex);
		vertices = nverts;
		indices = ninds;
		
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
	}
	private void createBlock(int xi, int yi, int zi) {
		float x = xi+positionChunk.x;
		float y = yi+positionChunk.y;
		float z = zi+positionChunk.z;
		if(getBlock(xi-1, yi, zi)==0)
			createCubeFace(PrefabCube.SIDES[0], x, y, z);
		if(getBlock(xi+1, yi, zi)==0)
			createCubeFace(PrefabCube.SIDES[1], x, y, z);
		if(getBlock(xi, yi-1, zi)==0)
			createCubeFace(PrefabCube.SIDES[2], x, y, z);
		if(getBlock(xi, yi+1, zi)==0)
			createCubeFace(PrefabCube.SIDES[3], x, y, z);
		if(getBlock(xi, yi, zi-1)==0)
			createCubeFace(PrefabCube.SIDES[4], x, y, z);
		if(getBlock(xi, yi, zi+1)==0)
			createCubeFace(PrefabCube.SIDES[5], x, y, z);
	}
	private void createCubeFace(float[] bms, float x, float y, float z) {
		System.arraycopy(new float[] {
				(bms[0]+x),  (bms[1]+y),  (bms[2]+z),  1, 0, 0,
				(bms[3]+x),  (bms[4]+y),  (bms[5]+z),  0, 1, 0,
				(bms[6]+x),  (bms[7]+y),  (bms[8]+z),  0, 0, 1,
				(bms[9]+x),  (bms[10]+y), (bms[11]+z), 1, 1, 0,
			}, 0, vertices, buildVertex, 24);
		buildVertex += 24;
		System.arraycopy(new short[] {
				(short)buildQuadInd, (short)(buildQuadInd+1), (short)(buildQuadInd+2),
				(short)buildQuadInd, (short)(buildQuadInd+2), (short)(buildQuadInd+3),
			}, 0, indices, buildIndex, 6);
		buildIndex += 6;
		buildQuadInd += 4;
	}
	
	/*
	 * *@Terrain Manipulation
	 */
	public void setPosition(Vector3 pos) {
		positionChunk.x = pos.x*chunkSize[0];
		positionChunk.y = pos.y*chunkSize[1];
		positionChunk.z = pos.z*chunkSize[2];
		updateTerrain(blocks);
	}
	public int getBlock(int x, int y, int z) {
		if(x<0||x>=chunkSize[0]||y<0||y>=chunkSize[1]||z<0||z>=chunkSize[2])
			return 0;
		return blocks[x*chunkSize[1]*chunkSize[2]+y*chunkSize[2]+z];
	}
	
	/*
	 * * @Redraw Functions
	 */
	
	public void draw() {
		drawFace();
	}
	private void drawFace() {
		mainProgram.useShader();
		Gdx.gl20.glUniformMatrix4fv(
				mainProgram.getUniform("povMatrix"), 
				1, false, pov.getMValues(), 0);
		//GL20.nglUniformMatrix4fv(location, count, transpose, value);
		mesh.draw(mainProgram, GL20.GL_TRIANGLES);
	}
}
