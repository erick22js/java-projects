package com.testProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.stream.FileCacheImageInputStream;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;



public class Loader {
	public static String readTextFile(String path){
		String result = "";
		try{
			FileReader rd = new FileReader(new File(path));
			while(true){
				int c = rd.read();
				if(c==-1)
					break;
				result += (char)c;
			}
			rd.close();
		}catch(IOException error){
			//System.out.println("The File in:'"+path+"' could not be loaded, because:\n"+error);
		}
		return result;
	}
}
