package com.idcorporation.redoom64.utils;

import com.idcorporation.redoom64.gameplay.map.Vertex;

public class MathDoom {
	final public static float PI = (float)Math.PI;
	public static float angleVector(float x1, float y1, float x2, float y2) {
		return (float) Math.atan2(y2-y1, x2-x1);
	}
	public static float angleVector(float x, float y) {
		return (float) Math.atan2(y, x);
	}
	public static float distanceVector(float x1, float y1, float x2, float y2) {
		float c1 = x2-x1;
		float c2 = y2-y1;
		return (float) Math.sqrt(c1*c1+c2*c2);
	}
	public static float distanceVectorSquare(float x1, float y1, float x2, float y2) {
		return (x2-x1)+(y2-y1);
	}
	public static boolean pointInPolygon(float px, float py, Vertex[] pol) {
		boolean collision = false;
		int next = 0;
		for(int current=0; current<pol.length; current++) {
			next = current+1;
			if (next == pol.length)
				next = 0;
			Vertex vc = pol[current];
			Vertex vn = pol[next];
			if (((vc.y >= py && vn.y < py) || (vc.y < py && vn.y >= py)) &&(px < (vn.x-vc.x)*(py-vc.y) / (vn.y-vc.y)+vc.x))
				collision = !collision;
		}
		return collision;
	}
	
}
