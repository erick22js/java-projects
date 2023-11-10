package com.idcorporation.redoom64.renderization;

import com.badlogic.gdx.math.Matrix4;
import com.idcorporation.redoom64.gameplay.map.Linedef;
import com.idcorporation.redoom64.gameplay.map.Vertex;

public class Projection {
	public float x;
	public float y; //Altitude
	public float z; //Depth
	public float direction;
	public float fov;
	public Matrix4 mat;
	public Projection(float x, float y, float z, float direction, float fov, Matrix4 mat) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.direction = direction;
		this.fov = fov;
		this.mat = mat;
	}
	public float[] xsFromLinedef(Linedef line) {
		float[] xs = new float[2];
		xs[0] = getXVertex(mat, line.v1);
		xs[1] = getXVertex(mat, line.v2);
		return xs;
	}
	public float getXVertex(Matrix4 mat, Vertex v) {
		float v0 = v.x;
		float v1 = 0;
		float v2 = v.y;
		float[] m = mat.getValues();
		float d = v0 * m[0 * 4 + 3] + v1 * m[1 * 4 + 3] + v2 * m[2 * 4 + 3] + m[3 * 4 + 3];
		return (v0 * m[0 * 4 + 0] + v1 * m[1 * 4 + 0] + v2 * m[2 * 4 + 0] + m[3 * 4 + 0]) / d;
	}
}
