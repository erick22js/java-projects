package com.popcap.gaming.Plants.Behavior;

public class ShootBehavior {
	public float delay = 999999f;
	public boolean canShoots(float deltaTime) {
		return delay < 0;
	}
	public void reset(float delay) {
		this.delay = delay;
	}
}
