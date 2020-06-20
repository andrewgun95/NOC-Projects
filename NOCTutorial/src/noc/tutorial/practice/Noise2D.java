package noc.tutorial.practice;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMath;

public class Noise2D extends Application {

	private int texture;
	private int width, height;

	@Override
	public void setup() {
		glEnable(GL_TEXTURE_2D);

		width = 256;
		height = 256;
	}

	@Override
	public void draw() {
		int pixels[] = new int[width * height];

		double ySpace = 0.0;

		for (int y = 0; y < width; y++) {
			double xSpace = 0.0;
			for (int x = 0; x < height; x++) {

				float value = (float) PMath.noise(xSpace, ySpace);

				value = PMath.map(value, -0.05f, 0.05f, 0, 255);

				pixels[y * width + x] = colorRGBA((int) value, (int) value, (int) value, (int) value);

				xSpace += 0.02;
			}
			ySpace += 0.02;
		}

		texture = loadTexture(pixels, width, height);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glBindTexture(GL_TEXTURE_2D, texture);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(400 - width / 2, 240 - height / 2);

		glTexCoord2f(1, 0);
		glVertex2f((400 - width / 2) + width, 240 - height / 2);

		glTexCoord2f(1, 1);
		glVertex2f((400 - width / 2) + width, (240 - height / 2) + height);

		glTexCoord2f(0, 1);
		glVertex2f(400 - width / 2, (240 - height / 2) + height);
		glEnd();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Noise2D());
	}

	static int colorRGBA(int r, int g, int b, int a) {
		return ((a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF) << 0);
	}

	static int loadTexture(int[] rgb, int width, int height) {
		int pixels[] = new int[width * height];
		pixels = rgb;

		// 1 pixel -> 4 data (red, green, blue, alpha)
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

		for (int y = 0; y < width; y++) {
			for (int x = 0; x < height; x++) {
				// pixels[y * image.getWidth + x] -> pixels[x][y]
				int pixel = pixels[y * width + x];
				// convert to alpha component
				buffer.put((byte) ((pixel >> 24) & 0xFF));
				// convert to red component
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				// convert to green component
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				// convert to blue component
				buffer.put((byte) ((pixel >> 0) & 0xFF));
			}
		}

		buffer.flip();
		// generate texture id
		int textureID = glGenTextures();

		// bind texture ID
		glBindTexture(GL_TEXTURE_2D, textureID);

		// setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		// setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		return textureID;
	}
}
