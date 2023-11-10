package Asserty.hardware;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.JFrame;

import Asserty.Memory;

public class Display extends Canvas{
	
	private int Width;
	private int Height;
	
	protected Graphics graphics;
	protected BufferStrategy bufferS;
	protected BufferedImage buffer;
	protected Graphics bufferG;
	public Memory VRAM;
	public Memory[] bus;
	private Palette pal;
	public JFrame frame;
	public boolean execute = false;
	
	//Mapeador de memória gráfica
	private short[] mapperB = new short[]{3, 5, 6, 7, 8, 9};
	public final int VRAMB = 0;
	public final int PALB  = 1;
	public final int OAMB  = 2;
	public final int SHTB  = 3;
	public final int TMAPB = 4;
	public final int SETSB = 5;
	
	public int vblankP = 0;
	private int[] clearLine;
	public boolean flushed = false;
	
	public Display(int Width, int Height, Memory VRAM, Memory[] bus, short[] mapperB){
		this.Width = Width;
		this.Height = Height;
		//if(VRAM.size()>=Width*Height*4)
		this.mapperB = mapperB;
		this.VRAM = VRAM;
		this.bus = bus;
		this.pal = new Palette(bus[mapperB[PALB]]);
		this.clearLine = new int[Width];
		initialize();
	}
	
	public void initialize() {
		this.setPreferredSize(new Dimension(Width*2, Height*2));
		buffer = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		bufferG = buffer.getGraphics();
		frame = new JFrame("Display Output");
		frame.add(this);
		frame.setResizable(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//this.getGraphics().
		//flush();
	}
	
	public void flush() {
		if(flushed)
			return;
		bufferS = this.getBufferStrategy();
		if(bufferS==null) {
			this.createBufferStrategy(3);
			return;
		}
		requestFocus();
		graphics = bufferS.getDrawGraphics();
		graphics.drawImage(buffer, 0, 0, frame.getWidth(), frame.getHeight(), null);
		bufferS.show();
		flushed = true;
	}
	//public void 
	public boolean executeVblank() { //Return if ended cross screen
		int b = SetsVblank.beginHblank(bus[mapperB[SETSB]]);
		int e = SetsVblank.endHblank(bus[mapperB[SETSB]]);
		buffer.setRGB(0, vblankP, Width, 1, clearLine, 0, 1);
		for(int x=b; x<Width; x++) {
			buffer.setRGB(x, vblankP, Palette.color5bTo8b(VRAM.get16((x*4+vblankP*(Width*4)))) );
			VRAM.set16(0x000000, (x*4+vblankP*(Width*4)));
		}
		vblankP++;
		vblankP = vblankP%Height;
		return vblankP==0;
	}
}

class OAM{
	
}

class SetsVblank{
	public static int beginHblank(Memory bank) {
		int b = bank.get8(0); 
		return b<0?0:b;
	}
	public static int endHblank(Memory bank) {
		return bank.get8(1);
	}
}
