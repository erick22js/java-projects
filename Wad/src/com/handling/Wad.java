package com.handling;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Wad {
	
	//private
	private String format;
	//private 
	private byte[] data;
	//private 
	private int lumps_quantity;
	
	private int address_definitions;
	
	private ByteBuffer[] byte_lumps;
	private String[] name_lumps;
	
	public Wad(String path) {
		
		/***************************HEADER_DEFINITION***********************/
		//Pre-load file wad bytes
		data = IOBytes.bytesFromFile(path);
		//Read and get his extension format
		format = IOBytes.bytesToString(IOBytes.extractBytesList(data, 0, 4));
		//Retrive lumps quantity
		lumps_quantity = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(data, 4, 4));
		//Retrieve address lumps definitions
		address_definitions = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(data, 8, 4));
		//Loading lumps bytes
		load_lumps_definition();
		
	}
	
	public Wad(byte[] data) {
		this.data = data;
		//Read and get his extension format
		format = IOBytes.bytesToString(IOBytes.extractBytesList(data, 0, 4));
		//Retrive lumps quantity
		lumps_quantity = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(data, 4, 4));
		//Retrieve address lumps definitions
		address_definitions = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(data, 8, 4));
		//Loading lumps bytes
		load_lumps_definition();
	}
	
	public Wad(File file) {
		data = IOBytes.bytesFromFile(file);
		//Read and get his extension format
		format = IOBytes.bytesToString(IOBytes.extractBytesList(data, 0, 4));
		//Retrive lumps quantity
		lumps_quantity = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(data, 4, 4));
		//Retrieve address lumps definitions
		address_definitions = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(data, 8, 4));
		//Loading lumps bytes
		load_lumps_definition();
	}
	
	public Wad() {
		//Pre-load file wad bytes
		data = new byte[0];
		//Read and get his extension format
		format = "IWAD";
		//Retrive lumps quantity
		lumps_quantity = 0;
		//Retrieve address lumps definitions
		address_definitions = 12;
		//Loading lumps bytes
		load_lumps_definition();
	}
	
	private void load_lumps_definition() {
		byte_lumps = new ByteBuffer[lumps_quantity];
		name_lumps = new String[lumps_quantity];
		for(int i=0; i<lumps_quantity; i++) {
			//Object[] lump = new Object[2];
			int pointer = address_definitions + i*16;
			int address = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(data, pointer, 4));
			int size = IOBytes.littleEndian_BytesToInt(IOBytes.extractBytesList(data, pointer+4, 4));
			String name = IOBytes.bytesToString(IOBytes.extractBytesList(data, pointer+8, 8));
			name = String.valueOf(name.replaceAll(""+((char)0), ""));
			//lump[0] = name;
			//lump[1] = IOBytes.extractBytesList(data, address, size);
			////System.out.println(size);
			////System.out.println("getting index "+i);
			if(size>0)
				byte_lumps[i] = ByteBuffer.wrap(IOBytes.extractBytesList(data, address, size));
			else
				byte_lumps[i] = ByteBuffer.wrap(new byte[0]);
			name_lumps[i] = name;
		}
	}
	
	public String get_wad_header() {
		return format;
	}
	
	public void set_wad_header(String header) {
		String h = header;
		if(h.length()>4)
			h = h.substring(0, 3);
		else
			if (h.length()==0)
				h = "IWAD";
			else if (h.length()==1)
				h += "   ";
			else if (h.length()==2)
				h += "  ";
			else if (h.length()==3)
				h += " ";
		format = h;
	}
	
	public int get_index_lump(String name_lump, int start) {
		for(int i=start; i<name_lumps.length; i++) {
			if((name_lumps[i]).toUpperCase().matches(name_lump.toUpperCase())) {
				return i;
			}
		}
		System.err.println("No lump matches name'"+name_lump+"' started at index "+start+" can be found!");
		return -1;
	}
	
	public String get_name_lump(int index) {
		return name_lumps[index];
	}
	
	public int lumps_quantity() {
		return lumps_quantity;
	}
	
	public byte[] get_lump_bytes(int index) {
		return byte_lumps[index].array();
	}
	
	public void set_lump(int index, byte[] bytes) {
		byte_lumps[index] = ByteBuffer.wrap(bytes);
	}
	public void insert_lump(int index, String name, byte[] bytes) {
		lumps_quantity++;
		ByteBuffer[] nbytes_lumps = new ByteBuffer[lumps_quantity];
		String[] nnames_lumps = new String[lumps_quantity];
		index = index>=lumps_quantity?lumps_quantity-1:index;
		
		name = name.length()>8?name.substring(0, 7):name;
		
		int added = 0;
		//System.out.println(lumps_quantity);
		for(int i=0; i<lumps_quantity; i++) {
			if(i!= index) {
				nbytes_lumps[i] = byte_lumps[i-added];
				nnames_lumps[i] = name_lumps[i-added];
			}else {
				added+=1;
				nbytes_lumps[i] = ByteBuffer.wrap(bytes);
				nnames_lumps[i] = name;
			}
		}
		byte_lumps = nbytes_lumps;
		name_lumps = nnames_lumps;
	}
	public void remove_lump(int index) {
		lumps_quantity--;
		ByteBuffer[] nbytes_lumps = new ByteBuffer[lumps_quantity];
		String[] nnames_lumps = new String[lumps_quantity];
		index = index>=lumps_quantity?lumps_quantity-1:index;
		int removed = 0;
		//System.out.println(lumps_quantity);
		for(int i=0; i<lumps_quantity+1; i++) {
			if(i!= index) {
				nbytes_lumps[i-removed] = byte_lumps[i];
				nnames_lumps[i-removed] = name_lumps[i];
			}else {
				removed+=1;
			}
		}
		byte_lumps = nbytes_lumps;
		name_lumps = nnames_lumps;
	}
	public void export_lump_as(int index, String file_out) {
		IOBytes.bytesToFile(byte_lumps[index].array(), file_out);
	}
	
	public int get_wad_lumps_size() {
		int data_size = 0;
		for(int i=0; i<lumps_quantity; i++)
			data_size += byte_lumps[i].array().length;
		return data_size;
	}
	public int get_wad_total_size() {
		//First, get all bytes data size
		int data_size = 0;
		data_size += get_wad_lumps_size();
		//Add pointers references lumps sizes
		data_size += lumps_quantity*16;
		//Add header file
		data_size += 12;
		return data_size;
	}
	public void compile_wad(String outFilePath) {
		//Generate all map bytes
		int size = get_wad_total_size();
		byte[] bytes = new byte[size];
		//Define header wad
		IOBytes.putBytes(IOBytes.stringAsByte(format), bytes, 0);
		IOBytes.putBytes(IOBytes.littleEndian_IntToBytes(lumps_quantity, 4), bytes, 4);
		int references_start = get_wad_lumps_size()+12;
		IOBytes.putBytes(IOBytes.littleEndian_IntToBytes(references_start, 4), bytes, 8);
		
		int pointer_ref = 12;
		
		for(int i=0; i<lumps_quantity; i++) {
			byte[] pointer = new byte[16];
			IOBytes.putBytes(IOBytes.littleEndian_IntToBytes(pointer_ref, 4), pointer, 0);
			IOBytes.putBytes(IOBytes.littleEndian_IntToBytes(byte_lumps[i].array().length, 4), pointer, 4);
			IOBytes.putBytes(IOBytes.stringAsByte(name_lumps[i]), pointer, 8);
			
			IOBytes.putBytes(byte_lumps[i].array(), bytes, pointer_ref);
			IOBytes.putBytes(pointer, bytes, references_start+i*16);
			pointer_ref += byte_lumps[i].array().length;
		}
		
		IOBytes.bytesToFile(bytes, outFilePath);
	}
}
