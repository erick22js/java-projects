package com.idcorporation.redoom64.gameplay;

import java.io.File;
import java.io.InputStream;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.handling.Wad;
import com.idcorporation.redoom64.gameplay.map.GameMap;
import com.idcorporation.redoom64.gameplay.map.Linedef;
import com.idcorporation.redoom64.gameplay.map.MapLoader;
import com.idcorporation.redoom64.gameplay.map.Sector;
import com.idcorporation.redoom64.gameplay.map.Segment;
import com.idcorporation.redoom64.gameplay.map.Sidedef;
import com.idcorporation.redoom64.gameplay.map.SubSector;
import com.idcorporation.redoom64.gameplay.map.Vertex;
import com.idcorporation.redoom64.renderization.BSPNodes;
import com.idcorporation.redoom64.renderization.Projection;
import com.idcorporation.redoom64.utils.MathDoom;
import com.idcorporation.redoom64.utils.numberUtil;


public class Main extends Game{

	Batch batch;
	ModelBatch mbatch;
	
	Mesh tmesh;
	
	PerspectiveCamera camera;
	
	Texture texw;
	Pixmap texpm;
	
	Wad gameData;
	Sector[] sectors;
	Sidedef[] sidedefs;
	Linedef[] lines;
	
	GameMap mapa;
	
	Projection proj;
	BSPNodes bsp;
	
	ShaderProgram program;
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		gameData = new Wad(Gdx.files.internal("assets/DOOM64.WAD").readBytes());
		
		batch = new SpriteBatch();
		mbatch = new ModelBatch();
		
		camera = new PerspectiveCamera(45, 2, 1);
		camera.far = 4096f;
		camera.near = .125f;
		camera.lookAt(0, 0, 0);
		//camera.position.set(0f, 0f, -10f);
		camera.update();
		
		byte[] data = gameData.get_lump_bytes(1217);
		texpm = new Pixmap(data, 0, data.length);
		texw = new Texture(texpm/*"assets/SPACEAN.png"*/);
		//new Texture();
		texw.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		program = new ShaderProgram(Gdx.files.internal("assets/vertex.glsh"), Gdx.files.internal("assets/fragment.glsh"));
		
		//Map
		Vertex[] vert = Vertex.loadVertexesFromMap(new Wad(gameData.get_lump_bytes(1616)));
		
		sectors = Sector.loadSectorsFromMap(new Wad(gameData.get_lump_bytes(1616)));
		sidedefs = Sidedef.loadSidedefsFromMap(new Wad(gameData.get_lump_bytes(1616)), sectors);
		
		lines = Linedef.loadLinedefsFromMap(new Wad(gameData.get_lump_bytes(1616)), vert, sidedefs);
		
		mapa = new MapLoader(new Wad(gameData.get_lump_bytes(1616))).retrieveLoadedMap();
		
		camera.translate(676,164,1344);
		camera.update();
		
		proj = new Projection(0, 0, 0, 0, (float) (Math.PI)/180*75, camera.combined);
		bsp = new BSPNodes(mapa, proj);
		
		//System.err.println(mapa.LINEDEFS[30].index);
		
		Gdx.gl20.glEnable(Gdx.gl20.GL_DEPTH_TEST);
		//Gdx.gl20.glDisable(Gdx.gl20.GL_DEPTH_TEST);
	}
	
	float pepe = 0;
	float rotato = -90;
	@Override
	public void render() {
		//Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT|Gdx.gl.GL_DEPTH_BUFFER_BIT);
		
		
		
		
		float an = (float) Math.toRadians(rotato);
		float sp = 8f;
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.translate((float) Math.cos(an)*sp, 0, (float) Math.sin(an)*sp);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			camera.translate((float) -Math.cos(an)*sp, 0, (float) -Math.sin(an)*sp);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.translate(0,-6,0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E)){
			camera.translate(0,6,0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			rotato-=2;
			camera.rotate(2, 0, 1, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			rotato+=2;
			camera.rotate(2, 0, -1, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
			rotato-=.25f;
			camera.rotate(.25f, 0, 1, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.C)){
			rotato+=.25f;
			camera.rotate(.25f, 0, -1, 0);
		}
		an += an>MathDoom.PI?-MathDoom.PI*2:an<-MathDoom.PI?MathDoom.PI*2:0;
		camera.update();
		
		proj.x = camera.position.x;
		//System.out.println(proj.x);
		proj.z = camera.position.z;
		proj.direction = an;
		
		int ssind = bsp.LocationBSPNode(520);
		System.out.println(ssind);
		//System.exit(0);
		SubSector ssector = mapa.SSECTORS[ssind];
		Segment seg = ssector.SEGS[0];
		
		Sector sect;
		
		if(seg.isFront)
			sect = seg.line.front.sector;
		else
			sect = seg.line.back.sector;
		
		//mapa.SECTORS[]
		
		//System.out.println(sect.index);

		tmesh = new Mesh(true, 4, 4, 
				new VertexAttribute(0, 3, "a_position"),
				new VertexAttribute(0, 2, "n_position"),
				new VertexAttribute(0, 4, "a_color"), 
				new VertexAttribute(0, 2, "a_uv"));
		tmesh.setIndices(new short[] {0, 1, 2, 3});// 0, 2, 3});
		program.setUniformMatrix("proj_matrix", camera.combined);
		tmesh.bind(program);
		program.bind();
		
		texw.bind();
		;
		bsp.RenderBSPNode(tmesh, program);
		/*for(int i=0; i<sect.linedefs.length; i++) {
			Linedef line = sect.linedefs[i];
			//if(line==null)
			//	continue;
			Sector sec = line.front.sector;
			tmesh.setVertices(new float[] {
					line.v1.x,  sec.ceilY,  line.v1.y,     1f, 0f, 0f, 1f,     0f, 0f,
					line.v1.x, sec.floorY,  line.v1.y,     0f, 1f, 0f, 1f,     0f, 1f,
					line.v2.x, sec.floorY,  line.v2.y,     0f, 0f, 1f, 1f,     1f, 1f,
					line.v2.x,  sec.ceilY,  line.v2.y,     1f, 1f, 0f, 1f,     1f, 0f,
			});
			if(line.front==null||line.back==null)
				tmesh.render(program, Gdx.gl.GL_TRIANGLE_FAN);
		}*/
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
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
	public void dispose() {
		super.dispose();
	}
	
}
