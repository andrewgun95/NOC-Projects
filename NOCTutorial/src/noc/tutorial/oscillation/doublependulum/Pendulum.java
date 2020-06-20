package noc.tutorial.oscillation.doublependulum;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Pendulum {

	final float density = 1000f;

	float length;
	float radius;
	float angle;
	float mass;

	float x, y;

	Pendulum(float length, float radius) {
		this(length, radius, 90);
	}

	Pendulum(float length, float radius, float angle) {
		this.length = length;
		this.radius = radius;
		this.angle = (float) (Math.toRadians(angle));

		mass = density / (float) (Math.PI) * radius * radius;
	}

	void draw(float origin_x, float origin_y) {
		// draw line
		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_LINES);
		glVertex2f(origin_x, origin_y);
		glVertex2f(x, y);
		glEnd();

		circle(x, y, radius);
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