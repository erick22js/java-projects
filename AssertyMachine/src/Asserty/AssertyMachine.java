package Asserty;

import Asserty.hardware.DisplayDevice;

public class AssertyMachine extends Thread
{
	Memory ram;
	private Memory rom;
	public Memory[] bus;
	public Cpu cpu;
	//public DeviceRunner cpuRunner;
	public DisplayDevice display;
	//public DeviceRunner displayRunner;
	public Device[] devices;
	
	public float clockTime = 1000/30;
	
	public AssertyMachine(Memory ram, short[] bank_mapper,  Device...devices){
		this.ram = ram;
		rom = new Memory(0x0);
		this.devices = devices;
		//Argumentos respectivamente para bank_mapper:
		// Mapeador configurado para
		// 0 -> memória ram
		// 0 -> Estoque
		// 0 -> Estoque de estado
		// 1 -> código binário
		this.cpu = new Cpu(this.ram, bus, bank_mapper, devices);
		reBuildBus();
		for(Device dev:this.devices) {
			dev.initialize(this.cpu.bus, cpu);
		}
		//this.cpuRunner = new DeviceRunner(this.cpu);
	}
	

	//Monta toda a memória no sistema para fácil acesso
	public void reBuildBus(){
		bus = new Memory[ram.banksQ+rom.banksQ];
		for(int i=0; i<ram.banksQ; i++) {
			bus[i] = ram.getBank(i);
		}
		for(int i=0; i<rom.banksQ; i++) {
			bus[ram.banksQ+i] = rom.getBank(i);
		}
		cpu.bus = this.bus;
		cpu.reset();
		for(Device dev:devices) {
			dev.bus = bus;
			dev.updateShardMemory(bus);
		}
	}
	
	public void loadRom(Memory rom) {
		this.rom = rom;
		reBuildBus();
	}
	
	public void start(Memory program){
		this.loadRom(program);
		try {
			while(true) {
				cpu.step();
				for(Device dev:devices) {
					dev.step();
				}
				try {
					Thread.sleep(0);
				}catch(Exception e) {}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
