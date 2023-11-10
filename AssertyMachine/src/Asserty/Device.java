package Asserty;

public class Device
{
	//public Cpu cpu;
	//public int ClockRate = 500000/30;
	public Memory[] bus;
	
	public Device(){}
	
	public void initialize(Memory[] bus, Cpu cpu){
		
	}
	
	public void updateShardMemory(Memory[] bus) {
		
	}
	
	//Code of return
	public int step() {
		return 0;
	}
	
	public boolean execute(Cpu cpu, int opr){
		
		return true;
	}
}
