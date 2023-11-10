package Asserty.compiler;
import java.io.*;
import java.nio.file.*;
import java.nio.file.Paths;

public class FileDeal
{
	public static String readFileString(String path){
		File file = new File(path);
		if(!file.exists()){
			System.err.println("File at: "+path+", not found");
			return "";
		}
		try{
			FileReader r = new FileReader(file);
			String str = "";
			int ccode = 0;
			while((ccode = r.read())>-1){
				str += String.valueOf((char) ccode);
			}
			r.close();
			return str;
		}catch(IOException erro){
			
		}
		return "";
	}
	public static void writeBytesToFile(String path, byte[] bytes){
		File file = new File(path);
		
		try{
			FileOutputStream fo = new FileOutputStream(file);
			fo.write(bytes);
			fo.close();
		}catch(Exception e){
			
		}
		//Path p = Paths.get(path);
		
		/*try
		{
			//Files.write(p, bytes, null);
		}
		catch (IOException e)
		{}*/
	}
	public static byte[] readBytesFromFile(String path){
		File file = new File(path);
		
		try
		{
			FileInputStream fi = new FileInputStream(file);
			int b = 0;
			byte[] bytes = new byte[(int)file.length()];
			int i=0;
			while((b = fi.read())>-1){
				bytes[i] = (byte)(b&0xff);
				i++;
			}
			return bytes;
		}
		catch (Exception e)
		{}

		return new byte[]{};
	}
}
