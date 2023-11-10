package com.idcorporation.redoom64.gameplay.map;

import com.handling.IOBytes;
import com.handling.Wad;

public class SubSector {
	
	public int index = 0;
	public Segment[] SEGS;
	public int firstSSeg;
	public int countSSeg;
	
	public SubSector(int index, int firstSSeg, int countSSeg) {
		this.index = index;
		this.firstSSeg = firstSSeg;
		this.countSSeg = countSSeg;
	}
	public static SubSector[] loadSubSectorsFromMap(Wad map) {
		byte[] lump = map.get_lump_bytes(map.get_index_lump("SSECTORS", 0));
		SubSector[] ssectors = new SubSector[lump.length/4];
		for(int v=0; v<ssectors.length; v++) {
			int b = v*4;
			ssectors[v] = new SubSector(
					v,
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+2, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b, 2))
					);
		}
		return ssectors;
	}
}
