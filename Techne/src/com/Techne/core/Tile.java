package com.Techne.core;

import com.Techne.core.gameplay.Nation;

public class Tile {
	
	public Nation owner = null;
	public boolean ground = true;
	public short item = 0;
	public TilePoliticalStatus political;

	//TODO: Terrain status
	
	public Tile() {}
}
