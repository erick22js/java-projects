package com.Techne.core;

import com.Techne.core.gameplay.Nation;
import com.badlogic.gdx.graphics.Color;

public class GameMapGenerator {
	
	private static enum procAction{
		SET_GROUND,
		SET_WATER,
		//SET_NATION,
		//SET_NONATION,
	};
	
	public static GameMap map_out;
	public static Nation[] nations_out;
	//private static Tile[] pointer_locals;
	
	public static void generate(GameMap map) {
		int width = map.getWidth();
		int height = map.getHeight();
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				map.getTile(x, y).ground = false;
				map.getTile(x, y).owner = null;
			}
		}
		/*pointer_locals = new Tile[(width/16)*(height/16)];
		for(int x=0; x<width; x+=16) {
			for(int y=0; y<height; y+=16) {
				//map.getTile(x, y).ground = false;
				pointer_locals
			}
		}*/
		int bigIslands = 4+((int)Math.floor(Math.random()*3));
		int littleIslands = 8+((int)Math.floor(Math.random()*5));
		for(int i=0; i<bigIslands; i++) {
			int c_s = (int)Math.pow(2, 4+((int)Math.floor(Math.random()*3)));
			int r_x = width/c_s;
			int r_y = height/c_s;
			procedural(procAction.SET_GROUND, map, c_s, ((int)Math.floor(Math.random()*r_x)), ((int)Math.floor(Math.random()*r_y)), null);
		}
		for(int i=0; i<littleIslands; i++) {
			int c_s = (int)Math.pow(2, 2+((int)Math.floor(Math.random()*2)));
			int r_x = width/c_s;
			int r_y = height/c_s;
			procedural(procAction.SET_GROUND, map, c_s, ((int)Math.floor(Math.random()*r_x)), ((int)Math.floor(Math.random()*r_y)), null);
		}
		map_out = map;
		nations_out = new Nation[] {
				new Nation("Zimfrom", (short)0, new Color(1f, 1f, 0f, 1f)),
				new Nation("Quedar", (short)0, new Color(.9f, .3f, 0f, 1f)),
				new Nation("Zoquebede", (short)0, new Color(0f, .3f, 1f, 1f)),
		};
		for(int n=0; n<nations_out.length; n++) {
			int count_cities = 1+((int)Math.floor(Math.random()*2));
			for(int c=0; c<count_cities; c++){
				int chance_counter = ((int)Math.floor(Math.random()*((width*height))));
				mainlnspread: for(int x=0; x<width; x++) {
					for(int y=0; y<height; y++) {
						Tile tile = map.getTile(x, y);
						chance_counter--;
						if(tile.ground&&tile.owner==null) {
							if(chance_counter<=0) {
								proceduralNation(.7f+((float)(Math.random()*.3f)), 20+((int)Math.floor(Math.random()*20)), map, x, y, nations_out[n]);
								nations_out[n].capital = tile;
								break mainlnspread;
							}
						}
					}
				}
			}
		}
		
	}
	private static void procedural(procAction action, GameMap map, int chunk_size, int chunk_x, int chunk_y, Object argument) {
		int width = map.getWidth();
		int height = map.getHeight();
		
		int beg_x = chunk_x*chunk_size;
		int end_x = (chunk_x+1)*chunk_size;
		int beg_y = chunk_y*chunk_size;
		int end_y = (chunk_y+1)*chunk_size;
		
		for(int x=beg_x; x<end_x&&x<width&&x>=0; x++) {
			for(int y=beg_y; y<end_y&&y<height&&y>=0; y++) {
				switch(action) {
					case SET_GROUND:{
						map.getTile(x, y).ground = true;
					}
					break;
					case SET_WATER:{
						map.getTile(x, y).ground = false;
					}
					break;
				}
			}
		}
		chunk_size >>= 1;
		if(chunk_size>=1) {
			int right = ((int)Math.round(Math.random()));
			procedural(action, map, chunk_size, chunk_x*2-(1*right), chunk_y*2, argument);
			procedural(action, map, chunk_size, chunk_x*2-(1*(1-right)), chunk_y*2+1, argument);
			
			int left = ((int)Math.round(Math.random()));
			procedural(action, map, chunk_size, chunk_x*2+1+(1*left), chunk_y*2, argument);
			procedural(action, map, chunk_size, chunk_x*2+1+(1*(1-left)), chunk_y*2+1, argument);
			
			int top = ((int)Math.round(Math.random()));
			procedural(action, map, chunk_size, chunk_x*2, chunk_y*2-(1*top), argument);
			procedural(action, map, chunk_size, chunk_x*2+1, chunk_y*2-(1*(1-top)), argument);
			
			int down = ((int)Math.round(Math.random()));
			procedural(action, map, chunk_size, chunk_x*2, chunk_y*2+1+(1*down), argument);
			procedural(action, map, chunk_size, chunk_x*2+1, chunk_y*2+1+(1*(1-down)), argument);
		}
	}
	private static void proceduralNation(float chance_spread, int m_range, GameMap map, int x, int y, Nation nation) {
		Tile tile = map.getTileSafe(x, y);
		if(m_range==0)
			return;
		m_range--;
		if(tile!=null) {
			if(tile.owner==nation)
				return;
			if(tile.ground) {
				tile.owner = nation;
				Tile stile;
				stile = map.getTileSafe(x-1, y);
				if(stile!=null)
					if(Math.random()<chance_spread)
						proceduralNation(chance_spread, m_range, map, x-1, y, nation);
				stile = map.getTileSafe(x+1, y);
				if(stile!=null)
					if(Math.random()<chance_spread)
						proceduralNation(chance_spread, m_range, map, x+1, y, nation);
				stile = map.getTileSafe(x, y-1);
				if(stile!=null)
					if(Math.random()<chance_spread)
						proceduralNation(chance_spread, m_range, map, x, y-1, nation);
				stile = map.getTileSafe(x, y+1);
				if(stile!=null)
					if(Math.random()<chance_spread)
						proceduralNation(chance_spread, m_range, map, x, y+1, nation);
			}else {
				Tile stile;
				stile = map.getTileSafe(x-1, y);
				if(stile!=null)
					if(stile.ground) {
						tile.owner = nation;
					}
				stile = map.getTileSafe(x+1, y);
				if(stile!=null)
					if(stile.ground) {
						tile.owner = nation;
					}
				stile = map.getTileSafe(x, y-1);
				if(stile!=null)
					if(stile.ground) {
						tile.owner = nation;
					}
				stile = map.getTileSafe(x, y+1);
				if(stile!=null)
					if(stile.ground) {
						tile.owner = nation;
					}
			}
			
		}
	}
}
