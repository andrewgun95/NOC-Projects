package org.lwjgl.test;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
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
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMatrix;
import project.noc.utils.PVector;

public class Test9 {

	private static void glSetup() {
		glMatrixMode(GL_PROJECTION);
		glOrtho(0, 800, 0, 480, 1, -1);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(800, 480));
			Display.create();

			glSetup();

			Mover a = new Mover(200f, 120f);

			PVector vel = new PVector(0.8660254f, 0.5f);
			vel.dir();

			while (!Display.isCloseRequested()) {
				Display.update();
				Display.sync(60);

				// update
				PVector normal = PMatrix.rotate2D_i(PVector.angle(vel), vel);

				if (Mouse.isButtonDown(0)) {
					normal.x = -1.0f;
				}

				PVector newvel = PMatrix.rotate2D(PVector.angle(vel), normal);

				vel.set(newvel);

				a.loc.add(vel);

				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

				a.draw();
			}

			Display.destroy();

		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	static class Mover {

		PVector loc;

		public Mover(float x, float y) {
			loc = new PVector(x, y);
		}

		public void draw() {
			glPushMatrix();
			glTranslatef(loc.x, loc.y, 0.0f);

			// circle
			glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
			glBegin(GL_TRIANGLE_FAN);
			// numSegments + 1 back to 0 degree
			for (int i = 0; i < 100 + 1; i++) {
				double angle = i * (Math.PI * 2 / 100);
				glVertex2f((float) (Math.cos(angle) * 25), (float) (Math.sin(angle) * 25));
			}
			glEnd();
			// lines
			glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
			glBegin(GL_LINE_LOOP);
			for (int i = 0; i < 100; i++) {
				double angle = i * (Math.PI * 2 / 100);
				glVertex2f((float) (Math.cos(angle) * 25), (float) (Math.sin(angle) * 25));
			}
			glEnd();

			glPopMatrix();
		}

	}

}
