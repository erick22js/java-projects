package com.test;

public class Main {
	
	static int[] v;
	
	public static void main(String[] args) {
		System.out.println("Initialized test!");
		
		v = new int[] {0, 0, 0};
		Printer p = new Printer(v);
		Exec p1 = new Exec(0, v);
		Exec p2 = new Exec(1, v);
		Exec p3 = new Exec(2, v);
		p.start();
		p1.start();
		p2.start();
		p3.start();
		try {
			p.join();
			p1.join();
			p2.join();
			p3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Ended threads!"+v[0]+"_"+v[1]+"_"+v[2]);
	}

}

class Exec extends Thread{
	int i;
	int[] h;
	public Exec(int index, int[] holder) {
		i = index;
		h = holder;
	}
	@Override
	public synchronized void start() {
		super.start();
	}
	@Override
	public void run() {
		super.run();
		while((h[0]+h[1]+h[2])<400_000_000) {
			h[i]++;
		}
	}
}

class Printer extends Thread{
	int[] h;
	public Printer(int[] holder) {
		h = holder;
	}
	@Override
	public synchronized void start() {
		super.start();
	}
	@Override
	public void run() {
		super.run();
		while((h[0]+h[1]+h[2])<400_000_000) {
			System.out.println("p: "+h[0]+"_"+h[1]+"_"+h[2]);
		}
	}
}