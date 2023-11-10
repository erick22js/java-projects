package com.idcorporation.redoom64.gameplay.map;

import com.handling.IOBytes;
import com.handling.Wad;
import com.idcorporation.redoom64.utils.numberUtil;

public class Vertex {
	public float x;
	public float y;
	public Vertex(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public static Vertex[] loadVertexesFromMap(Wad map) {
		byte[] lump = map.get_lump_bytes(map.get_index_lump("VERTEXES", 0));
		Vertex[] vertices = new Vertex[lump.length/8];
		for(int v=0; v<vertices.length; v++) {
			int b = v*8;
			vertices[v] = new Vertex(
					numberUtil.signedFloat16(IOBytes.extractBytesList(lump, b, 4))
					, -numberUtil.signedFloat16(IOBytes.extractBytesList(lump, b+4, 4))
					);
		}
		return vertices;
	}
}
