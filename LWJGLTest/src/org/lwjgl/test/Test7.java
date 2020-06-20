package org.lwjgl.test;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Test7 {

	// screen width, screen height
	static final int WIDTH = 800;
	static final int HEIGHT = 480;

	static final float PPM = 160f;
	static final int FPS = 60;

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();

			glSetup();

			PVector loc1 = new PVector(getWorldW() / 4, getWorldH() / 2);
			double angle1 = 0.0;
			PVector loc2 = new PVector((getWorldW() / 4) * 3, getWorldH() / 2);
			double angle2 = 0.0;

			float size = 0.2f;

			while (!Display.isCloseRequested()) {
				Display.sync(60);
				Display.update();

				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

				glPushMatrix();
				glTranslatef(loc1.x, loc1.y, 0);
				angle1 += 2;
				glRotated(angle1, 0, 0, 1);

				glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
				glBegin(GL_QUADS);
				glVertex2f(-size / 2, -size / 2);
				glVertex2f(size / 2, -size / 2);
				glVertex2f(size / 2, size / 2);
				glVertex2f(-size / 2, size / 2);
				glEnd();

				glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
				glBegin(GL_LINE_LOOP);
				glVertex2f(-size / 2, -size / 2);
				glVertex2f(size / 2, -size / 2);
				glVertex2f(size / 2, size / 2);
				glVertex2f(-size / 2, size / 2);
				glEnd();

				glPopMatrix();

				glPushMatrix();
				glTranslatef(loc2.x, loc2.y, 0);
				angle2 += 3;
				glRotated(angle2, 0, 0, 1);

				glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
				glBegin(GL_QUADS);
				glVertex2f(-size / 2, -size / 2);
				glVertex2f(size / 2, -size / 2);
				glVertex2f(size / 2, size / 2);
				glVertex2f(-size / 2, size / 2);
				glEnd();

				glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
				glBegin(GL_LINE_LOOP);
				glVertex2f(-size / 2, -size / 2);
				glVertex2f(size / 2, -size / 2);
				glVertex2f(size / 2, size / 2);
				glVertex2f(-size / 2, size / 2);
				glEnd();

				glPopMatrix();
			}

		} catch (LWJGLException e) {
			e.printStackTrace();
		}

	}

	private static void glSetup() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH / PPM, 0, HEIGHT / PPM, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	static float getWorldW() {
		return WIDTH / PPM;
	}

	static float getWorldH() {
		return HEIGHT / PPM;
	}

}
