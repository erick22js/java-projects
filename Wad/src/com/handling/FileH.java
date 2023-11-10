package com.handling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileH{
	public static void writeFile(String path, String content) {
		try {
			File f = new File(path);
			if (!f.exists())
				f.createNewFile();
			FileWriter w = new FileWriter(f);
			w.write(content);
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void appendFile(String path, String content) {
		try {
			File f = new File(path);
			FileWriter w = new FileWriter(f);
			w.append(readFile(path)+content);
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String readFile(String path) {
		File f = new File(path);
		String txt = "";
		try {
			FileReader rd = new FileReader(f);
			while(true) {
				int c = -1;
				try {
					c = rd.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(c<0)
					break;
				txt+=(char)c;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return txt;
	}
}