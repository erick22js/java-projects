package Asserty.hardware;

import java.awt.Color;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardDownRightHandler;
import javax.swing.text.JTextComponent.KeyBinding;

import Asserty.Cpu;
import Asserty.Device;
import Asserty.Memory;
import Asserty.Regs;

public class DisplayDevice extends Device{
	
	public Display display;
	public InputDevice inputDevice = null;
	
	//Mapeador de memória gráfica
	private short[] mapperB = new short[]{3, 5, 6, 7, 8, 9};
	public final int VRAMB = 0;
	public final int PALB  = 1;
	public final int OAMB  = 2;
	public final int SHTB  = 3;
	public final int TMAPB = 4;
	public final int SETSB = 5;
	
	public DisplayDevice(short[] banks) {
		mapperB = banks;
	}
	
	public void implementsKeyInputDevice(InputDevice device) {
		inputDevice = device;
		if(display!=null)
			this.display.addKeyListener(inputDevice);
	}
	
	@Override
	public void initialize(Memory[] bus, Cpu cpu) {
		// TODO Auto-generated method stub
		Memory vram = bus[mapperB[VRAMB]];
		display = new Display(256, 224, vram, bus, mapperB);
		display.bus = bus;
		if(inputDevice!=null)
			this.display.addKeyListener(inputDevice);
	}
	
	@Override
	public void updateShardMemory(Memory[] bus) {
		// TODO Auto-generated method stub
		Memory vram = bus[mapperB[VRAMB]];
		if(display!=null) {
			display.VRAM = vram;
			display.bus = bus;
		}
	}
	
	@Override
	public boolean execute(Cpu cpu, int opr) {
		switch(opr) {
			case 128:{ //Draw pixel by index
					//Get pixel position
					int pixel = cpu.getRegister16(Regs.bx);
					//Get pixel color
					int color = cpu.getRegister16(Regs.ax);
					//System.out.println("painting!");
					display.VRAM.set16(color, (pixel)*4);
					//display.VRAM.set32();
				}
				return true;
			case 129:{ //Draw pixel by coords
					//Get pixel position
					int pixelx = cpu.getRegister8(Regs.bx);
					int pixely = cpu.getRegister8(Regs.cx);
					//Get pixel color
					int color = cpu.getRegister16(Regs.ax);
					//System.out.println("painting!");
					display.VRAM.set16(color, (pixely*256+pixelx)*4);
					//display.VRAM.set32();
				}
				return true;
			case 130:{ //Draw Rect
					//Get pixel position
					short rx = (short)(cpu.getRegister16(Regs.bx)/16);
					short ry = (short)(cpu.getRegister16(Regs.cx)/16);
					rx = rx>0x800?(short)(rx-0xfff):rx;
					ry = ry>0x800?(short)(ry-0xfff):ry;
					int rw = cpu.getRegister16(Regs.dx);
					int rh = cpu.getRegister16(Regs.ex);
					//Get pixel color
					int color = cpu.getRegister16(Regs.ax);
					//System.out.println("painting!");
					for(int y=(ry<0?-ry:0); y<rh&&(ry+y)<224; y++)
						for(int x=(rx<0?-rx:0); x<rw&&(rx+x)<256; x++)
							display.VRAM.set16(color, ((y+ry)*256+x+rx)*4);
				}
				return true;
			case 140:{ //Draw Polygon
					
				}
				return true;
			case 151:{ //Force vblank and branch execution
					int address = cpu.getRegister16(Regs.ax);
					cpu.branchStepCpu(address, 177);
					display.executeVblank();
					//display.vblankP++;
				}
				return true;
			case 152:{ //Force entirely vblank and branch execution
					do {
						int address = cpu.getRegister16(Regs.ax);
						cpu.branchStepCpu(address, 177);
					}while(!display.executeVblank());
					//display.vblankP++;
				}
				return true;
			case 153:{ //Force vblank
					display.executeVblank();
					//display.vblankP++;
				}
				return true;
			case 154:{ //Force most vblank
					int siz = cpu.getRegister16(Regs.ax);
					for(int v=0; v<siz&&!display.executeVblank();v++) {}
					//display.vblankP++;
				}
				return true;
			case 155:{ //Force most vblank (no security)
					int siz = cpu.getRegister16(Regs.ax);
					for(int v=0; v<siz;v++) {
						display.executeVblank();
					}
					//display.vblankP++;
				}
				return true;
			case 156:{ //Force entirely vblank
					while(!display.executeVblank()) {}
				}
				return true;
			case 157:{ //Force entirely vblank and flush screen
					while(!display.executeVblank()) {}
					display.flush();
				}
				return true;
			case 158:{ //Only flush screen
					display.flush();
				}
				return true;
			case 159:{ //Unlock flush screen (Must use with warning)
					display.flushed = false;
				}
				return true;
		}
		return false;
	}
	
	@Override
	public int step() {
		// TODO Auto-generated method stub
		display.flush();
		display.flushed = false;
		//display.buffer.createGraphics()
		//display.buffer.getGraphics().setColor(new Color(0xff));
		//display.buffer.getGraphics().fillRect(10, 10, 32, 32);
		return -1;
	}
}
