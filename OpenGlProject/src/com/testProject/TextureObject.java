package com.testProject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class TextureObject {
	
	private int textureI;
	
	public TextureObject(String path) {
		textureI = loadTexture(path);
	}
	public int loadTexture(String path) {
		try {
			PNGDecoder decoder = new PNGDecoder(new FileInputStream(path));
			
			//System.out.println("Loaded image with dimensions: "+decoder.getWidth()+"x"+decoder.getHeight());
			
			
			ByteBuffer buffer = ByteBuffer.allocate(decoder.getWidth()*decoder.getHeight()*4);
			decoder.decode(buffer, decoder.getWidth()*4, Format.RGBA);
			buffer.flip();
			/*int size = decoder.getWidth()*decoder.getHeight()*4;
			byte[] bff = buffer.array();
			for(int i=3; i<=size*.01; i+=4)
				bff[i] = (byte)1;
			//System.out.println(bff[4]);
			//System.out.println(bff[5]);
			//System.out.println(bff[6]);
			//System.out.println(bff[7]);
			buffer = ByteBuffer.allocate(bff.length);
			buffer.put(bff);*/
			//System.out.println(buffer.array()[0]);
			//System.out.println(buffer.array()[1]);
			//System.out.println(buffer.array()[2]);
			//System.out.println(buffer.array()[3]);
			//System.out.println(buffer.array()[4]);
			//System.out.println(buffer.array()[5]);
			//System.out.println(buffer.array()[6]);
			//System.out.println(buffer.array()[7]);
			
			int textureI = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureI);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			
			return textureI;
		}catch(IOException e) {
			System.err.println("Not loaded image at:'"+path+"'.");
			return -1;
		}
	}
	public int getTexture() {
		return textureI;
	}
}
