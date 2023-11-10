package com.Techne.core;

public class GameMap {
	
	private int width;
	private int height;
	private Tile[] tiles;
	
	public GameMap() {
		
	}
	public GameMap(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width*height];
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				tiles[x*height+y] = new Tile();
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x*height+y];
	}
	public Tile getTileSafe(int x, int y) {
		if(x>=0&&x<this.width&&y>=0&&y<this.height)
			return tiles[x*height+y];
		return null;
	}
	public void setTile(Tile tile, int x, int y) {
		tiles[x*height+y] = tile;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
