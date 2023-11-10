package com.Techne;

import com.Techne.core.runtime.TechneGame;
import com.Techne.renderer.Layout.Button;
import com.Techne.renderer.Layout.Container;
import com.Techne.renderer.Layout.Layout;
import com.Techne.renderer.Layout.Panel;
import com.Techne.renderer.Layout.StyleSkin;
import com.Techne.renderer.Layout.Text;
import com.Techne.renderer.Layout.View;
import com.Techne.renderer.Layout.Frame;
import com.Techne.renderer.Layout.GameInput;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.XmlReader;

public class TechneMGame extends Game{
	
	TechneGame game;
	
	Layout cena;
	StyleSkin skin;
	Panel cont;
	
	GameInput input;
	
	@Override
	public void create() {
		StyleSkin.default_skin.style_atlas = new Texture(Gdx.files.internal("assets/mainui.png"));
		
		game = new TechneGame();
		
		skin = new StyleSkin();
		skin.style_atlas = new Texture(Gdx.files.internal("assets/mainui.png"));
		skin.panel_9chunk_src.set(0, 0, 8f, 8f);
		skin.panel_9chunk_size.set(16f, 16f);
		skin.button_9chunk_src.set(24f, 0, 8f, 8f);
		skin.button_9chunk_size.set(16f, 16f);
		skin.buttonon_9chunk_src.set(48f, 0, 8f, 8f);
		skin.buttonon_9chunk_size.set(16f, 16f);
		
		cena = new Layout(skin) {
			@Override
			protected void onTouchEvent(TouchEvent ev, float x, float y) {
				// TODO Auto-generated method stub
				super.onTouchEvent(ev, x, y);
				//System.out.println(this.vec3.toString());
			}
		};
		input = new GameInput(cena);
		Gdx.input.setInputProcessor(input);
		
		cont = new Panel() {
			@Override
			protected void onTouchEvent(TouchEvent ev, float x, float y){
				// TODO Auto-generated method stub
				super.onTouchEvent(ev, x, y);
			}
		};
		//cont.background_color.set(.2f, .4f, 1f, 1f);
		cont.bound_box.set(0, 0, 1, .5f);
		cena.addChild(cont);
		
		Frame cont2 = new Frame();
		cont2.background_color.set(.3f, 1f, .2f, 1f);
		cont2.bound_box.set(.6f, .1f, .3f, .8f);
		cont.addChild(cont2);
		
		Button btw = new Button();
		btw.bound_box.set(0, 0, .25f, .2f);
		cont.addChild(btw);
		
		Text texto = new Text();
		texto.bound_box.setPosition(.5f, .5f);
		texto.text = "Botao de texto";
		cena.addChild(texto);
		bt = new SpriteBatch();
		fnt = new BitmapFont();
		
	}
	BitmapFont fnt;
	Batch bt;
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		
		game.render();
		
		cena.resolution.set(game.camera.resolution);
		
		/*if(Gdx.input.isTouched(0)) {
			Vector3 t = game.gestureToScreen(0);
			cena.dispatchEventTouch(View.TouchEvent.DOWN, t.x, t.y);
		}*/
		cena.render();
		String a;
		bt.begin();
		fnt.draw(bt, "Texto", 0, 0);
		bt.end();
		
		//Gdx.app.exit();
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
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
