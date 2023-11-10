package com.idcorporation.redoom64.gameplay.map;

import java.util.ArrayList;

import com.handling.Wad;

public class MapLoader {
	
	Wad wadMap;
	
	Segment[] SEGS;
	
	Vertex[] VERTEXES;
	Sector[] SECTORS;
	Sidedef[] SIDEDEFS;
	Linedef[] LINEDEFS;
	
	SubSector[] SSECTORS;
	Segment[] SEGMENTS;
	Node[] NODES;
	
	public MapLoader(Wad wadMap) {
		this.wadMap = wadMap;
		
		VERTEXES = Vertex.loadVertexesFromMap(wadMap);
		SECTORS = Sector.loadSectorsFromMap(wadMap);
		SIDEDEFS = Sidedef.loadSidedefsFromMap(wadMap, SECTORS);
		LINEDEFS =  Linedef.loadLinedefsFromMap(wadMap, VERTEXES, SIDEDEFS);
		
		SSECTORS = SubSector.loadSubSectorsFromMap(wadMap);
		SEGMENTS = Segment.loadSegsFromMap(wadMap, VERTEXES, LINEDEFS);
		NODES = Node.loadNodesFromMap(wadMap);
		
		//******************Preset sectors linedef lists
		
		ArrayList<Linedef>[] linedefs = new ArrayList[SECTORS.length];
		for(int l=0; l<LINEDEFS.length; l++) {
			Integer s1 = null; Integer s2 = null;
			if(LINEDEFS[l].front != null) {
				int s = LINEDEFS[l].front.sector.index;
				if(linedefs[s]==null) {
					linedefs[s] = new ArrayList<Linedef>();
				}
				linedefs[s].add(LINEDEFS[l]);
			}
			if(LINEDEFS[l].back != null) {
				int s = LINEDEFS[l].back.sector.index;
				if(linedefs[s]==null) {
					linedefs[s] = new ArrayList<Linedef>();
				}
				linedefs[s].add(LINEDEFS[l]);
			}
		}
		for(int l=0; l<linedefs.length; l++) {
			if(linedefs[l]==null) {
				SECTORS[l].linedefs = new Linedef[0];
				continue;
			}
			Linedef[] lines = new Linedef[linedefs[l].size()];
			for(int li=0; li<lines.length; li++) {
				lines[li] = linedefs[l].get(li);
				
			}
			SECTORS[l].linedefs = lines;
		}
		
		//*********************Preset nodes and subsectors
		
		for(int n=0; n<NODES.length; n++) {
			Node node = NODES[n];
			if(node.right<32768) {
				node.rightNode = NODES[node.right];
			}else {
				node.rightSSector = SSECTORS[node.right-32768];
			}
			if(node.left<32768) {
				node.leftNode = NODES[node.left];
			}else {
				node.leftSSector = SSECTORS[node.left-32768];
			}
		}
		
		for(int ss=0; ss<SSECTORS.length; ss++) {
			SubSector subSector = SSECTORS[ss];
			Segment[] segs = new Segment[subSector.countSSeg];
			for(int s=0; s<segs.length; s++)
				segs[s] = SEGMENTS[subSector.firstSSeg+s];
			subSector.SEGS = segs;
		}
		
	}
	
	public GameMap retrieveLoadedMap() {
		return new GameMap(VERTEXES, SECTORS, SIDEDEFS, LINEDEFS,  NODES, SEGMENTS, SSECTORS);
	}
	
}
