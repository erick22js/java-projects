package com.popcap.gaming.Levels;

public class LevelModule {
	public GameModules.Locally.LocallyModule locally;
	//public GameModules.Weather weather;
	public boolean[] playableRows;
	public LevelModule(String locally, String weather) {
		switch(locally) {
			case "frontDay":
				this.locally = GameModules.Locally.FrontDay;
				break;
			default:
				this.locally = GameModules.Locally.FrontDay;
		}
		playableRows = new boolean[] {true, true, true, true, true, true};
	}
}
