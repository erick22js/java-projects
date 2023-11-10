package com.circuitsimulator.woltex.core;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.circuitsimulator.woltex.core.circuit.WoltexComponent;
import com.circuitsimulator.woltex.core.circuit.WoltexWire;

public class WoltexNodeRenderer {
	
	WoltexComponent[] components;
	WoltexWire[] wires;
	
	public WoltexNodeRenderer() {
		
	}
	public void render(ShapeRenderer renderer, Matrix4 globalMatrix) {
		for(int i=0; i<components.length; i++) {
			components[i].render(renderer, globalMatrix);
		}
		for(int i=0; i<wires.length; i++) {
			
		}
	}
}
