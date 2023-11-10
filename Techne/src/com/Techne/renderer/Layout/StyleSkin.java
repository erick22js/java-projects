package com.Techne.renderer.Layout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StyleSkin {
	

	/*
	 * @VISUAL OBJECTS
	 */
	public Texture style_atlas;
	
	/*
	 * @VISUAL SOURCES
	 */
	public Rectangle panel_9chunk_src = new Rectangle(0, 0, 8f, 8f);
	public Vector2   panel_9chunk_size = new Vector2(12f, 12f);
	public Rectangle button_9chunk_src = new Rectangle(24f, 0, 8f, 8f);
	public Vector2   button_9chunk_size = new Vector2(12f, 12f);
	public Rectangle buttonon_9chunk_src = new Rectangle(48f, 0, 8f, 8f);
	public Vector2   buttonon_9chunk_size = new Vector2(12f, 12f);
	public Rectangle editbox_9chunk_src = new Rectangle(0, 0, 0, 0);
	public Vector2   editbox_9chunk_size = new Vector2(0, 0);
	public Rectangle checkbox_9chunk_src = new Rectangle(0, 0, 0, 0);
	public Vector2   checkbox_9chunk_size = new Vector2(0, 0);
	
	/*
	 * @STATIC CONSTANT DEFAULTS
	 */
	
	public static final StyleSkin default_skin = new StyleSkin();
	
	public StyleSkin(){
		
	}
}
