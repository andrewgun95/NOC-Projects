package noc.tutorial.distribution;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMath;

public class Costumize extends Application {

	private int[] distribution;
	private int n = 100;

	@Override
	public void setup() {
		distribution = new int[n];
	}

	@Override
	public void draw() {

		int index = (int) PMath.map(montecarlo(), 0, 1, 0, n);

		distribution[index]++;

		float recWidth = 800 / distribution.length;
		float recHeight = 0;

		for (int i = 0; i < distribution.length; i++) {

			glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
			glRectf(i * recWidth, 10, i * recWidth + recWidth, 10 + (recHeight + distribution[i]));

			glLineWidth(2.0f);
			glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
			glBegin(GL_LINE_STRIP);
			glVertex2d(i * recWidth, 10);
			glVertex2f(i * recWidth + recWidth, 10);
			glVertex2f(i * recWidth + recWidth, 10 + (recHeight + distribution[i]));
			glVertex2f(i * recWidth, 10 + (recHeight + distribution[i]));
			glEnd();
		}

	}

	// monte carlo random
	float montecarlo() {
		while (true) {

			float r1 = (float) Math.random();

			float probability = r1;

			float r2 = (float) Math.random();

			if (r2 < probability) return r1;
		}
	}

	float montecarlo1() {
		while (true) {
			float r1 = (float) Math.random();

			float probability = r1;

			float r2 = (float) Math.random();

			if (r2 > probability) return r1;
		}
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Costumize());
	}

}
