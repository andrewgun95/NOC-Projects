package noc.tutorial.force.gravityattraction;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMath;
import project.noc.utils.PVector;

public class Mover {

	static final int numSegments = 100;
	static final float density = 0.1f;

	PVector location;
	PVector velocity;
	PVector acceleration;
	PVector force;

	double radius;
	float mass;

	public Mover(float x, float y, double r, float speed) {
		location = new PVector(x, y);
		velocity = new PVector(speed, 0);
		acceleration = new PVector();
		force = new PVector();

		radius = r;
		// mass = density * volume (area * 1 pixel height)
		mass = density * (float) (Math.PI * radius * radius);
	}

	public void update(float delta) {
		// newton's second law : acceleration = force / mass
		acceleration.set(PVector.div(force, mass));

		// syntax : new velocity = velocity + acceleration * time
		velocity.add(PVector.mul(acceleration, delta));
		velocity.limit(10);

		location.add(velocity);

		// reset force
		force.scl(0);
	}

	public PVector attract(Mover mover, float min, float max) {

		// calculate direction = unit vector r
		PVector gforce = PVector.sub(location, mover.location);
		gforce.norm();

		// calculate magnitude
		float gc = 1;
		float m1 = mass;
		float m2 = mover.mass;
		float d = PVector.sub(location, mover.location).mag();

		// constrain for acceptable value
		d = PMath.constrain(d, min, max);

		gforce.scl((gc * m1 * m2) / (d * d));

		return gforce;
	}

	public void applyForce(PVector f) {
		force.add(f);
	}

	public void display() {
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_TRIANGLE_FAN);
		// center position
		glVertex2f(location.x, location.y);
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * ((Math.PI * 2) / numSegments);
			glVertex2f(location.x + (float) (Math.cos(angle) * radius), location.y + (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * ((Math.PI * 2) / numSegments);
			glVertex2f(location.x + (float) (Math.cos(angle) * radius), location.y + (float) (Math.sin(angle) * radius));
		}
		glEnd();

	}

}
