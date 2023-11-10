package com.circuitsimulator.woltex.core;

import java.io.File;
import java.io.FileReader;

public class WoltexIO {
	public static String readTextFile(String path) {
		File file = new File(path);
		String out = "";
		try {
			FileReader reader = new FileReader(file);
			int cha = 0;
			while((cha = reader.read())!=-1) {
				out += (char)(cha&0xffff);
			}
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return out;
	}
}
