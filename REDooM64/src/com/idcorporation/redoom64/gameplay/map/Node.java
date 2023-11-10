package com.idcorporation.redoom64.gameplay.map;

import com.handling.IOBytes;
import com.handling.Wad;

public class Node {
	
	public int x;
	public int y;
	public int dx;
	public int dy;
	
	public int right;
	public Node rightNode = null;
	public SubSector rightSSector = null;
	
	public int left;
	public Node leftNode = null;
	public SubSector leftSSector = null;
	
	public Node(int x, int y, int dx, int dy, int right, int left) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.right = right;
		this.left = left;
	}
	public static Node[] loadNodesFromMap(Wad map) {
		byte[] lump = map.get_lump_bytes(map.get_index_lump("NODES", 0));
		Node[] nodes = new Node[lump.length/28];
		for(int v=0; v<nodes.length; v++) {
			int b = v*28;
			nodes[v] = new Node(
					IOBytes.UintToInt(IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b, 2))),
					IOBytes.UintToInt(IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+2, 2))),
					IOBytes.UintToInt(IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+4, 2))),
					IOBytes.UintToInt(IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+6, 2))),
					
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+24, 2)),
					IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+26, 2))
					//IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b, 2)),
					//IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+2, 2)),
					//IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+4, 2)),
					//IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+6, 2)),
					//IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+8, 2)),
					//sectors[IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(lump, b+10, 2))]
					);
		}
		return nodes;
	}
}
