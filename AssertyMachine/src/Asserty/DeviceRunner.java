package Asserty;

public class DeviceRunner extends Thread
{
	public Device device;
	public int ClockRate = 1000/30;
	
	public boolean running = true;
	
	public DeviceRunner(Device device){
		this.device = device;
	}
	public void run(){
		//Cpu cpu = (Cpu)device;
		//while(true){
		//	try{
		device.step();
		//		this.sleep(20);
		//	}catch(Exception e){
				
		//	}
		//}
	}
}
