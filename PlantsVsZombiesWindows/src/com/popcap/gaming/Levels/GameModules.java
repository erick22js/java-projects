package com.popcap.gaming.Levels;

import com.badlogic.gdx.graphics.Texture;
import com.popcap.gaming.Resources.DataImages;

class GameModules{
	public static class Locally{
		public static class LocallyModule{
			public Texture background;
			public Texture overlay;
			public Texture mask;
			public boolean sunFalls;
			public int rows;
			public int[] collumsAltitude;
			public LocallyModule(Texture background, Texture overlay, Texture mask, boolean sunFalls, int rows, int[] collumsAltitude) {
				this.background = background;
				this.overlay = overlay;
				this.mask = mask;
				this.sunFalls = sunFalls;
				this.rows = rows;
				this.collumsAltitude = collumsAltitude;
			}
		}
		public static LocallyModule FrontDay = new LocallyModule(
				DataImages.backgroundFrontDay, 
				DataImages.backgroundFrontDayGameoverOverlay, 
				DataImages.backgroundFrontDayGameoverMask, 
				true, 
				5, 
				new int[] {0,0,0,0,0,0,0,0,0});
	}
	public static class Weather{
		public static int CLEAR = 210;
		public static int FOGGY = 211;
		public static int RAINY = 212;
		public static int STORMY = 213;
	}
}

