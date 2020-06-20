package noc.tutorial.extra;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Mover {

	static final int numSegments = 100;

	static final float density = 0.001f;

	PVector location;
	PVector velocity;
	PVector acceleration;
	PVector force;

	double radius;

	public Mover(float x, float y, double r) {
		location = new PVector(x, y);

		velocity = new PVector();
		acceleration = new PVector();
		force = new PVector();

		// one pixel equals one meter
		radius = r;
	}

	public void update(float delta) {

		// newton's second law : acceleration = force / mass
		acceleration.set(PVector.div(force, mass()));

		velocity.add(PVector.mul(acceleration, delta));
		velocity.limit(10);

		location.add(velocity);

		// reset force
		force.scl(0);
	}

	public void applyForce(PVector f) {
		force.add(f);
	}

	public void applyMomentum(PVector m) {
		// velocity = momentum / mass
		velocity.add(PVector.div(m, mass()));
	}

	public void display() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glColor4f(0.7f, 0.7f, 0.7f, 0.5f);
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(location.x, location.y);
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f(location.x + (float) (Math.cos(angle) * radius), location.y + (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f(location.x + (float) (Math.cos(angle) * radius), location.y + (float) (Math.sin(angle) * radius));
		}
		glEnd();
	}

	public float mass() {
		// calculate mass : density * volume (of tube with 1 pixel height)
		return density * (float) (Math.PI * radius * radius);
	}

}
