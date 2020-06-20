package noc.tutorial.collision;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Wheel {

	static final int numSegments = 100;
	static final float density = 0.01f;

	PVector location;
	PVector velocity;
	PVector acceleration;
	PVector force;

	double radius;
	float mass;

	// in degree
	float angularvel;
	float angle;

	public Wheel(float x, float y, double r) {
		location = new PVector(x, y);
		velocity = new PVector();
		acceleration = new PVector();
		force = new PVector();

		radius = r;
		// mass : density * volume
		mass = density * (float) (Math.PI * radius * radius);

		angle = 0.0f;
	}

	public void update(float delta) {
		// LINEAR MOTION
		// newton's second law : acceleration = force / mass
		acceleration.set(PVector.div(force, mass));

		// physics syntax : new velocity = velocity + acceleration * delta time;
		velocity.add(PVector.mul(acceleration, delta));
		velocity.limit(10);

		location.add(velocity);

		// clear force
		force.scl(0);

		// ANGULAR MOTION
		// physics syntax : w = v (in x direction because linear motion only occur on x direction) / r
		angularvel = velocity.x / (float) radius;

		// rotate clock-wise
		angle -= (float) (Math.toDegrees(angularvel));
	}

	public void applyForce(PVector f) {
		force.add(f);
	}

	public void display() {
		glPushMatrix();
		glTranslatef(location.x, location.y, 0.0f);
		glRotatef(angle, 0, 0, 1);

		// circle
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_TRIANGLE_FAN);
		// numSegments + 1 back to 0 degree
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();
		// lines
		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINE_LOOP);
		// line for sign
		glVertex2f(0.0f, 0.0f);
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glPopMatrix();
	}
}
