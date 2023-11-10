package com.tester;


import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import org.json.JSONArray;

import com.handling.DoomWad;
import com.handling.IOBytes;
import com.handling.Wad;




public class Principal{
	
	final static String path = "C:/Users/Erick/Documents/Programas/eclipse/2018/eclipse/workspace/Wad/assets/";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Wad wad = new Wad(path+"DOOM64.WAD");
		
		try {
			Soundbank bank = MidiSystem.getSoundbank(IOBytes.bytesAsInputStream(IOBytes.bytesFromFile(path+"DOOMSND.SF2")));
			Sequencer seqr = MidiSystem.getSequencer(false);
			Synthesizer sinth = MidiSystem.getSynthesizer();
			
			seqr.open();
			sinth.open();
			sinth.loadAllInstruments(bank);
			
			seqr.getTransmitter().setReceiver(sinth.getReceiver());
			
			seqr.setSequence(IOBytes.bytesAsInputStream(wad.get_lump_bytes(1611)));
			/*
			Sequence seq = MidiSystem.getSequence(ip);
			
			seqr.setSequence(seq);*/
			seqr.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Erro: "+e);
		}
		
		//PNGDecoder dec = new PNGDecoder(new Image);
		
		wad.export_lump_as(1499, path+"punch.mid");
		
		System.out.println(wad.lumps_quantity());
		
		//DoomWad doom = new DoomWad(path+"DOOMU.WAD");
		
		//doom.getWad().export_lump_as(11, path+"vertex.lmp");
		
		System.out.println("Done! doomed!");
	}

}
