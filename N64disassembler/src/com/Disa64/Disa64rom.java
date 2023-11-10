package com.Disa64;

import java.io.*;

public class Disa64rom{
	RandomAccessFile ac;
	File acf;
	String[] validFormats = {"n64", "v64", "z64"};
	
	//Rom header
	short mode;
	byte comp;
	byte check1;
	int check2;
	int entryP;
	short check3;
	byte check4;
	byte unknowByte;
	int CRC1;
	int CRC2;
	long check5;
	String id = "";
	int check6;
	short check7;
	byte check8;
	byte manufactureId;
	short idCode;
	byte countryC;
	byte check9;
	
	//Rom info
	boolean byteSwap;
	boolean compreessed;
	String romRegion;
	
	public Disa64rom(File file){
		try{
			acf = file;
			if(!file.exists()){
				System.err.println("This file doesn't exists");
			}
			if(!file.isFile()){
				System.err.println("This ins't a file path");
			}
			ac = new RandomAccessFile(file, "r");
			checkFormat();
			checkHeader();
		}catch(Exception e){
			System.err.println("Fails on get n64 rom");
			//e.printStackTrace();
		}
	}
	
	//Private checks rom
	public void checkFormat()throws Exception{
		String name = acf.getName();
		System.out.println("Checking format from '"+name+"'...");
		String ext = name.substring(name.lastIndexOf(".")+1, name.length());
		System.out.println("Format name detected: "+ext);
		boolean compatible = false;
		for(String t:validFormats){
			if(t.contentEquals(ext)){
				System.out.println("Valid format name!");
				compatible = true;
			}
		}
		if(!compatible){
			System.err.println("Invalid extension for a n64 rom");
			throw new Exception("Invalid extension for a n64 rom");
		}
	}
	public void checkHeader()throws Exception{
		System.out.println("Checking rom header...");
		String invalid = "Invalid rom";
		//Fetch values
		mode = ac.readShort();
		comp = ac.readByte();
		check1 = ac.readByte();
		check2 = ac.readInt();
		entryP = ac.readInt();
		check3 = ac.readShort();
		check4 = ac.readByte();
		unknowByte = ac.readByte();
		CRC1 = ac.readInt();
		CRC2 = ac.readInt();
		check5 = ac.readLong();
		id = "";
		for(int i=0; i<20; i++){
			id += (char)ac.readByte();
		}
		check6 = ac.readInt();
		check7 = ac.readShort();
		check8 = ac.readByte();
		manufactureId = ac.readByte();
		idCode = ac.readShort();
		countryC = ac.readByte();
		check9 = ac.readByte();
		
		if(mode==((short)0x8037)){
			byteSwap = false;
		}else if(mode==((short)0x3780)){
			byteSwap = true;
		}else{
			System.out.println("Invalid byte swap: "+Integer.toHexString(mode));
			System.err.println(invalid);
			throw new Exception(invalid);
		}
		if(comp==0x12){
			compreessed = false;
		}else if(comp==0x13){
			compreessed = true;
		}else{
			System.out.println("Invalid compress: "+Integer.toHexString(comp));
			System.err.println(invalid);
			throw new Exception(invalid);
		}
		/*if(check1!=0x40){
			System.err.println(invalid);
			throw new Exception(invalid);
		}
		if(check2!=0x0){
			System.err.println(invalid);
			throw new Exception(invalid);
		}
		if(check3!=0x0){
			System.err.println(invalid);
			throw new Exception(invalid);
		}
		if(check4!=0x14){
			System.err.println(invalid);
			throw new Exception(invalid);
		}
		if(check5!=0x0){
			System.err.println(invalid);
			throw new Exception(invalid);
		 }
		 if(check6!=0x0){
		 	System.err.println(invalid);
		 	throw new Exception(invalid);
		 }
		 if(check7!=0x0){
		 	System.err.println(invalid);
		 	throw new Exception(invalid);
		 }
		 if(check8!=0x0){
			System.err.println(invalid);
		 	throw new Exception(invalid);
		 }
		 if(check9!=0x0){
		 	System.err.println(invalid);
		 	throw new Exception(invalid);
		 }
		*/
		switch(countryC){
			case 'B':
				romRegion = "Brazilian";
				break;
			case 'D':
				romRegion = "Germany";
				break;
			case 'E':
				romRegion = "USA";
				break;
			case 'J':
				romRegion = "Japan";
				break;
			case 'P':
				romRegion = "Europe";
				break;
			case 'U':
				romRegion = "Australia";
				break;
			default:
				System.err.println("Invalid country code: "+((char)countryC));
				System.err.println(invalid);
				throw new Exception(invalid);
		}
		System.out.println("<------------>");
		System.out.println("Rom identified: "+id);
		System.out.println("Rom id identified: 0x"+Integer.toHexString(idCode));
		System.out.println("Rom region identified: "+romRegion);
		System.out.println("<------------>");
	}
	public void disassemblesToFile(String base, int chunkSize, int limitChunks){
		try{
			//Output info files
			IOHanddler.writeToFile(base+"header.txt", 
					"[ROM INFO]"+"\n"+
					"\n"+
					"byte_swap = "+byteSwap+"\n"+
					"compreessed = "+compreessed+"\n"+
					"game_address = 0x"+Integer.toHexString(entryP)+"\n"+
					"crc1 = 0x"+Integer.toHexString(CRC1)+"\n"+
					"crc2 = 0x"+Integer.toHexString(CRC2)+"\n"+
					"image_name = "+id+"\n"+
					"manufaturer = "+manufactureId+"\n"+
					"id = "+Integer.toHexString(idCode)+"\n"+
					"region = "+romRegion+"\n"
					);
			//Disassembles the boot code
			{
				String code = "";
				for(;ac.getFilePointer()<0x1000;) {
					code = code.concat(Opcode64Disasm.fetchAsmOpc(ac, false));
				}
				IOHanddler.writeToFile(base+"asm/bootcode.asm", code);
			}
			//Disassembles begin preview code
			/*{
				String code = "";
				for(;ac.getFilePointer()<0x1500;) {
					code = code.concat(Opcode64Disasm.fetchAsmOpc(ac, false));
				}
				IOHanddler.writeToFile(base+"asm/bpreview.asm", code);
			}*/
			{
				String code = "";
				ac.seek(0xD_96_20);
				for(;ac.getFilePointer()<0xD_97_20;) {
					code = code.concat(Opcode64Disasm.fetchAsmOpc(ac, false));
				}
				IOHanddler.writeToFile(base+"asm/rspcode.asm", code);
			}
			//Output disassemble files
			/*for(int c=0; c<limitChunks; c++){
				System.out.println("\nGenerating chunk: "+(c+1)+"/"+limitChunks+"\n");
				String out = disassembles(chunkSize, c);
				IOHanddler.writeToFile(base+"asm/asmchunk"+c+".asm", out);
			}*/
			System.out.println("Disassembled!");
		}
		catch (Exception e){
			System.out.println("Error on outputing disassembly");
		}
	}/*
	private String disassembles(int chunkSize, int chunki){
		String out = "";
		//float jumpProgress = 2;
		//float progress = -jumpProgress;
		long p;
		float waitSecs = .25f;
		long nowTime = System.nanoTime();
		long oldTime = nowTime;
		try{
			while((p=ac.getFilePointer())<acf.getTotalSpace()&&(p-chunkSize*chunki)<chunkSize){
				out = out.concat(Opcode64Disasm.fetchAsmOpc(ac, false));
				float p2 = ((p-chunkSize*chunki)/((float)chunkSize))*100;
				nowTime = System.nanoTime();
				if(nowTime>(oldTime+(waitSecs*1000000000))){
					//progress = p2;
					oldTime = nowTime;
					System.out.println(p2+"% completed");
				}
			}
		}catch(Exception e){
			System.out.println("Error on disassembling");
		}
		return out;
	}*/
}
