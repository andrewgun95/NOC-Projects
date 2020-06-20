package project.noc.practice;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Practice1 {

	static final float density = 0.1f;

	PVector location;
	PVector velocity;
	PVector acceleration;
	PVector force;
	float mass;
	float radius;

	float alpha;

	public Practice1(float x, float y, float r) {
		// initial values given for static object
		location = new PVector(x, y);
		velocity = new PVector();
		acceleration = new PVector();
		force = new PVector();

		mass = density * ((float) Math.PI * r * r);
		radius = r;
		alpha = 1.0f;
	}

	public void applyForce(PVector f) {
		force.set(f);
	}

	public void update(float delta) {
		// update values given for dynamic object

		acceleration.set(PVector.div(force, mass));

		velocity.add(PVector.mul(acceleration, delta));
		// need to limit velocity for constraint behavior
		velocity.limit(10);

		location.add(velocity);
	}

	public void draw() {
		final int numSegment = 100;

		glPushMatrix();
		glTranslatef(location.x, location.y, .0f);

		// color almost white
		glColor4f(0.8f, 0.8f, 0.8f, alpha);
		glBegin(GL_TRIANGLE_FAN);
		for (int i = 0; i <= numSegment; i++) {
			float angle = i * ((float) (Math.PI * 2) / numSegment);
			glVertex2f((float) Math.cos(angle) * radius, (float) Math.sin(angle) * radius);
		}
		glEnd();

		// stroke
		// color grey
		glColor4f(0.5f, 0.5f, 0.5f, alpha);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i <= numSegment; i++) {
			float angle = i * ((float) (Math.PI * 2) / numSegment);
			glVertex2f((float) Math.cos(angle) * radius, (float) Math.sin(angle) * radius);
		}
		glEnd();
		glPopMatrix();

	}

}
