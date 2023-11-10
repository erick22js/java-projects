package com.ICompany.ReinEmp.game.core;

public class WorldGenerator {
	public static ChunkMap[][] genMap(int cwid, int chei){
		ChunkMap[][] map = new ChunkMap[cwid][chei];
		for(int cx=0; cx<cwid; cx++) {
			for(int cy=0; cy<chei; cy++) {
				ChunkTile[] tiles = new ChunkTile[ChunkMap.CHUNK_HEIGHT*ChunkMap.CHUNK_WIDTH];
				for(int i=0; i<tiles.length; i++)
					tiles[i] = new ChunkTile((short)1, (short)2, (byte)0);
				ChunkMap chunk = new ChunkMap(cx, cy, tiles);
				map[cx][cy] = chunk;
			}
		}
		return map;
	}
}
