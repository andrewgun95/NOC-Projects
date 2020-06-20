package noc.tutorial.fractals;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glVertexPointer;

public class RecursiveCircle extends Application {

	@Override
	public void setup() {

	}

	/**
	 * with recursion
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 */
	public void drawRCircle(float x, float y, float radius) {
		glColor3f(0.8f, 0.8f, 0.8f);
		circle(GL_LINE_LOOP, x, y, radius);

		if (radius > 1) drawRCircle(x, y, radius / 2.0f);
	}

	/**
	 * without recursion
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 */
	public void drawCircle(float x, float y, float radius) {

		float r = radius;

		while (r > 1) {
			circle(GL_LINE_LOOP, x, y, r);
			r = r / 2.0f;
		}
	}

	public void cloud(float x, float y, float radius) {

		glColor3f(0.8f, 0.8f, 0.8f);
		circle(GL_LINE_LOOP, x, y + radius, radius);

		if (radius > 1) {
			cloud(x + radius, y, radius / 2);
			// call more than one, make fractal more elegant
			cloud(x - radius, y, radius / 2);
		}

	}

	public void sierpinski(float x, float y, float radius) {

		glColor3f(0.8f, 0.8f, 0.8f);
		circle(GL_LINE_LOOP, x, y, radius);

		if (radius > 1) {
			sierpinski(x + radius, y, radius / 2);
			// call more than one, make fractal more elegant
			sierpinski(x - radius, y, radius / 2);
			sierpinski(x, y + radius, radius / 2);
		}

	}

	public void batik(float x, float y, float radius) {

		glColor3f(0.8f, 0.8f, 0.8f);
		circle(GL_LINE_LOOP, x, y, radius);

		if (radius > 1) {
			batik(x + radius, y, radius / 2);
			// call more than one, make fractal more elegant
			batik(x - radius, y, radius / 2);
			batik(x, y + radius, radius / 2);
			batik(x, y - radius, radius / 2);
		}

	}

	@Override
	public void draw() {

		// drawRCircle(400, 240, 100);
		cloud(400, 240, 100);
		// sierpinski(400, 240, 100);
		// batik(400, 240, 100);

	}

	public void circle(int mode, float x, float y, float radius) {

		final int numSegment = 100;

		FloatBuffer vertices = BufferUtils.createFloatBuffer(numSegment * 2);

		for (int i = 0; i < numSegment; i++) {
			double angle = i * (Math.PI * 2 / numSegment);
			vertices.put(x + (float) (Math.cos(angle) * radius));
			vertices.put(y + (float) (Math.sin(angle) * radius));
		}
		vertices.flip();

		glEnableClientState(GL_VERTEX_ARRAY);
		glVertexPointer(2, 0, vertices);
		glDrawArrays(mode, 0, numSegment);
		glEnableClientState(GL_VERTEX_ARRAY);
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new RecursiveCircle());
	}

}
