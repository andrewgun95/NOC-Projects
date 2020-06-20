package noc.tutorial.oscillation;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Oscillation3 extends Application {

	static final float numSegments = 100;
	static final float radius = 25;

	float x, y;
	Wave wave;

	@Override
	public void setup() {
		x = 400;
		y = 240;
		wave = new Wave(400);
	}

	@Override
	public void draw() {

		float amount = wave.value(1, 2);

		float movement = x + amount;

		glPushMatrix();

		glTranslatef(movement, y, 0);
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_TRIANGLE_FAN);
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();
		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glPopMatrix();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINES);
		glVertex2f(400, 240);
		glVertex2f(movement, y);
		glEnd();

	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation3());
	}

	class Wave {

		float amplitude, angle;
		double counter;

		public Wave(float amplitude) {
			this.amplitude = amplitude;
			counter = 0;
			angle = 0.0f;
		}

		public float value() {
			counter++;
			// n = counter / 100
			// amplitude * sin(2 * pi * n)

			return amplitude * (float) Math.sin(2.0 * Math.PI * (counter / 200.0));
		}

		public float value(float frequency, float period) {
			angle += (0.05f * frequency / period);

			return amplitude * (float) Math.sin(angle);
		}

	}
}
