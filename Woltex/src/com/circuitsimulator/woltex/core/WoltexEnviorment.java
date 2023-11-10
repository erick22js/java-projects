package com.circuitsimulator.woltex.core;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.circuitsimulator.woltex.core.circuit.WoltexComponent;
import com.circuitsimulator.woltex.core.circuit.WoltexWire;

public class WoltexEnviorment {
	
	//For rendering
	public static ShapeRenderer shaperen = new ShapeRenderer();
	WoltexNodeRenderer[] nodes;
	
	ArrayList<WoltexComponent> components;
	ArrayList<WoltexWire> wires;
	WoltexController control;
	
	public WoltexEnviorment(WoltexController control) {
		this.control = control;
		components = new ArrayList<WoltexComponent>();
		wires = new ArrayList<WoltexWire>();
	}
	
	public void addComponent(WoltexComponent c) {
		components.add(c);
	}
	
	public void render() {
		control.update();
		//Matrix4 tr;
		for(WoltexComponent c:components) {
			//tr = control.combined.cpy().translate(c.position.x, y, z);
			c.render(shaperen, control.combined);
		}
	}
	
}
