package com.ICompany.ReinEmp.game.core;

public class ItemsStorage {
	int[] items;
	public ItemsStorage(int size) {
		items = new int[size];
	}
	public int getItem(int index) {
		return items[index];
	}
	public int getItemId(int index) {
		return items[index]>>16;
	}
	public int getItemCount(int index) {
		return items[index]&0xffff;
	}
	/*public int stackItem(int id, int count) {
		for(int i=0; i<items.length && count>0; i++)
			if(getItemId(i)==id)
				return 0;
		return 0;
	}*/
}
