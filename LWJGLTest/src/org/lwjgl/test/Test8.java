package org.lwjgl.test;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

public class Test8 {

	static final float WIDTH = 5;
	static final float HEIGHT = 3;
	static final float PPM = 160f;

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode((int) (WIDTH * PPM), (int) (HEIGHT * PPM)));
			Display.create();

			// OPEN GL'S setup
			// projection : 3D coordinate system to 2D coordinate system (computer coordinate)
			glMatrixMode(GL_PROJECTION);
			glOrtho(0, WIDTH, 0, HEIGHT, 10, -10);
			// enable depth testing for z-culling
			glEnable(GL_DEPTH_TEST);

			float angle = 0;

			while (!Display.isCloseRequested()) {
				Display.update();
				Display.sync(60);

				// model view : object coordinate to world coordinate
				glMatrixMode(GL_MODELVIEW);

				// reset model view
				glLoadIdentity();

				// clear screen
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

				angle++;

				glPushMatrix();
				// center position of world coordinate

				// transformation
				glTranslatef(WIDTH / 2, HEIGHT / 2, 0);
				glRotatef(angle, 0, 1, 0);
				glRotatef(angle, 1, 0, 0);
				glRotatef(angle, 0, 0, 1);
				glScalef(0.5f, 0.5f, 0.5f);

				glBegin(GL_QUADS);

				// front side quad
				glColor3f(0.8f, 0.8f, 0.8f);
				glVertex3f(-1.0f, -1.0f, 1.0f);
				glVertex3f(1.0f, -1.0f, 1.0f);
				glVertex3f(1.0f, 1.0f, 1.0f);
				glVertex3f(-1.0f, 1.0f, 1.0f);

				// back side quad
				glColor3f(0.8f, 0.8f, 0.8f);
				glVertex3f(-1.0f, -1.0f, -1.0f);
				glVertex3f(1.0f, -1.0f, -1.0f);
				glVertex3f(1.0f, 1.0f, -1.0f);
				glVertex3f(-1.0f, 1.0f, -1.0f);

				// right side quad
				glColor3f(0.6f, 0.6f, 0.6f);
				glVertex3f(1.0f, -1.0f, 1.0f);
				glVertex3f(1.0f, -1.0f, -1.0f);
				glVertex3f(1.0f, 1.0f, -1.0f);
				glVertex3f(1.0f, 1.0f, 1.0f);

				// left side quad
				glColor3f(0.6f, 0.6f, 0.6f);
				glVertex3f(-1.0f, -1.0f, 1.0f);
				glVertex3f(-1.0f, -1.0f, -1.0f);
				glVertex3f(-1.0f, 1.0f, -1.0f);
				glVertex3f(-1.0f, 1.0f, 1.0f);

				// top side quad
				glColor3f(0.4f, 0.4f, 0.4f);
				glVertex3f(-1.0f, 1.0f, 1.0f);
				glVertex3f(1.0f, 1.0f, 1.0f);
				glVertex3f(1.0f, 1.0f, -1.0f);
				glVertex3f(-1.0f, 1.0f, -1.0f);

				// bottom side quad
				glColor3f(0.4f, 0.4f, 0.4f);
				glVertex3f(-1.0f, -1.0f, 1.0f);
				glVertex3f(1.0f, -1.0f, 1.0f);
				glVertex3f(1.0f, -1.0f, -1.0f);
				glVertex3f(-1.0f, -1.0f, -1.0f);

				glEnd();

				glColor3f(0.3f, 0.3f, 0.3f);
				glBegin(GL_LINE_LOOP);

				// front side line
				glVertex3f(-1.0f, -1.0f, 1.0f);
				glVertex3f(1.0f, -1.0f, 1.0f);
				glVertex3f(1.0f, 1.0f, 1.0f);
				glVertex3f(-1.0f, 1.0f, 1.0f);

				// back side line
				glVertex3f(-1.0f, -1.0f, -1.0f);
				glVertex3f(1.0f, -1.0f, -1.0f);
				glVertex3f(1.0f, 1.0f, -1.0f);
				glVertex3f(-1.0f, 1.0f, -1.0f);

				// right side line
				glVertex3f(1.0f, -1.0f, 1.0f);
				glVertex3f(1.0f, -1.0f, -1.0f);
				glVertex3f(1.0f, 1.0f, -1.0f);
				glVertex3f(1.0f, 1.0f, 1.0f);

				// left side line
				glVertex3f(-1.0f, -1.0f, 1.0f);
				glVertex3f(-1.0f, -1.0f, -1.0f);
				glVertex3f(-1.0f, 1.0f, -1.0f);
				glVertex3f(-1.0f, 1.0f, 1.0f);

				// top side line
				glVertex3f(-1.0f, 1.0f, 1.0f);
				glVertex3f(1.0f, 1.0f, 1.0f);
				glVertex3f(1.0f, 1.0f, -1.0f);
				glVertex3f(-1.0f, 1.0f, -1.0f);

				// bottom side line
				glVertex3f(-1.0f, -1.0f, 1.0f);
				glVertex3f(1.0f, -1.0f, 1.0f);
				glVertex3f(1.0f, -1.0f, -1.0f);
				glVertex3f(-1.0f, -1.0f, -1.0f);

				glEnd();

				glPopMatrix();

			}

			Display.destroy();

		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

}
