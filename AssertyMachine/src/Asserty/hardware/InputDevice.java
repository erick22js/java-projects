package Asserty.hardware;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Asserty.Cpu;
import Asserty.Device;
import Asserty.Memory;

public class InputDevice extends Device implements KeyListener{
	private int bankKeys;
	public Memory KRAM;
	public InputDevice(int bankKeys) {
		this.bankKeys = bankKeys;
	}
	@Override
	public void initialize(Memory[] bus, Cpu cpu) {
		KRAM = bus[bankKeys];
	}
	@Override
	public void updateShardMemory(Memory[] bus) {
		// TODO Auto-generated method stub
		KRAM = bus[bankKeys];
	}
	@Override
	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub
		switch(key.getKeyCode()) {
			case KeyEvent.VK_W:
				KRAM.set8(1, 0);
				break;
			case KeyEvent.VK_S:
				KRAM.set8(1, 1);
				break;
			case KeyEvent.VK_A:
				KRAM.set8(1, 2);
				break;
			case KeyEvent.VK_D:
				KRAM.set8(1, 3);
				break;
			case KeyEvent.VK_SPACE:
				KRAM.set8(1, 4);
				break;
				
		}
		//System.out.println("Pressed: "+key.getKeyChar());
	}
	@Override
	public void keyReleased(KeyEvent key) {
		// TODO Auto-generated method stub
		switch(key.getKeyCode()) {
			case KeyEvent.VK_W:
				KRAM.set8(0, 0);
				break;
			case KeyEvent.VK_S:
				KRAM.set8(0, 1);
				break;
			case KeyEvent.VK_A:
				KRAM.set8(0, 2);
				break;
			case KeyEvent.VK_D:
				KRAM.set8(0, 3);
				break;
			case KeyEvent.VK_SPACE:
				KRAM.set8(0, 4);
				break;
				
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
