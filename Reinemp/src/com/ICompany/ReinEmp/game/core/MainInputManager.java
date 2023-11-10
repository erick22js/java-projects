package com.ICompany.ReinEmp.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class MainInputManager{
	public static float getScreenX(int ind) {
		return Gdx.input.getX(ind)/((float)Gdx.graphics.getWidth());
	}
	public static float getScreenY(int ind) {
		return Gdx.input.getY(ind)/((float)Gdx.graphics.getHeight());
	}
}
