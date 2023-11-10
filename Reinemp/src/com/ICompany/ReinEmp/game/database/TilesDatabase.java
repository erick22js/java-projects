package com.ICompany.ReinEmp.game.database;

public class TilesDatabase {
	
	//int[] 
	
	// { 2 = 1, 8 = 2, 10 = 3, 11 = 4, 16 = 5, 18 = 6, 22 = 7, 24 = 8, 26 = 9, 27 = 10, 30 = 11, 31 = 12, 64 = 13, 66 = 14, 72 = 15, 74 = 16, 75 = 17, 80 = 18, 82 = 19, 86 = 20, 88 = 21, 90 = 22, 91 = 23, 94 = 24, 95 = 25 , 104 = 26, 106 = 27, 107 = 28, 120 = 29, 122 = 30, 123 = 31, 126 = 32, 127 = 33, 208 = 34, 210 = 35, 214 = 36, 216 = 37, 218 = 38, 219 = 39, 222 = 40, 223 = 41, 248 = 42, 250 = 43, 251 = 44, 254 = 45, 255 = 46, 0 = 47 }
	
	//Both flag
	public static final int SOLID_FLAG = 0b00000000001;
	
	//Tile flag
	public static final int JOINEFFECT_FLAG = 0b01000000000;
	
	//Ground flag
	public static final int FARM_FLAG = 0b00000000100;
	
	// -> Tiles formats
	// Flags
	// Uv => x and y indices
	// Args => 32 bit argument for uses
	
	public static int[][] grounds = new int[][] {
		new int[] {FARM_FLAG, 0, 1, 0}, //Grass top ground
		new int[] {FARM_FLAG, 0, 2, 0}, //Dirt ground
		new int[] {0, 0, 3, 0}, //Stone ground
		new int[] {0, 0, 4, 0}, //Sand ground
	};
	public static int[][] tiles = new int[][] {
		new int[] {0, 0, 0, 0}, //Air tile
		new int[] {SOLID_FLAG, 1, 5, 0}, //Rocks
		new int[] {SOLID_FLAG|JOINEFFECT_FLAG, 1, 7, 0}, //Stone wall
	};
}
