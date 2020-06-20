package noc.tutorial.oscillation.attractor;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMath;
import project.noc.utils.PVector;

public class Attraction {

	static final int numSegments = 100;
	static final float density = 1f;

	PVector location;
	double radius;
	float mass;

	public Attraction(float x, float y, double r) {
		location = new PVector(x, y);
		radius = r;
		mass = density * (float) (Math.PI * radius * radius);
	}

	public PVector attract(Mover mover) {

		PVector gforce = PVector.sub(location, mover.location);
		gforce.norm();

		float gc = 1;
		float m1 = mass;
		float m2 = mover.mass;
		float d = PVector.sub(location, mover.location).mag();

		d = PMath.constrain(d, 5, 25);

		gforce.scl((gc * m1 * m2) / (d * d));

		return gforce;
	}

	public void display() {

		glColor4f(0.6f, 0.6f, 0.6f, 1.0f);
		glBegin(GL_TRIANGLE_FAN);
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
