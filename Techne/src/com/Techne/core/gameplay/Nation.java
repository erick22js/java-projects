package com.Techne.core.gameplay;

import com.Techne.core.Tile;
import com.badlogic.gdx.graphics.Color;

public class Nation {
	
	public boolean type_null = false;
	
	public String id_name;
	public short id_index;
	public Color id_color;
	
	public int info_population;
	public int info_population_sum;
	
	public Tile capital;
	
	public Nation(String name, short id_index, Color color) {
		this.id_name = name;
		this.id_color = color;
	}
}
