package Asserty.hardware;

import Asserty.Memory;

public class Palette {
	
	public static int palIndex = 0;
	public int[][] palettes;
	
	public Palette(Memory bank) {
		palettes = new int[256][];
		for(int p=0; p<256; p++) {
			palettes[p] = new int[16];
			for(int c=0; c<16; c++) {
				palettes[p][c] = color5bTo8b(bank.get16((p*16+c)*2));
			}
		}
	}
	public int getColor(int pal, int i) {
		return palettes[pal][i];
	}
	
	public static int color5bTo8b(int c) {
		return 
				((((c&0b000000000011111)>>0)*8)<<0)+//Blue
				((((c&0b000001111100000)>>5)*8)<<8)+//Green
				((((c&0b111110000000000)>>10)*8)<<16)//Red
				;
	}
}
