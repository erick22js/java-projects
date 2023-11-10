package com.Techne.renderer.Layout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GameInput implements InputProcessor{
	
	Layout layout;
	
	public GameInput(Layout actLayout) {
		this.layout = actLayout;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		float x = (screenX/((float)Gdx.graphics.getWidth()))*2f-1f;
		float y = (1f-screenY/((float)Gdx.graphics.getHeight()))*2f-1f;
		layout.dispatchEventTouch(View.TouchEvent.DOWN, x, y);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		float x = (screenX/((float)Gdx.graphics.getWidth()))*2f-1f;
		float y = (1f-screenY/((float)Gdx.graphics.getHeight()))*2f-1f;
		layout.dispatchEventTouch(View.TouchEvent.UP, x, y);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		float x = (screenX/((float)Gdx.graphics.getWidth()))*2f-1f;
		float y = (1f-screenY/((float)Gdx.graphics.getHeight()))*2f-1f;
		layout.dispatchEventTouch(View.TouchEvent.MOVE, x, y);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		System.out.println(amountY);
		return false;
	}
}
