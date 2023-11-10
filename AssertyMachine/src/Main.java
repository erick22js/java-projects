import Asserty.compiler.*;
import Asserty.hardware.ConsoleDevice;
import Asserty.hardware.Display;
import Asserty.hardware.DisplayDevice;
import Asserty.hardware.InputDevice;
import Asserty.hardware.MathDevice;
import Asserty.hardware.Palette;
import Asserty.*;
import java.nio.*;
import java.util.*;
import java.io.*;

public class Main{
	
	public static final String s_proj = "C:/Users/Erick/Documents/Programas/eclipse/2018/eclipse/workspace/AssertyMachine/projtest/";
	
	public static void main(String[] args){
		//System.out.println(FileDeal.readFileString(s_proj+"main.ast"));
		System.out.println("Color cv: "+Integer.toBinaryString(Palette.color5bTo8b(0b111110000011111)));
		Importer ipt = new Importer() {
			@Override
			public String getFile(String namespace) throws Exception {
				// TODO Auto-generated method stub
				String src = FileDeal.readFileString(s_proj+namespace);
				if(Tokener.equalStr(src, "")){
					throw new Exception("Can't found file with name '"+namespace+"'");
				}
				return src;
			}
			
			@Override
			public String getModule(String namespace) throws Exception{
				throw new Exception("Can't found module with name '"+namespace+"'");
				//return "";
			}
		};
		
		
		
		AssertyParser parser = new AssertyParser(ipt);
		AssertyCompiler compiler = new AssertyCompiler();
		byte[] bin = null;
				try {
					JsonObj map = parser.parseMap(FileDeal.readFileString(s_proj+"main.ast"), "main.ast");
			
			//System.out.println(map.toString(2));
			bin = compiler.compile(map);
			
			FileDeal.writeBytesToFile(s_proj+"bin.ot", bin);
			//******************Execution
			
			MemoryMapper mapper = new MemoryMapper(new int[]{
				/*00*/0x000000, 0x600000,
				/*01*/0x600000, 0x400000,
				/*02*/0xa00000, 0x300000,
				/*03*/0xd00000, 0x50000, //Video Ram
				/*04*/0xd50000, 0x100, //Keys binding
				/*05*/0xd50100, 0x400, //OAM
				  //Graphical ram
				/*06*/0xd50500, 0xcd90, //Pallete
				/*07*/0xd5d290, 0x100000, //Tiles storage
				/*08*/0xe5d290, 0x100000, //Tilemap storage
				/*09*/0xf5d290, 0x2000, //Draws Settings
			});
			Memory ram = null;
			try {
				ram = new Memory(0xffffff, mapper); //16kb
			}catch(Exception e) {
				e.printStackTrace();
			}
			Device console = new ConsoleDevice();
			Device math = new MathDevice();
			
			DisplayDevice display = new DisplayDevice(new short[] {(short)3,(short)5,(short)6,(short)7,(short)8,(short)9,});
			InputDevice input = new InputDevice(4);
			display.implementsKeyInputDevice(input);
			AssertyMachine machine = new AssertyMachine(
					ram, new short[] {0, 1, 2, (short) mapper.Map.length}, 
					console, math, display, input);
			machine.start(new Memory(bin, true));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
