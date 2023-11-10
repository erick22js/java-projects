package com.test;
import java.util.*;

import com.Disa64.Disa64rom;
import com.Disa64.Opcode64;

import java.io.*;

public class Main{
	public static void main(String[] args){
		String path;
		String basePath = "prots/";//"/sdcard/AppProjects/N64disassembler/prots/";
		/*
		System.out.println("Insert the path to rom: ");
		Scanner sc = new Scanner(System.in);
		path = sc.nextLine();
		System.out.flush();*/
		
		path = basePath+"dkr.z64";
		String patho = basePath+"dkrasm";
		
		System.out.println("Getting rom file from path '"+path+"'...");
		
		
		Disa64rom rom = new Disa64rom(new File(path));
		//File out = new File(patho);
		try{
		//out.createNewFile();
		}catch(Exception e){}
		rom.disassemblesToFile(basePath, 0x100, 0x8);
		//System.out.println(Opcode64.fetchOpcode(0x1074020, false).toString());
		//System.out.println(Opcode64.fetchOpcode(0x0D000410, false).toString());
	}
}
