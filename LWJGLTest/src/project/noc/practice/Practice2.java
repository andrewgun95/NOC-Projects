package project.noc.practice;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Practice2 {

	public static void main(String[] args) {

		// will create native Display window, to provide fast and small context to draw on, and low latency input
		try {
			Display.setDisplayMode(new DisplayMode(800, 480));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glMatrixMode(GL11.GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 480, 1, -1);
		glMatrixMode(GL11.GL_MODELVIEW);

		while (!Display.isCloseRequested()) {

			glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
			glBegin(GL_TRIANGLE_STRIP);
			glVertex2f(200, 200);
			glVertex2f(150, 250);
			glVertex2f(100, 200);
			glVertex2f(50, 250);
			glVertex2f(0, 200);
			glEnd();

			glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
			glBegin(GL_TRIANGLE_FAN);
			glVertex2f(300, 300);
			glVertex2f(400, 300);
			glVertex2f(400, 325);
			glVertex2f(375, 350);
			glVertex2f(350, 362.5f);
			glVertex2f(325, 368.75f);
			glEnd();

			// perform double buffering, and pool keyboard and mouse
			Display.update();
		}

		// destroy native Display, and clean up resource
		Display.destroy();

	}

}
