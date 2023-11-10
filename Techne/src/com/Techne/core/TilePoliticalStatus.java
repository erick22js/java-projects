package com.Techne.core;

public class TilePoliticalStatus {
	
	//TODO: Political status
	public static int TILE_AREA = 2500; //Meters^2
	// s => size => m^2
	//Political geography tile aspects
	public static final short houses_s = 15; //Ex: Each house is 10m^2
	public short houses = 0;
	public static final short commerces_s = 40;
	public short commerces = 0;
	public static final short industries_s = 200;
	public short industries = 0;
	
	//Political tile aspects
	public static final short population_fh = 5; //Max population for houses
	public short population = 0;
	public static final short soldiers_fp = 5; //Max allowed soldier for population
	public short soldiers = 0;
	
	
	public TilePoliticalStatus() {}
}
