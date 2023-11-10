package com.idcorporation.redoom64.gameplay.map;

import com.handling.IOBytes;
import com.handling.Wad;
import com.idcorporation.redoom64.utils.numberUtil;

public class Segment {
	
	public Vertex v1;
	public Vertex v2;
	public int angle;
	public Linedef line;
	public boolean isFront;
	public int offset;
	
	enum facing{
		FRONT, BACK
	};
	
	public Segment(Vertex v1, Vertex v2, int angle, Linedef line, boolean front, int offset) {
		this.v1 = v1;
		this.v2 = v2;
		this.angle = angle;
		this.line = line;
		this.isFront = front;
		this.offset = offset;
	}
	
	public static Segment[] loadSegsFromMap(Wad map, Vertex[] vertices, Linedef[] LINEDEFS) {
		byte[] lump = map.get_lump_bytes(map.get_index_lump("SEGS", 0));
		Segment[] segs = new Segment[lump.length/12];
		for(int v=0; v<segs.length; v++) {
			int b = v*12;
			segs[v] = new Segment(
					vertices[IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b, 2))],
					vertices[IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+2, 2))],
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+4, 2)),
					LINEDEFS[IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+6, 2))],
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+8, 2))==0,
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+10, 2))
					);
		}
		return segs;
	}
	
}
