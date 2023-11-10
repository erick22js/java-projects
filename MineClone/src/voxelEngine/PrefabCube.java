package voxelEngine;

public class PrefabCube {
	static final float[][] SIDES = new float[][] {
		new float[] { //Left
			0, 1, 0,
			0, 0, 0,
			0, 0, 1,
			0, 1, 1,
		},
		new float[] { //Right
			1, 1, 1,
			1, 0, 1,
			1, 0, 0,
			1, 1, 0,
		},
		new float[] { //Bottom
			0, 0, 1,
			0, 0, 0,
			1, 0, 0,
			1, 0, 1,
		},
		new float[] { //Top
			1, 1, 0,
			0, 1, 0,
			0, 1, 1,
			1, 1, 1,
		},
		new float[] { //Back
			1, 1, 0,
			1, 0, 0,
			0, 0, 0,
			0, 1, 0,
		},
		new float[] { //Front
			0, 1, 1,
			0, 0, 1,
			1, 0, 1,
			1, 1, 1,
		},
	};
}
