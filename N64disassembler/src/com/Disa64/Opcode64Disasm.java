package com.Disa64;

import java.io.RandomAccessFile;

import com.test.Prettify;

/*
 * Classe para análise de bytes, opcodes e retorno de desmontagem
 */

public class Opcode64Disasm {
	private static int bytesRFetchAcu = 0;
	private static long bytesAcu = 0;
	public static int lastFetch = 0;
	
	public static String fetchAsmOpc(RandomAccessFile ac, boolean littleEndian) throws Exception{
		String ad = Integer.toHexString((int)ac.getFilePointer());
		lastFetch = Opcode64Disasm.fetchInt(ac, littleEndian);
		Opcode64 opc = Opcode64.fetchOpcode(lastFetch, littleEndian);
		int ins = lastFetch;
		//System.out.println(ac.getFilePointer()+" => "+Integer.toHexString(lastFetch)+" : "+Integer.toBinaryString(lastFetch));
		if(opc==null) {
			String hexed = Integer.toHexString(ins);
			for(int i=hexed.length(); i<8; i++)
				hexed = ("0").concat(hexed);
			String bined = Integer.toBinaryString(ins);
			for(int i=bined.length(); i<32; i++)
				bined = ("0").concat(bined);
			return "0b"+bined+" -> [0x"+hexed+"]\n";
		}
		//System.out.println("Fetched opcode: "+opc.templateInd.opcode);
		//System.out.println();
		return Prettify.prettyStrNumber(ad, 8)+" => "+opc.toString()+" \n";
	}
	/*
	 * Funções de fetch para dados de arquivos com tamanho em bits especificado,
	 * size deve obrigatoriamente estar entre 0 e 32 bits, pra evitar bugs inesperados
	 */
	public static int fetchBitsInt(RandomAccessFile ac, int size, boolean littleEndian)throws Exception{
		long n = 0;
		if(bytesRFetchAcu==0||bytesRFetchAcu<size) {
			bytesRFetchAcu += 32;
			int fetch = fetchInt(ac, littleEndian);
			bytesAcu <<= 32;
			bytesAcu += fetch;
		}
		bytesRFetchAcu -= size;
		n = bytesAcu>>bytesRFetchAcu;
		n <<= bytesRFetchAcu;
		bytesAcu &= ~n;
		n >>= bytesRFetchAcu;
		return (int)(n&0xffffffff);
	}
	public static short fetchBitsShort(RandomAccessFile ac, int size, boolean littleEndian)throws Exception{
		long n = 0;
		if(bytesRFetchAcu==0||bytesRFetchAcu<size) {
			bytesRFetchAcu += 16;
			int fetch = fetchShort(ac, littleEndian);
			bytesAcu <<= 16;
			bytesAcu += fetch;
		}
		bytesRFetchAcu -= size;
		n = bytesAcu>>bytesRFetchAcu;
		n <<= bytesRFetchAcu;
		bytesAcu &= ~n;
		n >>= bytesRFetchAcu;
		return (short)(n&0xffff);
	}
	public static byte fetchBitsByte(RandomAccessFile ac, int size, boolean littleEndian)throws Exception{
		long n = 0;
		if(bytesRFetchAcu==0||bytesRFetchAcu<size) {
			bytesRFetchAcu += 8;
			int fetch = fetchByte(ac, littleEndian);
			bytesAcu <<= 8;
			bytesAcu += fetch;
		}
		bytesRFetchAcu -= size;
		n = bytesAcu>>bytesRFetchAcu;
		n <<= bytesRFetchAcu;
		bytesAcu &= ~n;
		n >>= bytesRFetchAcu;
		return (byte)(n&0xff);
	}
	/*
	 * Fetchs padr�es
	 */
	public static float fetchFloat(RandomAccessFile ac, boolean littleEndian)throws Exception{
		return ac.readFloat();
	}
	public static int fetchInt(RandomAccessFile ac, boolean littleEndian)throws Exception{
		return ac.readInt();
	}
	public static short fetchShort(RandomAccessFile ac, boolean littleEndian)throws Exception{
		return ac.readShort();
	}
	public static byte fetchByte(RandomAccessFile ac, boolean littleEndian)throws Exception{
		return ac.readByte();
	}
}
