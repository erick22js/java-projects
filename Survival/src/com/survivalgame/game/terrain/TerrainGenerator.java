package com.survivalgame.game.terrain;

import com.survivalgame.game.renderer.Chunk;

public class TerrainGenerator {
	public static int[] genChunk(int xOffset, int yOffset, int zOffset) {
		int[] blocks = new int[Chunk.chunkSize[0]*Chunk.chunkSize[1]*Chunk.chunkSize[2]];
		for(int x=0; x<Chunk.chunkSize[0]; x++)
			for(int y=0; y<Chunk.chunkSize[1]; y++)
				for(int z=0; z<Chunk.chunkSize[2]; z++) {
					int block = 0;
					block = 1;
					blocks[x*Chunk.chunkSize[1]*Chunk.chunkSize[2]+y*Chunk.chunkSize[2]+z] = block;
				}
		return blocks;
	}
}
