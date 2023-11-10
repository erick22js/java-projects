package com.test;

public class Prettify {
	public static String prettyStrNumber(String base, int size) {
		for(int i=base.length(); i<size; i++)
			base = "0"+base;
		return base;
	}
}
