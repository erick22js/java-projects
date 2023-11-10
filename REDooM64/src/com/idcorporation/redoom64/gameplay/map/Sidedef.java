package com.idcorporation.redoom64.gameplay.map;

import com.handling.IOBytes;
import com.handling.Wad;

public class Sidedef {
	
	public int offsetX;
	public int offsetY;
	public int textureLower;
	public int textureMiddle;
	public int textureUpper;
	public Sector sector;
	
	public Sidedef(int offsetX, int offsetY, int textureLower, int textureUpper, int textureMiddle, Sector sector) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.textureLower = textureLower;
		this.textureMiddle = textureMiddle;
		this.textureUpper = textureUpper;
		this.sector = sector;
	}
	public static Sidedef[] loadSidedefsFromMap(Wad map, Sector[] sectors) {
		byte[] lump = map.get_lump_bytes(map.get_index_lump("SIDEDEFS", 0));
		Sidedef[] sidedefs = new Sidedef[lump.length/12];
		for(int v=0; v<sidedefs.length; v++) {
			int b = v*12;
			sidedefs[v] = new Sidedef(
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+2, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+4, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+6, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+8, 2)),
					sectors[IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+10, 2))]
					);
		}
		return sidedefs;
	}
}
