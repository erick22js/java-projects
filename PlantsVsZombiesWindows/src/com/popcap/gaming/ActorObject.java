package com.popcap.gaming;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.popcap.gaming.Resources.Resources;

public class ActorObject {
	public float x = 0;
	public float y = 0;
	public float scaleX = 1;
	public float scaleY = 1;
	public float pivotX = 0;
	public float pivotY = 0;
	public float angle = 0;
	
	public float[] color = new float[] {1,1,1,1};
	
	public Matrix4 matrix = new Matrix4();
	
	public void prepareForDraw() {
		matrix = Device.ResetView.cpy();
		matrix.translate(x, y, 0);
		matrix.rotate(0, 0, 1, angle);
		matrix.scale(scaleX, scaleY, 1);
		matrix.translate(pivotX, pivotY, 0);
		Resources.batchRender.getShader().setUniformMatrix("obj_projTrans", matrix);
		Resources.batchRender.getShader().setUniform4fv("color", color, 0, 4);//("color", );
	}
}
