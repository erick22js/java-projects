package com.ICompany.ReinEmp.game;

import com.ICompany.ReinEmp.game.core.MainGameManager;
import com.ICompany.ReinEmp.game.core.MainInputManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class MainReinemp extends Game{
	
	MainGameManager mGame;
	
	@Override
	public void create() {
		mGame = new MainGameManager();
	}
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		
		
		if(Gdx.input.isKeyPressed(Keys.Q))
			mGame.camera.zoom *= 1.0625;
		if(Gdx.input.isKeyPressed(Keys.E))
			mGame.camera.zoom /= 1.0625;
		if(Gdx.input.isKeyPressed(Keys.D))
			mGame.camera.translate(.1f*mGame.camera.zoom, 0);
		if(Gdx.input.isKeyPressed(Keys.A))
			mGame.camera.translate(-.1f*mGame.camera.zoom, 0);
		if(Gdx.input.isKeyPressed(Keys.W))
			mGame.camera.translate(0, .1f*mGame.camera.zoom);
		if(Gdx.input.isKeyPressed(Keys.S))
			mGame.camera.translate(0,-.1f*mGame.camera.zoom);
		mGame.camera.update();
		//System.out.println(mGame.screenToBlocks(Gdx.input.getX(0), Gdx.input.getY(0)).toString());
		
		mGame.execute();
		
	}
	@Override
	public void pause() {
		super.pause();
	}
	@Override
	public void resume() {
		super.resume();
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
