package com.handling;

import java.util.logging.FileHandler;

import org.json.JSONArray;
import org.json.JSONObject;

public class DoomWad {
	private Wad wad;
	public DoomWad(String file_wad_path) {
		wad = new Wad(file_wad_path);
		
	}
	
	/*
	 * ********************PALETTE HANDLING*******************************
	 */
	public int[][] get_lump_pallete(int index_lump, int palette) {
		int lumpi = index_lump;//wad.get_index_lump("PLAYPAL", 0);
		if(lumpi<0)
			return new int[0][0];
		byte[] lump = wad.get_lump_bytes(lumpi);
		int[][] pallete = new int[256][3];
		for(int i=0; i<256; i++) {
			// i*3
			pallete[i][0] = lump[palette*256*3+i*3]&0xFF;
			pallete[i][1] = lump[palette*256*3+i*3+1]&0xFF;
			pallete[i][2] = lump[palette*256*3+i*3+2]&0xFF;
		}
		return pallete;
	}
	public void set_lump_pallete(int index_lump, int palette, int[][] data) {
		int lumpi = index_lump;//wad.get_index_lump("PLAYPAL", 0);
		if(lumpi<0)
			return;
		byte[] lump = wad.get_lump_bytes(lumpi);
		for(int i=0; i<256; i++) {
			lump[palette*256*3+i*3] = (byte) data[i][0];
			lump[palette*256*3+i*3+1] = (byte) data[i][1];
			lump[palette*256*3+i*3+2] = (byte) data[i][2];
		}
		wad.set_lump(lumpi, lump);
	}
	/*
	 * ************************THINGS HANDLING*********************************
	 */
	public JSONArray get_lump_things(int index_lump) {
		if(index_lump<0)
			return new JSONArray();
		byte[] lump = wad.get_lump_bytes(index_lump);
		int size = (int)(lump.length*.1);
		JSONArray things = new JSONArray();
		for(int i=0; i<size; i++) {
			JSONObject thing = new JSONObject();
			thing.put("x", 
					IOBytes.UintToInt(IOBytes.littleEndian_BytesToInt(
					IOBytes.extractBytesList(lump, i*10, 2))));
			thing.put("y", 
					IOBytes.UintToInt(IOBytes.littleEndian_BytesToInt(
					IOBytes.extractBytesList(lump, i*10+2, 2))%0xfffff));
			thing.put("angle", IOBytes.littleEndian_BytesToInt(
					IOBytes.extractBytesList(lump, i*10+4, 2)));
			thing.put("type", IOBytes.littleEndian_BytesToInt(
					IOBytes.extractBytesList(lump, i*10+6, 2)));
			thing.put("flag", IOBytes.littleEndian_BytesToInt(
					IOBytes.extractBytesList(lump, i*10+8, 2)));
			things.put(thing);
		}
		return things;
	}
	public void set_lump_things(int index_lump, JSONArray things) {
		if(index_lump<0)
			return ;
		int size = things.length();
		byte[] lump = new byte[size*10];
		for(int i=0; i<size; i++) {
			IOBytes.putBytes(IOBytes.littleEndian_IntToBytes(
					IOBytes.IntToUint(things.getJSONObject(i).getInt("x")), 2
					), lump, i*10);
			IOBytes.putBytes(IOBytes.littleEndian_IntToBytes(
					IOBytes.IntToUint(things.getJSONObject(i).getInt("y")), 2
					), lump, i*10+2);
			IOBytes.putBytes(IOBytes.littleEndian_IntToBytes(
					things.getJSONObject(i).getInt("angle"), 2
					), lump, i*10+4);
			IOBytes.putBytes(IOBytes.littleEndian_IntToBytes(
					things.getJSONObject(i).getInt("type"), 2
					), lump, i*10+6);
			IOBytes.putBytes(IOBytes.littleEndian_IntToBytes(
					things.getJSONObject(i).getInt("flag"), 2
					), lump, i*10+8);
		}
		wad.set_lump(index_lump, lump);
	}
	
	public Wad getWad() {
		return wad;
	}
	public void saveWadFile(String path) {
		wad.compile_wad(path);
	}
	
	/*********************DATA EXPORTING****************************/
	public void flatLumpToPng(int index_lump, int index_palette, String image_path) {
		byte[] flat_bytes = wad.get_lump_bytes(index_lump);
		int[][] palette = get_lump_pallete(0, index_palette);
		byte[] imageData = new byte[12288];
		
		for(int p=0; p<flat_bytes.length;p++) {
			int i = p*3;
			int pixel = (int)(flat_bytes[p]&0xff);
			imageData[i] = (byte) palette[pixel][0];
			imageData[i+1] = (byte) palette[pixel][1];
			imageData[i+2] = (byte) palette[pixel][2];
		}
		
		IOBytes.byteToImageNoAlpha(image_path, imageData, 64, 64);
	}
	//**********test area
	public void spriteDefinitionExport(int index_lump, int index_palette, String image_path) {
		byte[] sprite_bytes = wad.get_lump_bytes(index_lump);
		int[][] palette = get_lump_pallete(0, index_palette);
		
		//Fist, retrieve sprite data
		int pointer = 0;
		int width = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(sprite_bytes, 0, 2));
		int height = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(sprite_bytes, 2, 2));
		pointer = 8;
		byte[][] dataCollums = new byte[width][];
		for(int c=0; c<width; c++) {
			int begin = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(sprite_bytes, pointer, 4));
			int avoid = c==width-1?sprite_bytes.length:IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(sprite_bytes, pointer+4, 4));
			dataCollums[c] = IOBytes.extractBytesList(sprite_bytes, begin, avoid-begin);
			pointer+=4;
		}
		
		FileH.writeFile(image_path, (new JSONArray().putAll(dataCollums)).toString(4));
		
		byte[] imageData = new byte[width*height*4];
		for(int r=0; r<width; r++) {
			byte[] dataC = dataCollums[r];
			int pointerB = 0;
			int actualPixel = 0;
			//System.out.println(r);
			while(true) {
				//System.err.println(actualPixel);
				int start = dataC[pointerB]&0xff;
				for(; actualPixel<start;) {
					int index = actualPixel*width*4+r*4;
					imageData[index] = (byte)0;
					imageData[index+1] = (byte)0;
					imageData[index+2] = (byte)0;
					imageData[index+3] = (byte)0;
					actualPixel++;
				}
				//System.err.println(actualPixel);
				pointerB++;
				int length = dataC[pointerB]&0xff;
				pointerB+=2;
				for(int i=0; i<length; i++) {
					int index = actualPixel*width*4+r*4;
					int[] color = palette[dataC[pointerB]&0xff];
					imageData[index] = (byte)color[0];
					imageData[index+1] = (byte)color[1];
					imageData[index+2] = (byte)color[2];
					imageData[index+3] = (byte)255;
					actualPixel++;
					pointerB++;
				}
				//System.err.println(actualPixel);
				pointerB++;
				if(dataC[pointerB]==-1) {
					break;
				}
			}
			/*for(int b=0; b<dataCollums[r].length; b++) {
				
			}*/
		}
		IOBytes.byteToImageWithAlpha(image_path, imageData, width, height);
		//FileH.writeFile(image_path, rowsData.toString(4));
	}
}
