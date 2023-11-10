package com.Techne.renderer.Layout;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

public class Container extends View{
	
	//Main properties
	public ArrayList<View> childs;
	
	public Container() {
		super();
		childs = new ArrayList<View>();
		
	}
	
	@Override
	protected boolean dispatchEventTouch(Matrix4 pMatrix, TouchEvent ev, float x, float y) {
		// TODO Auto-generated method stub
		boolean inBound = super.dispatchEventTouch(pMatrix, ev, x, y);
		int size = childs.size();
		View v;
		for(int i=0; i<size; i++) {
			v = childs.get(i);
			v.dispatchEventTouch(ev, x, y);
		}
		return inBound;
	}
	
	public void addChild(View view) {
		if(view.style==null)
			view.style = this.style;
		childs.add(view);
	}
	public void removeChild(int index) {
		childs.remove(index);
	}
	public boolean removeChild(View view) {
		int size = childs.size();
		View v;
		for(int i=0; i<size; i++) {
			v = childs.get(i);
			if(v==view) {
				childs.remove(i);
				return true;
			}
		}
		return false;
	}
	public boolean hasChild(View view) {
		int size = childs.size();
		View v;
		for(int i=0; i<size; i++) {
			v = childs.get(i);
			if(v==view) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Matrix4 render(Matrix4 pMatrix, Layout pLayout) {
		return super.render(pMatrix, pLayout);
	}
	protected Matrix4 renderChilds(Matrix4 pMatrix, Layout pLayout) {
		int size = childs.size();
		View v;
		for(int i=0; i<size; i++) {
			v = childs.get(i);
			v.render(matrix, pLayout);
		}
		return matrix;
	}
}
