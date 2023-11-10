package com.popcap.gaming.Plants;

import com.popcap.gaming.Device;
import com.popcap.gaming.Plants.Behavior.ShootBehavior;
import com.popcap.gaming.Reanim.ReanimData;
import com.popcap.gaming.Reanim.ReanimObject;
import com.popcap.gaming.Resources.DataPlants;
import com.popcap.gaming.Resources.DataReanims;

public class Peashooter extends Plant{
	
	ShootBehavior shoot = new ShootBehavior();
	
	public Peashooter() {
		//super.Plant(DataPlants.Reanim_peashooter);
		slot = 0;
		anims = new ReanimObject[] {new ReanimObject(DataPlants.Reanim_peashooter)};
		shoot.delay = 999999;
		pivotX = -64;
		pivotY = -67;
		//color[0] = color[1] = color[2] = 1.75f;
	}
	@Override
	public void execute() {
		//scaleX+=.01f;
		renderBodies();
	}
}
