package com.idcorporation.redoom64.gameplay.map;

public class GameMap {
	
	public Vertex[] VERTEXES;
	public Sector[] SECTORS;
	public Sidedef[] SIDEDEFS;
	public Linedef[] LINEDEFS;
	
	public Node[] NODES;
	public Segment[] SEGMENTS;
	public SubSector[] SSECTORS;
	
	public GameMap(Vertex[] VERTEXES, Sector[] SECTORS, Sidedef[] SIDEDEFS, Linedef[] LINEDEFS,  Node[] NODES, Segment[] SEGMENTS, SubSector[] SSECTORS) {
		this.VERTEXES = VERTEXES;
		this.SECTORS = SECTORS;
		this.SIDEDEFS = SIDEDEFS;
		this.LINEDEFS = LINEDEFS;
		
		this.NODES = NODES;
		this.SEGMENTS = SEGMENTS;
		this.SSECTORS = SSECTORS;
	}
}
