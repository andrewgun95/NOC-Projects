package org.lwjgl.enviroment;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

public class Generator {

	static FontRenderContext getDefaultContext() {
		AffineTransform transform = new AffineTransform();
		FontRenderContext context = new FontRenderContext(transform, true, true);
		return context;
	}

	public static BufferedImage glFont(String str, Font font, Color color){
		// initialize context
		FontRenderContext context = getDefaultContext();
		// string bounding box
		Rectangle2D bound = font.getStringBounds(str, context);

		int imageWidth = (int) Math.ceil(bound.getWidth());
		int imageHeight = (int) Math.ceil(bound.getHeight());

		// specify image with string bound
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);

		Graphics g = image.getGraphics();
		g.setFont(font);
		g.setColor(color);
		g.drawString(str, 0, image.getHeight());
		
		return image;
	}

	public static int glTexture(BufferedImage image){
		int pixels[] = new int[image.getWidth() * image.getHeight()];
		pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		// 1 pixel -> 4 data (red, green, blue, alpha)
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

		// change y pixels direction, because buffered image x, y from top, left corner to bottom, right corner
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixelx = x;
				int pixely = (image.getHeight() - 1) - y;
				
				// pixels[y * image.getWidth + x] -> pixels[x][y]
				int pixel = pixels[pixely * image.getWidth() + pixelx];
				// convert to red component
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				// convert to green component
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				// convert to blue component
				buffer.put((byte) ((pixel >> 0) & 0xFF));
				// convert to alpha component
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		// change buffer from set to get
		buffer.flip();
		
		return glImage(glGenTextures(), image.getWidth(), image.getHeight(), buffer);
	}

	static int glImage(int texture, int width, int height, ByteBuffer buffer) {
		glBindTexture(GL_TEXTURE_2D, texture);

		// setup wrapping for image
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);

		// setup filtering for image
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		return texture;
	}

}
