package Asserty.hardware;

import Asserty.Cpu;
import Asserty.Device;
import Asserty.Regs;

public class MathDevice extends Device{
	@Override
	public boolean execute(Cpu cpu, int opr){
		switch(opr){
			case 32:{ //Eval sine
					float value = (float) (Math.sin(cpu.getRegister16(Regs.ax)/256f)*(Math.PI*2));
					cpu.setRegister(Regs.ax, (int)(value*256));
				}
				return true;
			case 33:{ //Eval cos
					float value = (float) (Math.cos(cpu.getRegister16(Regs.ax)/256f)*(Math.PI*2));
					cpu.setRegister(Regs.ax, (int)(value*256));
				}
				return true;
			case 34:{ //Eval tan
					float value = (float) (Math.tan(cpu.getRegister16(Regs.ax)/256f)*(Math.PI*2));
					cpu.setRegister(Regs.ax, (int)(value*16));
				}
				return true;
		}
		return false;
	}
}
