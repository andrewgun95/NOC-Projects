package noc.tutorial.slider;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMath;
import project.noc.utils.PVector;

public class Ball {

	final int numSegments = 100;
	final float density = 0.01f;
	final float restitution = 0.5f;

	PVector location;
	PVector velocity;
	PVector acceleration;
	PVector force;

	float angle;
	float a_acc;
	float a_vel;
	float torque;

	float radius;
	float mass;
	float inertia;

	public Ball(float x, float y, float r) {
		location = new PVector(x, y);
		velocity = new PVector();
		acceleration = new PVector();
		force = new PVector();

		radius = r;
		mass = density * (float) (Math.PI * radius * radius);
		// small inertia
		inertia = 0.4f * mass * radius;

		a_acc = 0.0f;
		a_vel = 0.0f;
		torque = 0.0f;
		angle = 0.0f;
	}

	public void update(float delta) {

		// linear motion
		acceleration.set(PVector.div(force, mass));

		velocity.add(PVector.mul(acceleration, delta));
		velocity.limit(10);

		location.add(velocity);

		force.scl(0);

		// motion
		if ((int) velocity.x == 0) {
			a_vel = 0;
		}

		// angular motion
		a_acc = torque / inertia;
		a_vel += a_acc * delta;
		a_vel = PMath.constrain(a_vel, -10, 0);
		angle += a_vel;
	}

	public void applyForce(PVector f) {
		force.add(f);
	}

	public void applyTorque(float t) {
		torque = t;
	}

	public void display() {
		glPushMatrix();
		glTranslatef(location.x, location.y, 0.0f);
		glRotatef(angle, 0, 0, 1);

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

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINES);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(radius, 0.0f);
		glEnd();

		glPopMatrix();
	}
}
