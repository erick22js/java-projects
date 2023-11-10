package com.ICompany.ReinEmp.game.core;

public class ChunkTile {
	public short id;
	public short ground;
	public byte flags;
	public static final byte FLAG_HAS_STORAGE = 0b0000001;
	public static final byte FLAG_OBSTRUCTED = 0b0000010;
	public ItemsStorage storage;
	public ChunkTile(short id, short ground, byte flags) {
		this.id = id;
		this.ground = ground;
		this.flags = (byte) (flags|FLAG_HAS_STORAGE);
		this.storage = null;
	}
	public ChunkTile(short id, short ground, byte flags, ItemsStorage storage) {
		this.id = id;
		this.ground = ground;
		this.flags = flags;
		this.storage = storage;
	}
}
