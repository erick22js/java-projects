package com.circuitsimulator.woltex.core.circuit.components;

import com.circuitsimulator.woltex.core.circuit.WoltexComponent;
import com.circuitsimulator.woltex.core.circuit.WoltexPin;

public class AND_Component extends WoltexComponent{

	public AND_Component() {
		super("AND_GATE.JS");
		pins = new WoltexPin[] {
			new WoltexPin(this, true, -1.5f,  .8f, (float)Math.PI),
			new WoltexPin(this, true, -1.5f, -.8f, (float)Math.PI),
			new WoltexPin(this, false, 1.5f,   0f, 0),
		};
		//int e = 50;
	}
	
}
