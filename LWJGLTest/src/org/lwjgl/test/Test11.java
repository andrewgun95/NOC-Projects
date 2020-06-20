package org.lwjgl.test;

import java.awt.Color;

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
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Test11 {

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(800, 480));
			Display.create();

			glMatrixMode(GL_PROJECTION);
			glOrtho(0, 800, 0, 480, 1, -1);

			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();

			final float radius = 25f;
			PVector location = new PVector(400, 240);
			Color color = new Color(0.8f, 0.8f, 0.8f);

			boolean drag = false;
			PVector dragOffset = new PVector();

			while (!Display.isCloseRequested()) {
				Display.update();
				Display.sync(30);

				glClearColor(1, 1, 1, 1);
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

				PVector mouse = new PVector(Mouse.getX(), Mouse.getY());
				PVector length = PVector.sub(mouse, location);

				while (Mouse.next()) {

					if (Mouse.getEventButton() == 0) {
						if (Mouse.getEventButtonState()) {
							if (length.mag() < radius) {
								color = new Color(0.6f, 0.6f, 0.6f);

								drag = true;
								dragOffset.set(length);

								System.out.println("Pressed");
							}
						} else {
							color = new Color(0.8f, 0.8f, 0.8f);
							
							drag = false;
							System.out.println("Released");
						}
					}

				}

				if (drag) {
					location.set(PVector.sub(mouse, dragOffset));

					System.out.println("Dragged");
				}

				circle(location, radius, color);
			}

			Display.destroy();

		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	static void circle(PVector location, float radius, Color color) {
		glPushMatrix();
		glTranslatef(location.x, location.y, .0f);

		final int numSegments = 100;

		glColor3f((float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f);
		glBegin(GL_TRIANGLE_FAN);
		for (int i = 0; i <= numSegments; i++) {
			double angle = i * (Math.PI * 2 / 100);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * (Math.PI * 2 / 100);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glPopMatrix();
	}

}
