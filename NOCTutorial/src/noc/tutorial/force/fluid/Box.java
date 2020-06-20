package noc.tutorial.force.fluid;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Box {

	PVector location;
	PVector velocity;
	PVector acceleration;
	PVector force;

	float side;
	float density;

	public Box(float x, float y, float s, float d) {
		location = new PVector(x, y);
		velocity = new PVector();
		acceleration = new PVector();
		force = new PVector();

		side = s;
		density = d;
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

	public void display() {

		glColor4f(0.7f, 0.7f, 0.7f, 0.7f);
		glBegin(GL_QUADS);
		glVertex2f(location.x - side / 2, location.y - side / 2);
		glVertex2f(location.x + side / 2, location.y - side / 2);
		glVertex2f(location.x + side / 2, location.y + side / 2);
		glVertex2f(location.x - side / 2, location.y + side / 2);
		glEnd();

		glColor4f(0.3f, 0.3f, 0.3f, 1.0f);
		glBegin(GL_LINE_LOOP);
		glVertex2f(location.x - side / 2, location.y - side / 2);
		glVertex2f(location.x + side / 2, location.y - side / 2);
		glVertex2f(location.x + side / 2, location.y + side / 2);
		glVertex2f(location.x - side / 2, location.y + side / 2);
		glEnd();
	}

	public void applyForce(PVector f) {
		force.add(f);
	}

	public float mass() {
		// mass = density * volume (in one pixel width)
		return density * (side * side);
	}

}
