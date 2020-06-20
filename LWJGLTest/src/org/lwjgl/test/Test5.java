package org.lwjgl.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
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

public class Test5 {

	static final int WIDTH = 800;
	static final int HEIGHT = 480;

	static void glSetup() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	private static int loadTexture(BufferedImage image) {
		int pixels[] = new int[image.getWidth() * image.getHeight()];
		pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

		int colorNums = image.getColorModel().getNumComponents();

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * colorNums);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				// convert color values
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) ((pixel >> 0) & 0xFF));
				// convert alpha values
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		buffer.flip();

		return glImage(glGenTextures(), image.getWidth(), image.getHeight(), buffer);
	}

	private static int glImage(int texture, int width, int height, ByteBuffer buffer) {
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

	private static BufferedImage image(String str, Font font, Color color) {
		// initialize context
		FontRenderContext context = new FontRenderContext(new AffineTransform(), true, true);
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

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();

			glEnable(GL_TEXTURE_2D);
			glSetup();

			BufferedImage image;

			while (!Display.isCloseRequested()) {
				// HEIGHT - Mouse.getY() and - Mouse.getDY() to improve correct
				// position view based of orthographic, position of the mouse by
				// default will be in bottom left corner display

				// position
				String message1 = "Mouse : (" + Mouse.getX() + " , " + (HEIGHT - Mouse.getY()) + ")";
				// movement
				String message2 = "DMouse : (" + Mouse.getDX() + " , " + (-Mouse.getDY()) + ")";

				image = image(message1 + " " + message2, new Font(Font.MONOSPACED, Font.PLAIN, 20), Color.WHITE);
				int texture = loadTexture(image);

				Display.sync(30);
				Display.update();

				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				glClearColor(0, 0, 0, 1);

				glPushMatrix();
				// transform
				glTranslatef(0, 0, 0);

				// drawing
				glBindTexture(GL_TEXTURE_2D, texture);
				glBegin(GL_QUADS);

				glTexCoord2f(0, 0);
				glVertex2f(0, 0);

				glTexCoord2f(1, 0);
				glVertex2f(image.getWidth(), 0);

				glTexCoord2f(1, 1);
				glVertex2f(image.getWidth(), image.getHeight());

				glTexCoord2f(0, 1);
				glVertex2f(0, image.getHeight());

				glEnd();

				glPopMatrix();
			}

		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

}
