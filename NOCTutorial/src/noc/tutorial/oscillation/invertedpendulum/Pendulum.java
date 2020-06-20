package noc.tutorial.oscillation.invertedpendulum;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Pendulum {

	static final float density = 0.01f;

	float length;
	float radius;
	float angle;

	float x, y;

	float vel;
	float acc;

	float mass;

	Pendulum(float length, float radius) {
		this(length, radius, 0);
	}

	/**
	 * create pendulum
	 * 
	 * @param length
	 * @param radius
	 * @param angle
	 *            in degree, counter clock-wise
	 */
	Pendulum(float length, float radius, float angle) {
		this.length = length;
		this.radius = radius;
		this.angle = (float) (Math.toRadians(angle));

		mass = density * (float) (Math.PI * radius * radius);
	}

	void pivotTo(float pivot_x, float pivot_y) {
		x = pivot_x;
		y = pivot_y;
	}

	void applyAcc(float a) {
		acc = a;
	}

	void update(float delta) {
		// velocity = velocity + acceleration * time;
		vel += (acc * delta);
		if (vel > 0.5f) {
			vel = 0.5f;
		}
		// angle = angle + velocity
		angle += vel;

		// friction
		float e = 0.981f;
		vel *= e;
		
		// restore acceleration
		acc = 0;
	}

	void draw() {
		float dx = (float) (Math.cos(angle + Math.toRadians(90)) * length);
		float dy = (float) (Math.sin(angle + Math.toRadians(90)) * length);

		// draw line
		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINES);
		glVertex2f(x, y);
		glVertex2f(x + dx, y + dy);
		glEnd();

		circle(x + dx, y + dy, radius);
	}

	void circle(float x, float y, float radius) {
		final int numSegments = 100;

		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(x, y);
		for (int i = 0; i < numSegments + 1; i++) {
			float angle = i * (float) (Math.PI * 2 / numSegments);
			glVertex2f(x + (float) (Math.cos(angle) * radius), y + (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_STRIP);
		for (int i = 0; i < numSegments + 1; i++) {
			float angle = i * (float) (Math.PI * 2 / numSegments);
			glVertex2f(x + (float) (Math.cos(angle) * radius), y + (float) (Math.sin(angle) * radius));
		}
		glEnd();
	}

}
