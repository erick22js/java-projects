package com.Techne.renderer;

import com.Techne.base.Mesh;
import com.Techne.base.ShaderProgram;
import com.Techne.core.Database;
import com.Techne.core.GameMap;
import com.Techne.core.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.Map;

public class MapChunkMesh {
	
	public static ShaderProgram program;
	public static Texture tile_texture;
	private static boolean program_initiated = false;
	private static final int attV = 9;
	private Mesh geometry;
	
	private GameMap game_map;
	private int width;
	private int height;
	private int offset_x;
	private int offset_y;
	
	private short[] indices;
	private float[] vertices;
	
	public boolean toUpdate = true;
	
	public MapChunkMesh() {
		init(0, 0, 0, 0);
	}
	public MapChunkMesh(int offset_x, int offset_y, int width, int height) {
		init(offset_x, offset_y, width, height);
	}
	private void init(int offset_x, int offset_y, int width, int height) {
		if(!program_initiated) {
			program = new ShaderProgram(
					Gdx.files.internal("assets/shaders/tilemap.vertex.sl").readString(),
					Gdx.files.internal("assets/shaders/tilemap.fragment.sl").readString(),
					new String[] {"aPosition", "aColor", "aUv1", "aUv2"}, new int[] {2, 3, 2, 2},
					new String[] {"uMatrix", "uTex"}
				);
			tile_texture = new Texture(Gdx.files.internal("assets/tiles.png"));
			program_initiated = true;
		}
		this.width = width;
		this.height = height;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
		this.generateGeometry();
	}
	private void generateGeometry() {
		geometry = new Mesh();
		indices = new short[6*width*height];
		int ind_a = 0;
		vertices = new float[attV*4*width*height];
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				indices[(x*width+y)*6+0] = (short)(ind_a+0);
				indices[(x*width+y)*6+1] = (short)(ind_a+1);
				indices[(x*width+y)*6+2] = (short)(ind_a+2);
				indices[(x*width+y)*6+3] = (short)(ind_a+0);
				indices[(x*width+y)*6+4] = (short)(ind_a+2);
				indices[(x*width+y)*6+5] = (short)(ind_a+3);
				ind_a += 4;
				vertices[(x*width+y)*attV*4+0]  = x+offset_x+0;   vertices[(x*width+y)*attV*4+1] = y+offset_y+1;
				vertices[(x*width+y)*attV*4+2]  = 1;              vertices[(x*width+y)*attV*4+3] = 0;              vertices[(x*width+y)*attV*4+4]  = 0;
				vertices[(x*width+y)*attV*4+5]  = 1;              vertices[(x*width+y)*attV*4+6] = 0;
				vertices[(x*width+y)*attV*4+7]  = 4;              vertices[(x*width+y)*attV*4+8] = 1;
				
				vertices[(x*width+y)*attV*4+9]  = x+offset_x+1;   vertices[(x*width+y)*attV*4+10] = y+offset_y+1;
				vertices[(x*width+y)*attV*4+11] = 0;              vertices[(x*width+y)*attV*4+12] = 1;             vertices[(x*width+y)*attV*4+13]  = 0;
				vertices[(x*width+y)*attV*4+14] = 2;              vertices[(x*width+y)*attV*4+15] = 0;
				vertices[(x*width+y)*attV*4+16] = 5;              vertices[(x*width+y)*attV*4+17] = 1;
				
				vertices[(x*width+y)*attV*4+18] = x+offset_x+1;   vertices[(x*width+y)*attV*4+19] = y+offset_y+0;
				vertices[(x*width+y)*attV*4+20] = 0;              vertices[(x*width+y)*attV*4+21] = 0;             vertices[(x*width+y)*attV*4+22] = 1;
				vertices[(x*width+y)*attV*4+23] = 2;              vertices[(x*width+y)*attV*4+24] = 1;
				vertices[(x*width+y)*attV*4+25] = 5;              vertices[(x*width+y)*attV*4+26] = 2;
				
				vertices[(x*width+y)*attV*4+27] = x+offset_x+0;   vertices[(x*width+y)*attV*4+28] = y+offset_y+0;
				vertices[(x*width+y)*attV*4+29] = 1;              vertices[(x*width+y)*attV*4+30] = 1;             vertices[(x*width+y)*attV*4+31] = 0;
				vertices[(x*width+y)*attV*4+32] = 1;              vertices[(x*width+y)*attV*4+33] = 1;
				vertices[(x*width+y)*attV*4+34] = 4;              vertices[(x*width+y)*attV*4+35] = 2;
				
			}
		}
		geometry.setIndices(indices);
		geometry.setVertices(vertices);
		updateGeometry();
	}
	public void bindMap(GameMap gameMap) {
		if(this.game_map!=null)
			return;
		this.game_map = gameMap;
	}
	public void updateGeometry() {
		if(this.game_map==null)
			return;
		Tile tile;
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				tile = game_map.getTile(offset_x+x, offset_y+y);
				
				int base_x = tile.ground? Database.tex_ground_x: Database.tex_sea_x;
				int base_y = tile.ground? Database.tex_ground_y: Database.tex_sea_y;
				int top_x = 0;
				int top_y = 0;
				
				float r = tile.owner==null?1:tile.owner.id_color.r;
				float g = tile.owner==null?1:tile.owner.id_color.g;
				float b = tile.owner==null?1:tile.owner.id_color.b;
				
				vertices[(x*width+y)*attV*4+2]  = r;              vertices[(x*width+y)*attV*4+3]  = g;              vertices[(x*width+y)*attV*4+4]  = b;
				vertices[(x*width+y)*attV*4+5]  = base_x+0;       vertices[(x*width+y)*attV*4+6]  = base_y+0;
				vertices[(x*width+y)*attV*4+7]  = top_x+0;        vertices[(x*width+y)*attV*4+8]  = top_y+0;
				
				vertices[(x*width+y)*attV*4+11] = r;              vertices[(x*width+y)*attV*4+12] = g;             vertices[(x*width+y)*attV*4+13]  = b;
				vertices[(x*width+y)*attV*4+14] = base_x+1;       vertices[(x*width+y)*attV*4+15] = base_y+0;
				vertices[(x*width+y)*attV*4+16] = top_x+1;        vertices[(x*width+y)*attV*4+17] = top_y+0;
				
				vertices[(x*width+y)*attV*4+20] = r;              vertices[(x*width+y)*attV*4+21] = g;             vertices[(x*width+y)*attV*4+22]  = b;
				vertices[(x*width+y)*attV*4+23] = base_x+1;       vertices[(x*width+y)*attV*4+24] = base_y+1;
				vertices[(x*width+y)*attV*4+25] = top_x+1;        vertices[(x*width+y)*attV*4+26] = top_y+1;
				
				vertices[(x*width+y)*attV*4+29] = r;              vertices[(x*width+y)*attV*4+30] = g;             vertices[(x*width+y)*attV*4+31]  = b;
				vertices[(x*width+y)*attV*4+32] = base_x+0;       vertices[(x*width+y)*attV*4+33] = base_y+1;
				vertices[(x*width+y)*attV*4+34] = top_x+0;        vertices[(x*width+y)*attV*4+35] = top_y+1;
				
			}
		}
		geometry.setVertices(vertices);
	}
	public void render(float[] gMatrix) {
		if(toUpdate) {
			updateGeometry();
			toUpdate = false;
		}
		program.useShader();
		tile_texture.bind(0);
		Gdx.gl20.glUniformMatrix4fv(program.getUniform("uMatrix"), 1, true, gMatrix, 0);
		Gdx.gl20.glUniform1i(program.getUniform("uTex"), 0);
		geometry.draw(program, Gdx.gl.GL_TRIANGLES);
	}
}
