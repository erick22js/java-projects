package com.handling;

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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class IOBytes {
	
	public static byte[] bytesFromFile(String path) {
		try {
			File file = new File(path);
			Path pat = Paths.get(path);
			byte[] bytes = Files.readAllBytes(pat);
			return bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error on loading file.");
			e.printStackTrace();
		}
		return new byte[0];
	}
	public static byte[] bytesFromFile(File file) {
		try {
			Path pat = Paths.get(file.getPath());
			byte[] bytes = Files.readAllBytes(pat);
			return bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error on loading file.");
			e.printStackTrace();
		}
		return new byte[0];
	}
	public static void bytesToFile(byte[] data, String path) {
		try {
			File file = new File(path);
			if(!file.exists())
				file.createNewFile();
			Path pat = Paths.get(path);
			
			Files.write(pat, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error on writting file.");
			e.printStackTrace();
		}
		return ;
	}
	public static InputStream bytesAsInputStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}
	public static void byteToImageNoAlpha(String path, byte[] pixelsrgb, int width, int height) {
		DataBufferByte buffer = new DataBufferByte(pixelsrgb, pixelsrgb.length);
		WritableRaster raster = Raster.createInterleavedRaster(buffer, width, height, 3*width, 3, new int[] {0,1,2}, (Point)null);
		ColorModel cm = new ComponentColorModel(ColorModel.getRGBdefault().getColorSpace(), false, true, Transparency.OPAQUE, java.awt.image.DataBuffer.TYPE_BYTE);
		BufferedImage image = new BufferedImage(cm, raster, true, null);
		
		try {
			ImageIO.write(image, "png", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Cant generate image because: "+e);
			e.printStackTrace();
		}
	}
	public static void byteToImageWithAlpha(String path, byte[] pixelsrgb, int width, int height) {
		DataBufferByte buffer = new DataBufferByte(pixelsrgb, pixelsrgb.length);
		WritableRaster raster = Raster.createInterleavedRaster(buffer, width, height, 4*width, 4, new int[] {0,1,2,3}, (Point)null);
		ColorModel cm = new ComponentColorModel(ColorModel.getRGBdefault().getColorSpace(), true, true, Transparency.TRANSLUCENT, java.awt.image.DataBuffer.TYPE_BYTE);
		BufferedImage image = new BufferedImage(cm, raster, true, null);
		
		try {
			ImageIO.write(image, "png", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Cant generate image because: "+e);
			e.printStackTrace();
		}
	}
	public static byte[] extractBytesList(byte[] bytes, int begin, int length) {
		byte[] list = new byte[length];
		for(int i=0; i<length && i+begin<bytes.length; i++) {
			list[i] = bytes[i+begin];
		}
		return list;
	}
	public static String bytesToString(byte[] bytes) {
		String text = "";
		for(int i=0; i<bytes.length; i++) {
			text += (char)bytes[i];
		}
		return text;
	}
	public static int littleEndian_BytesToInt(byte[] bytes) {
		int value = 0;
		for(int i=0; i<bytes.length; i++) {
			value += Math.pow(256, i)*(bytes[i]&0xff);
		}
		return value;
	}
	public static byte[] littleEndian_IntToBytes(int value, int bytes_quantity) {
		byte[] bytes = new byte[bytes_quantity];
		int rest = value;
		int p = 0;
		while(p<bytes_quantity) {
			int mod = (int)(value % Math.pow(256, p+1));
			rest -= mod;
			bytes[p] = (byte)(mod/Math.pow(256, p));
			p++;
		}
		return bytes;
	}
	public static int UintToInt(int value) {
		return value>0x7fff?value-0xffff-1:value;
	}
	public static int IntToUint(int value) {
		return value<0?0xffff+value+1:value;
	}
	public static byte[] stringAsByte(String text) {
		byte[] bytes = new byte[text.length()];
		for(int i=0; i<bytes.length; i++)
			bytes[i] = (byte)text.charAt(i);
		return bytes;
	}
	public static void putBytes(byte[] src, byte[] destination, int begin) {
		for(int i=0; i<src.length && (i+begin)<destination.length; i++)
			destination[i+begin] = src[i];
	}
	//**********CONVERSION***************
	public static int[] bytesAsInts(byte[] bytes) {
		int[] ints = new int[bytes.length];
		for(int i=0; i<ints.length; i++)
			ints[i] = (int)(bytes[i]&0xff);
		return ints;
	}
	public static byte[] intsAsBytes(int[] ints) {
		byte[] bytes = new byte[ints.length];
		for(int i=0; i<bytes.length; i++)
			bytes[i] = (byte)ints[i];
		return bytes;
	}
	
	//**********BITS HANDLING************//
	public static int[] byteToBits(byte byt) {
		int[] bits = new int[8];
		int by = (int)(byt&0xff);
		for(int e=0; e<8; e++) {
			int lem = by%2;
			by-=lem;
			by/=2;
			bits[e] = lem;
		}
		
		return bits;
	}
	public static byte bitsToByte(int[] bits) {
		int byt = 0;
		for(int e=7; e>-1; e--) {
			byt *= 2;
			byt += bits[e];
		}
		return (byte)byt;
	}
}
