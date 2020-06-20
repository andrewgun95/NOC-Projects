package org.lwjgl.test;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Test6 {

	static final int WIDTH = 800;
	static final int HEIGHT = 480;
	static final int FPS = 60;

	static long lastFrame = 0;
	static int counter = 0;

	static long lastDelta = 0;

	public static void main(String[] args) {

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("FPS : " + counter);
			Display.create();

			glSetup();
			glEnable(GL_TEXTURE_2D);

			lastFrame = getTime();
			lastDelta = getTime();

			while (!Display.isCloseRequested()) {

				// update
				Display.sync(FPS);
				Display.update();

				// left mouse button
				if (Mouse.isButtonDown(0)) {
					System.out.println("Mouse Left Pressed");
				}
				if (Mouse.isButtonDown(1)) {
					System.out.println("Mouse Right Pressed");
				}

				// perform buffer input
				while (Keyboard.next()) {
					// same as Keyboard.isKeyDown(Keyboard.KEY_A)
					if (Keyboard.getEventKey() == Keyboard.KEY_A) {
						System.out.println("A Pressed");
					}
					// perform hold
					if (Keyboard.getEventKeyState()) {
						if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
							System.out.println("Pressed");
						}
					} else {
						System.out.println("Release");
					}
				}

				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				glClearColor(1, 1, 1, 1);

				float x = WIDTH / 2;
				float y = HEIGHT / 2;
				float radius = 100f;
				int n = 12;

				glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
				glBegin(GL_TRIANGLE_FAN);
				glVertex2f(x, y);
				// n + 1 : 330 degree to 360 degree (0 degree)
				for (int i = 0; i < n + 1; i++) {
					// angle in radian
					double angle = i * (2 * Math.PI / n);
					glVertex2f(x + (float) Math.cos(angle) * radius, y + (float) Math.sin(angle) * radius);
				}
				glEnd();

				glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
				glLineWidth(1);
				glBegin(GL_LINE_LOOP);
				for (int i = 0; i < n; i++) {
					// angle in radian
					double angle = i * (2 * Math.PI / n);

					glVertex2f(x + (float) Math.cos(angle) * radius, y + (float) Math.sin(angle) * radius);
				}
				glEnd();

				updateFPS();

				System.out.println(getDelta());
			}

			Display.destroy();

		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private static void updateFPS() {
		// print each 1 second
		if (getTime() - lastFrame > 1000) {
			Display.setTitle("FPS : " + counter);
			counter = 0;
			lastFrame = getTime();
		}
		counter++;
	}

	private static float getDelta() {
		long time = getTime();
		float delta = (float) (time - lastDelta);
		lastDelta = time;
		return delta / 1000f;
	}

	private static long getTime() {
		// millisecond
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	private static void glSetup() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

}
