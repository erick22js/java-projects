package com.popcap.gaming.Resources;

import com.badlogic.gdx.graphics.Texture;
import com.popcap.gaming.Reanim.ReanimControl;
import com.popcap.gaming.Reanim.ReanimData;

public class DataPlants {
	//**************PEASHOOTER******************
	public static ReanimData Reanim_peashooter = ReanimData.uncodeAnim(
			Resources.loadAsset("compiled/reanim/PeaShooterSingle.reanim.compiled").readString(),
			DataReanims.peashooter_main, 
			new ReanimControl(
			new Object[] {
				"idle", 25, 49,
				"shoot", 0, 25,
			}
	));
}
