package noc.tutorial.oscillation;

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

import project.noc.utils.PVector;

public class Pendulum {

	PVector location;
	float length;

	float aVel;
	float aAcc;
	float angle;

	public Pendulum(float x, float y, float length_) {
		location = new PVector(x, y);
		length = length_;
		aVel = .0f;
		aAcc = .0f;
		angle = .0f;
	}

	public void update(float delta) {

		// force gravity = G * m1 * m2 / r ^ 2
		// restore force = force gravity * sin (angle)
		// a = force tangent / mass
		// a = -9.81 * sin (angle) / r

		aAcc = (-9.81f * (float) Math.sin(Math.toRadians(angle)) / (length * 0.01f));

		aVel += (aAcc * delta);
		angle += aVel;

		// friction of damping = e * velocity;
		final float e = 0.981f;
		aVel = e * aVel;
	}

	public void display() {

		glPushMatrix();
		glTranslatef(location.x, location.y, 0);

		float cir_x = (float) Math.sin(Math.toRadians(angle)) * length;
		float cir_y = -(float) Math.cos(Math.toRadians(angle)) * length;
		circle(cir_x, cir_y, 25);

		glBegin(GL_LINES);
		glVertex2f(.0f, .0f);
		glVertex2f(cir_x, cir_y);
		glEnd();

		glPopMatrix();

	}

	public void circle(float x, float y, float radius) {
		final int numSegments = 100;

		glColor4f(0.8f, 0.8f, 0.8f, 0.5f);
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(x, y);
		for (int i = 0; i <= numSegments; i++) {
			double angle = i * ((Math.PI * 2) / numSegments);
			glVertex2f(x + (float) (Math.cos(angle) * radius), y + (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * ((Math.PI * 2) / numSegments);
			glVertex2f(x + (float) (Math.cos(angle) * radius), y + (float) (Math.sin(angle) * radius));
		}
		glEnd();
	}

}
