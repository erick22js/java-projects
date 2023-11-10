package com.idcorporation.redoom64.utils;

import com.handling.IOBytes;

public class numberUtil {
	public static float signedFloat16(byte[] bytes) {
		float value = 0;
		value += IOBytes.littleEndian_BytesToInt(new byte[] {bytes[2],bytes[3]});
		value = IOBytes.UintToInt((int) value);
		value += IOBytes.littleEndian_BytesToInt(new byte[] {bytes[0],bytes[1]})/65536f * (value<0?1:-1);
		return value;
	}
}
