package com.ICompany.ReinEmp.game.core;

import com.ICompany.ReinEmp.game.renderer.ChunkMapRenderer;

public class ChunkMap {
	public static final int CHUNK_WIDTH = 256;//32;
	public static final int CHUNK_HEIGHT = 256;//32;
	
	protected int chunkX = 0;
	protected int chunkY = 0;
	
	private ChunkTile[] tiles;
	
	private ChunkMap[] nbrhd;
	/* --Neighborhood--
	 *    0 1 2
	 *    3 T 4    T=> this
	 *    5 6 7
	 */
	
	private static final ChunkTile blankTile = new ChunkTile((short)0, (short)0, (byte)0);
	
	public ChunkMapRenderer renderer;
	
	public ChunkMap(int x, int y, ChunkTile[] tiles) {
		chunkX = x;
		chunkY = y;
		
		//Initialize tiles (Only for debug purpose)
		this.tiles = tiles;
		
		renderer = new ChunkMapRenderer(this, x, y);
		
	}
	
	public ChunkTile getTile(int x, int y) {
		if(x<0) {
			return blankTile;
		}
		else if(x>=CHUNK_WIDTH) {
			return blankTile;
		}
		else if(y<0) {
			return blankTile;
		}
		else if(y>=CHUNK_HEIGHT) {
			return blankTile;
		}
		return tiles[x*CHUNK_HEIGHT+y];
	}
	
	public void setNeighborhood(ChunkMap[] nbrhd) {
		this.nbrhd = nbrhd;
	}
	
	public void render() {
		renderer.render();
	}
}
