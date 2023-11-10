package com.Techne.renderer.Layout;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class View {
	
	//Static event variables
	public static enum TouchEvent{
		DOWN,
		UP,
		MOVE,
	}
	
	//Element properties
	public String name = "";
	
	//Spatial properties
	protected static Vector3 eventTouchVec3 = new Vector3();
	protected static Vector2 resolutionVec2 = new Vector2();
	protected static Matrix4 emptyMatrix = new Matrix4();
	protected Matrix4 matrix;
	public Rectangle bound_box;
	
	//Visual properties
	public StyleSkin style = StyleSkin.default_skin;
	
	//Constructor
	public View() {
		matrix = new Matrix4();
		bound_box = new Rectangle();
	}
	
	//Event handdler
	public void dispatchEventTouch(TouchEvent ev, float x, float y) {
		dispatchEventTouch(matrix, ev, x, y);
	}
	protected boolean dispatchEventTouch(Matrix4 pMatrix, TouchEvent ev, float x, float y) {
		eventTouchVec3.set(x, y, 0);
		eventTouchVec3.add(-pMatrix.val[12], -pMatrix.val[13], 0f);
		eventTouchVec3.scl(1f/pMatrix.val[0], 1f/pMatrix.val[5], 1f);
		boolean inBound = eventTouchVec3.x>=0f&&eventTouchVec3.x<1f&&eventTouchVec3.y>=0f&&eventTouchVec3.y<1f;
		if(inBound)
			onTouchEvent(ev, eventTouchVec3.x, eventTouchVec3.y);
		return inBound;
	}
	
	//Overridable events
	protected void onTouchEvent(TouchEvent ev, float x, float y) {}
	
	//Matrix updater
	protected Matrix4 updateMatrix(Matrix4 pMatrix) {
		matrix.set(pMatrix);
		matrix.translate(bound_box.x, bound_box.y, 0f);
		matrix.scale(bound_box.width, bound_box.height, 1f);
		return matrix;
	}
	
	//View renderer
	protected Matrix4 render(Matrix4 pMatrix, Layout pLayout) {
		updateMatrix(pMatrix);
		return matrix;
	}
}
