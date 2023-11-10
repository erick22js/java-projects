package com.ICompany.ReinEmp.game.renderer;

import java.nio.FloatBuffer;

import com.ICompany.ReinEmp.game.base.Mesh;
import com.ICompany.ReinEmp.game.base.ShaderProgram;
import com.ICompany.ReinEmp.game.core.ChunkMap;
import com.ICompany.ReinEmp.game.core.ChunkTile;
import com.ICompany.ReinEmp.game.database.TilesDatabase;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class ChunkMapRenderer {
	
	public static final int ATTRIBS_FOR_VERTICE = 3;
	public static ShaderProgram program;
	public static Texture tiles;
	
	private ChunkMap chunk;
	private Mesh geometry;
	private float[] buffer;
	
	int chunkX;
	int chunkY;
	
	public ChunkMapRenderer(ChunkMap chunk, int cx, int cy) {
		//If global chunk shader ins't defined, init it
		if(program==null) {
			program = new ShaderProgram(
					Gdx.files.internal("assets/shaders/tileset.vertex.glsl").readString(),
					Gdx.files.internal("assets/shaders/tileset.fragment.glsl").readString(),
					new String[] {"a_pos", "a_uvs", "a_col"},  new int[] {1, 1, 1}, 
					new String[] {"global_matrix", "base_color", "tex"}
					);
			tiles = new Texture(Gdx.files.internal("assets/blockTexture.png"));
		}
		
		chunkX = cx;
		chunkY = cy;
		
		//And now, set the geometry and chunk map (for requesting blocks data and get appearance)
		this.chunk = chunk;
		geometry = new Mesh();
		
		//Setup buffer
		buffer = new float[
              				ChunkMap.CHUNK_WIDTH*ChunkMap.CHUNK_HEIGHT*
           				ATTRIBS_FOR_VERTICE*4];
		geometry.setVertices(buffer);
		
		//Setup indices and values
		short[] indices = new short[ChunkMap.CHUNK_WIDTH*ChunkMap.CHUNK_HEIGHT*6];
		int v_ind = 0;
		for(int x=0; x<ChunkMap.CHUNK_WIDTH; x++) {
			for(int y=0; y<ChunkMap.CHUNK_HEIGHT; y++) {
				indices[(x*ChunkMap.CHUNK_HEIGHT+y)*6] =   (short) v_ind;
				indices[(x*ChunkMap.CHUNK_HEIGHT+y)*6+1] = (short) (v_ind+1);
				indices[(x*ChunkMap.CHUNK_HEIGHT+y)*6+2] = (short) (v_ind+2);
				indices[(x*ChunkMap.CHUNK_HEIGHT+y)*6+3] = (short) v_ind;
				indices[(x*ChunkMap.CHUNK_HEIGHT+y)*6+4] = (short) (v_ind+2);
				indices[(x*ChunkMap.CHUNK_HEIGHT+y)*6+5] = (short) (v_ind+3);
				v_ind += 4;
				buffer[(x*ChunkMap.CHUNK_HEIGHT+y)*4*ATTRIBS_FOR_VERTICE+(ATTRIBS_FOR_VERTICE*0)] = (((x+chunkX*ChunkMap.CHUNK_WIDTH)&0b1111111111)<<10)+((y+1+chunkY*ChunkMap.CHUNK_HEIGHT)&0b1111111111);
				buffer[(x*ChunkMap.CHUNK_HEIGHT+y)*4*ATTRIBS_FOR_VERTICE+(ATTRIBS_FOR_VERTICE*1)] = (((x+1+chunkX*ChunkMap.CHUNK_WIDTH)&0b1111111111)<<10)+((y+1+chunkY*ChunkMap.CHUNK_HEIGHT)&0b1111111111);
				buffer[(x*ChunkMap.CHUNK_HEIGHT+y)*4*ATTRIBS_FOR_VERTICE+(ATTRIBS_FOR_VERTICE*2)] = (((x+1+chunkX*ChunkMap.CHUNK_WIDTH)&0b1111111111)<<10)+((y+chunkY*ChunkMap.CHUNK_HEIGHT)&0b1111111111);
				buffer[(x*ChunkMap.CHUNK_HEIGHT+y)*4*ATTRIBS_FOR_VERTICE+(ATTRIBS_FOR_VERTICE*3)] = (((x+chunkX*ChunkMap.CHUNK_WIDTH)&0b1111111111)<<10)+((y+chunkY*ChunkMap.CHUNK_HEIGHT)&0b1111111111);
			}
		}
		geometry.setIndices(indices);
		updateMesh();
	}
	
	
	public void updateMesh() {
		int offset_block;
		ChunkTile mTile;
		ChunkTile mTile0;
		ChunkTile mTile1;
		ChunkTile mTile2;
		ChunkTile mTile3;
		ChunkTile mTile4;
		ChunkTile mTile5;
		ChunkTile mTile6;
		ChunkTile mTile7;
		int[] dt;
		int[] dt0;
		int[] dt1;
		int[] dt2;
		int[] dt3;
		int[] dt4;
		int[] dt5;
		int[] dt6;
		int[] dt7;
		int[] dtg;
		for(int x=0; x<ChunkMap.CHUNK_WIDTH; x++) {
			for(int y=0; y<ChunkMap.CHUNK_HEIGHT; y++) {
				mTile  = chunk.getTile(x, y);
				mTile0 = chunk.getTile(x-1, y+1);
				mTile1 = chunk.getTile(x, y+1);
				mTile2 = chunk.getTile(x+1, y+1);
				mTile3 = chunk.getTile(x-1, y);
				mTile4 = chunk.getTile(x+1, y);
				mTile5 = chunk.getTile(x-1, y-1);
				mTile6 = chunk.getTile(x, y);
				mTile7 = chunk.getTile(x+1, y-1);
				dt = TilesDatabase.tiles[mTile.id];
				dt0 = TilesDatabase.tiles[mTile0.id];
				dt1 = TilesDatabase.tiles[mTile1.id];
				dt2 = TilesDatabase.tiles[mTile2.id];
				dt3 = TilesDatabase.tiles[mTile3.id];
				dt4 = TilesDatabase.tiles[mTile4.id];
				dt5 = TilesDatabase.tiles[mTile5.id];
				dt6 = TilesDatabase.tiles[mTile6.id];
				dt7 = TilesDatabase.tiles[mTile7.id];
				dtg = TilesDatabase.grounds[mTile.ground];
				offset_block = (x*ChunkMap.CHUNK_HEIGHT+y)*4*ATTRIBS_FOR_VERTICE;
				buffer[offset_block+(ATTRIBS_FOR_VERTICE*0)+1] = ((dt[1])<<18)  +((dt[2])<<12)   +  ((dtg[1])<<6)  +(dtg[2]);
				buffer[offset_block+(ATTRIBS_FOR_VERTICE*1)+1] = ((dt[1]+1)<<18)+((dt[2])<<12)   +  ((dtg[1]+1)<<6)+(dtg[2]);
				buffer[offset_block+(ATTRIBS_FOR_VERTICE*2)+1] = ((dt[1]+1)<<18)+((dt[2]+1)<<12) +  ((dtg[1]+1)<<6)+(dtg[2]+1);
				buffer[offset_block+(ATTRIBS_FOR_VERTICE*3)+1] = ((dt[1])<<18)  +((dt[2]+1)<<12) +  ((dtg[1])<<6)  +(dtg[2]+1);
				buffer[offset_block+(ATTRIBS_FOR_VERTICE*0)+2] = 0x888888;
				buffer[offset_block+(ATTRIBS_FOR_VERTICE*1)+2] = 0x888888;
				buffer[offset_block+(ATTRIBS_FOR_VERTICE*2)+2] = 0x888888;
				buffer[offset_block+(ATTRIBS_FOR_VERTICE*3)+2] = 0x888888;
			}
		}
		((FloatBuffer)geometry.getVerticesBuffer()).position(0);
		((FloatBuffer)geometry.getVerticesBuffer()).put(buffer);
		((FloatBuffer)geometry.getVerticesBuffer()).position(0);
	}
	
	//private 
	
	public void render() {
		//geometry.setVertices(buffer);
		//((FloatBuffer)geometry.getVerticesBuffer()).
		geometry.draw(program, GL20.GL_TRIANGLES);
	}
	
}
