package com.idcorporation.redoom64.gameplay.map;

import com.handling.IOBytes;
import com.handling.Wad;

public class Linedef {
	public Vertex v1;
	public Vertex v2;
	public int index;
	public int flags;           //#For view
	public int specialAction;   //#For view
	public int sectorTag;       //#For view
	public Sidedef front;
	public Sidedef back;
	
	public boolean forDraw = false;
	
	public Linedef(Vertex v1, Vertex v2, int index, int flags, int specialAction, int sectorTag, Sidedef front, Sidedef back) {
		this.v1 = v1;
		this.v2 = v2;
		this.index = index;
		this.flags = flags;
		this.specialAction = specialAction;
		this.sectorTag = sectorTag;
		this.front = front;
		this.back = back;
	}
	
	public static Linedef[] loadLinedefsFromMap(Wad map, Vertex[] vertices, Sidedef[] sidedefs) {
		byte[] lump = map.get_lump_bytes(map.get_index_lump("LINEDEFS", 0));
		Linedef[] linedefs = new Linedef[lump.length/16];
		for(int v=0; v<linedefs.length; v++) {
			int b = v*16;
			int sidefront = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+12, 2));
			int sideback = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+14, 2));
			linedefs[v] = new Linedef(
					vertices[IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b, 2))],
					vertices[IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+2, 2))],
					v,
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+4, 4)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+8, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+10, 2)),
					sidefront<65535?sidedefs[sidefront]:null,
					sideback<65535?sidedefs[sideback]:null
					);
			//System.out.println(lump.length);
		}
		return linedefs;
	}
}
