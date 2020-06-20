package org.lwjgl.test;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_QUAD_STRIP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Test2 {

	static final int WIDTH = 800;
	static final int HEIGHT = 480;
	static final int FPS = 60;

	static void glSetup() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode((int) WIDTH, (int) HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glSetup();

		while (!Display.isCloseRequested()) {
			Display.sync(60);
			Display.update();

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glClearColor(1, 1, 1, 1);

			// draw points : treats each vertex as a single point. vertex n defines point n. n points are drawn.
			glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
			glPointSize(10);
			glBegin(GL_POINTS);
			glVertex2f(20, 20);
			glVertex2f(40, 40);
			glVertex2f(60, 60);
			glEnd();

			// draw lines : treats each pair of vertices as an independent line segment.
			// vertices 2 ⁢ n - 1 and 2 ⁢ n define line n. n 2 lines are drawn.
			glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
			glLineWidth(2);
			glBegin(GL_LINES);
			// single line : two pair of vertex
			glVertex2f(100, 100);
			glVertex2f(100, 200);
			// single line : two pair of vertex
			glVertex2f(300, 100);
			glVertex2f(300, 200);
			glEnd();

			// draw lines : draws a connected group of line segments from the first vertex to the last
			glColor4f(0.2f, 0.2f, 0.2f, 1.0f);
			glLineWidth(2);
			glBegin(GL_LINE_STRIP);
			glVertex2f(250, 250);
			glVertex2f(250, 350);
			glVertex2f(350, 350);
			glVertex2f(350, 250);
			glEnd();

			// draw lines : draws a connected group of line segments from the first vertex to the last, then back to the first
			glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
			glLineWidth(2);
			glBegin(GL_LINE_LOOP);
			glVertex2f(400, 400);
			glVertex2f(400, 450);
			glVertex2f(450, 450);
			glVertex2f(450, 400);
			glEnd();

			// draw triangles : treats each triplet of vertices as an independent triangle.
			// vertices 3 ⁢ n - 2 , 3 ⁢ n - 1 , and 3 ⁢ n define triangle n. N 3 triangles are drawn.
			glColor4f(0.2f, 0.2f, 0.2f, 1.0f);
			glBegin(GL_TRIANGLES);
			// single triangle : three of vertex
			glVertex2f(400, 200);
			glVertex2f(450, 250);
			glVertex2f(500, 200);
			// single triangle : three of vertex
			glVertex2f(400, 300);
			glVertex2f(450, 350);
			glVertex2f(500, 300);
			glEnd();

			// draw triangles : draws a connected group of triangles. one triangle is defined for each vertex presented after the first two vertices.
			// for odd n, vertices n, n + 1 , and n + 2 define triangle n. for even n, vertices n + 1 , n, and n + 2 define triangle n.
			// n - 2 triangles are drawn.
			glColor4f(0.2f, 0.2f, 0.2f, 1.0f);
			glBegin(GL_TRIANGLE_STRIP);
			// combined triangle : even n vertices
			// n
			glVertex2f(550, 200);
			// n + 1
			glVertex2f(600, 250);
			// n + 2 -> triangle : n + 1, n, n + 2
			glVertex2f(650, 200);
			// n + 3 -> triangle : n + 2, n + 1, n + 3
			glVertex2f(650, 250);
			// n + 4 -> triangle : n + 3, n + 2, n + 4
			glVertex2f(700, 300);
			// n + 5 -> triangle : n + 4, n + 3, n + 5
			glVertex2f(750, 250);
			glEnd();

			glColor4f(0.2f, 0.2f, 0.2f, 1.0f);
			glBegin(GL_TRIANGLE_STRIP);
			// combined triangle : odd n vertices
			// n
			glVertex2f(550, 50);
			// n + 1
			glVertex2f(600, 100);
			// n + 2 -> triangle : n, n + 1, n + 2
			glVertex2f(650, 50);
			// n + 3 -> triangle : n + 1, n + 2, n + 3
			glVertex2f(650, 100);
			// n + 4 -> triangle : n + 2, n + 3, n + 4
			glVertex2f(700, 150);
			glEnd();
			
			// draw triangles : draws a connected group of triangles. one triangle is defined for each vertex presented after the first two vertices. 
			// vertices 1 , n + 1 , and n + 2 define triangle n. N - 2 triangles are drawn.
			glColor4f(1.0f, 0.2f, 0.2f, 1.0f);
			glBegin(GL_TRIANGLE_FAN);
			// combined triangle : from n point
			// n
			glVertex2f(50, 250);
			// n + 1
			glVertex2f(100, 300);
			// n + 2 -> triangle : n, n + 1, n + 2
			glVertex2f(150, 250);
			// n + 3 -> triangle : n, n + 2, n + 3
			glVertex2f(150, 300);
			// n + 4 -> triangle : n, n + 3, n + 4
			glVertex2f(200, 350);
			glEnd();
			
			// draw quad : treats each group of four vertices as an independent quadrilateral. 
			// vertices 4 ⁢ n - 3 , 4 ⁢ n - 2 , 4 ⁢ n - 1 , and 4 ⁢ n define quadrilateral n. N 4 quadrilaterals are drawn.
			glColor4f(0.0f, 1.0f, 0.2f, 1.0f);
			glBegin(GL_QUADS);
			// single quad : four of vertex
			glVertex2f(650, 350);
			glVertex2f(650, 400);
			glVertex2f(700, 400);
			glVertex2f(700, 350);
			// single quad : four of vertex
			glVertex2f(550, 350);
			glVertex2f(550, 400);
			glVertex2f(600, 400);
			glVertex2f(600, 350);
			glEnd();
			
			// draw quad : draws a connected group of quadrilaterals. One quadrilateral is defined for each pair of vertices presented after the first pair. 
			// vertices 2 ⁢ n - 1 , 2 ⁢ n , 2 ⁢ n + 2 , and 2 ⁢ n + 1 define quadrilateral n. N 2 - 1 quadrilaterals are drawn Note that the order in which vertices are used to construct a quadrilateral from strip data is different from that used with independent data
			glColor4f(0.0f, 0.8f, 0.2f, 1.0f);
			// combine quad : diagonal
			glBegin(GL_QUAD_STRIP);
			// 2n - 1
			glVertex2f(350, 100);
			// 2n
			glVertex2f(350, 50);
			// 2n + 1
			glVertex2f(400, 100);
			// 2n + 2 -> quad : 2n - 1, 2n, 2n + 2, 2n + 1
			glVertex2f(400, 50);
			glColor4f(0.8f, 0.0f, 0.2f, 0.5f);
			// 2n + 3 -> quad : 2n, 2n + 1, 2n + 3, 2n + 2
			glVertex2f(500, 100);
			// 2n + 4 -> quad : 2n + 1, 2n + 2, 2n + 4, 2n + 3
			glVertex2f(500, 50);
			glEnd();
			
			
			// draw polygon : draws a single, convex polygon. vertices 1 through N define this polygon.
			glColor4f(0.0f, 0.2f, 0.8f, 1.0f);
			glBegin(GL_POLYGON);
			glVertex2f(150, 150);
			glVertex2f(200, 200);
			glVertex2f(250, 200);
			glVertex2f(200, 150);
			glEnd();
		}

		Display.destroy();
	}

}
