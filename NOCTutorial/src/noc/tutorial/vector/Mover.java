package noc.tutorial.vector;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Mover {

	static final int numSegments = 100;
	static final double radius = 25;

	PVector location;
	PVector velocity;
	PVector acceleration;

	public Mover() {
		location = new PVector(400, 240);
		// zero velocity : will changed by acceleration
		velocity = new PVector();

		acceleration = new PVector(2f, 0.0f);
	}

	public void update(float delta) {
		// acceleration = PVector.rand(5);

		// physics syntax : new velocity = velocity + acceleration * delta time;
		velocity.add(PVector.mul(acceleration, delta));
		velocity.limit(20);

		location.add(velocity);
	}

	public void display() {
		// circle
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(location.x, location.y);
		// numSegments + 1 back to 0 degree
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f(location.x + (float) (Math.cos(angle) * radius), location.y + (float) (Math.sin(angle) * radius));
		}
		glEnd();
		// lines
		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f(location.x + (float) (Math.cos(angle) * radius), location.y + (float) (Math.sin(angle) * radius));
		}
		glEnd();
	}

}
