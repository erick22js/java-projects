package com.Disa64;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IOHanddler {
	public static boolean writeToFile(String path, String text) throws IOException{
		try{
			File outfile = new File(path);
			boolean exists = outfile.exists();
			new File(outfile.getParent()).mkdirs();
			if(!exists) {
				outfile.createNewFile();
			}
			FileWriter w = new FileWriter(outfile);
			w.write(text);
			w.close();
			return true;
		}
		catch (IOException e){
			System.err.println("Can't write to file in path:'"+path+"'.");
			throw e;
		}
	}
}
