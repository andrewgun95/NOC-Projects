package org.lwjgl.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Test4 {

	static final int WIDTH = 800;
	static final int HEIGHT = 480;
	static final int FPS = 60;

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();

			BufferedImage image = null;
			try {
				image = ImageIO.read(new File("res/image.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			final int data = loadTexture(image);

			glSetup();
			// to activated texture
			glEnable(GL_TEXTURE_2D);

			while (!Display.isCloseRequested()) {

				Display.sync(FPS);
				Display.update();

				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

				glPushMatrix();
				glTranslatef(WIDTH / 2, HEIGHT / 2, 0);

				// draw here
				glBindTexture(GL_TEXTURE_2D, data);
				glBegin(GL_QUADS);
				
				// sampling color information from bottom-left corner
				glTexCoord2f(0, 0);
				glVertex2f(0, 0);

				// sampling color information from bottom-right corner
				glTexCoord2f(1, 0);
				glVertex2f(100, 0);

				// sampling color information from top-right corner
				glTexCoord2f(1, 1);
				glVertex2f(100, 100);

				// sampling color information from top-left corner
				glTexCoord2f(0, 1);
				glVertex2f(0, 100);

				glEnd();

				glPopMatrix();

			}
			Display.destroy();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static int loadTexture(BufferedImage image) {
		int pixels[] = new int[image.getWidth() * image.getHeight()];
		pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		// 1 pixel -> 4 data (red, green, blue, alpha)
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

		// change y pixels direction, because buffered image x, y from top, left corner to bottom, right corner
		for (int y = image.getHeight() - 1; y >= 0; y--) {
			for (int x = 0; x < image.getWidth(); x++) {
				// pixels[y * image.getWidth + x] -> pixels[x][y]
				int pixel = pixels[y * image.getWidth() + x];
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
		// generate texture id
		int textureID = glGenTextures();

		// bind texture ID
		glBindTexture(GL_TEXTURE_2D, textureID);

		// setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);

		// setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		return textureID;
	}

	private static void glSetup() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

}
