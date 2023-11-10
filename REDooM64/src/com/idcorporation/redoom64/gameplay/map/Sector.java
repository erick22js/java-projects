package com.idcorporation.redoom64.gameplay.map;

import com.badlogic.gdx.graphics.Color;
import com.handling.IOBytes;
import com.handling.Wad;

public class Sector {
	
	public int index;
	
	public int floorY;
	public int ceilY;
	public int textureFloor;
	public int textureCeil;
	public Color colorThing;
	public Color colorWallBottom;
	public Color colorWallTop;
	public int specialAction;
	public int tag;
	public int flags;
	
	public Linedef[] linedefs;

	public boolean forDraw = false;
	
	public Sector(int index, int floorY, int ceilY, int textureFloor, int textureCeil, Color colorThing, Color colorWallTop, Color colorWallBottom, int specialAction, int tag, int flags) {
		this.index = index;
		this.floorY = floorY;
		this.ceilY = ceilY;
		this.textureFloor = textureFloor;
		this.textureCeil = textureCeil;
		this.colorThing = colorThing;
		this.colorWallBottom = colorWallBottom;
		this.colorWallTop = colorWallTop;
		this.specialAction = specialAction;
		this.tag = tag;
		this.flags = flags;
	}
	public static Sector[] loadSectorsFromMap(Wad map) {
		byte[] lump = map.get_lump_bytes(map.get_index_lump("SECTORS", 0));
		Sector[] sectors = new Sector[lump.length/24];
		for(int v=0; v<sectors.length; v++) {
			int b = v*24;
			sectors[v] = new Sector(
					v,
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+2, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+4, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+6, 2)),
					new Color(1,1,1,1),
					new Color(1,1,1,1),
					new Color(1,1,1,1),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+18, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+20, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+22, 2))
					);
		}
		return sectors;
	}
}
