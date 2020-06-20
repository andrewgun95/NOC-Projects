package org.lwjgl.test;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PIntersector;
import project.noc.utils.PVector;

public class Test10 {

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

			Point2D.Float point = new Point2D.Float(400, 10);
			Line2D.Float line = new Line2D.Float(0, 480, 800, 0);
			// correct
			float distance = PIntersector.distanceLinePoint(line.x1, line.y1, line.x2, line.y2, point.x, point.y);
			System.out.println(distance);

			while (!Display.isCloseRequested()) {
				Display.update();
				Display.sync(60);

				// point
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
				glColor3f(0.5f, 0.5f, 0.5f);
				glPointSize(2.0f);
				glBegin(GL_POINTS);
				glVertex2f(point.x, point.y);
				glEnd();

				// line
				glColor3f(0.5f, 0.5f, 0.5f);
				glBegin(GL_LINES);
				glVertex2f(line.x1, line.y1);
				glVertex2f(line.x2, line.y2);
				glEnd();

				point.y++;
				PVector near = PIntersector.nearestSegmentPoint(line.x1, line.y1, line.x2, line.y2, point.x, point.y, new PVector());

				glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
				glBegin(GL_LINES);
				glVertex2f(near.x, near.y);
				glVertex2f(point.x, point.y);
				glEnd();
			}

			Display.destroy();

		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

}
