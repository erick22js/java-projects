package Asserty.hardware;

import java.util.Scanner;

import Asserty.Cpu;
import Asserty.Device;
import Asserty.Regs;

public class ConsoleDevice extends Device{
	@Override
	public boolean execute(Cpu cpu, int opr){
		switch(opr){
			case 0:{
					int bg = cpu.getRegister16(Regs.ax);
					int sz = cpu.getRegister16(Regs.bx);
					String out = "";
					for(int i=0; i<sz; i++)
						out += String.valueOf((char)cpu.getMemoryBus8(cpu.getBankMemory(), bg+i));
					System.out.println(out);
					System.out.flush();
				}
				return true;
			case 1:{
					Scanner input = new Scanner(System.in);
					String in = input.next();
					int bg = cpu.getRegister16(Regs.ax);
					int sz = in.length();
					for(int i=0; i<sz; i++){
						cpu.setMemoryBus8(
							cpu.getBankMemory(),
							bg+i,
							in.charAt(i));
					}
					System.out.flush();
					cpu.setRegister(Regs.dls, sz);
				}
				return true;
			case 2:{
					int value = cpu.getRegister16(Regs.ax);
					System.out.println(value);
					System.out.flush();
				}
				return true;
			case 3:{ //Pretty print hex number
					int value = cpu.getRegister16(Regs.ax);
					System.out.println("0x"+Integer.toHexString(value));
					System.out.flush();
				}
				return true;
			case 4:{ //Pretty print bin number
					int value = cpu.getRegister16(Regs.ax);
					System.out.println("0b"+Integer.toBinaryString(value));
					System.out.flush();
				}
				return true;
			case 12:{ //Stop for debug
					int interrupt = 1;
					interrupt++;
					System.out.println("Debug!");
				}
		}
		return false;
	}
}
