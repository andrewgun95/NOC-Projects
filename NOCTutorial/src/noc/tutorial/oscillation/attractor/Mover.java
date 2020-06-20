package noc.tutorial.oscillation.attractor;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMath;
import project.noc.utils.PMatrix;
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

	Oscillator oscillator;
	PVector oscillate;

	public Mover(float x, float y, double r, float speed) {
		location = new PVector(x, y);
		velocity = new PVector(speed, 0);
		acceleration = new PVector();
		force = new PVector();

		radius = r;
		mass = density * (float) (Math.PI * radius * radius);

		oscillator = new Oscillator(10);
		oscillate = new PVector();
	}

	public void update(float delta) {
		acceleration.set(PVector.div(force, mass));

		velocity.add(PVector.mul(acceleration, delta));
		velocity.limit(10);

		location.add(velocity);

		force.scl(0);
		
		// oscillate
		float amount = oscillator.oscillate(velocity.mag());		
		amount = PMath.map(amount, -10, 10, 0, 20);
		
		float angle = PVector.angle(velocity);
		
		oscillate = PMatrix.rotate2D(angle, new PVector(amount, 0));
	}

	public void applyForce(PVector f) {
		force.add(f);
	}

	public void display() {
		
		
		glPushMatrix();
		glTranslatef(location.x, location.y, 0.0f);

		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(0.0f, 0.0f);
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * ((Math.PI * 2) / numSegments);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * ((Math.PI * 2) / numSegments);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glPopMatrix();
		
		drawCrawler(location.x + oscillate.x, location.y + oscillate.y);
	}
	
	public void drawCrawler(float x, float y){
		glColor4f(0.5f, 0.5f, 0.5f, 0.5f);
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(x, y);
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * ((Math.PI * 2) / numSegments);
			glVertex2f(x + (float) (Math.cos(angle) * radius / 2), y + (float) (Math.sin(angle) * radius / 2));
		}
		glEnd();
		
		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * ((Math.PI * 2) / numSegments);
			glVertex2f(x + (float) (Math.cos(angle) * radius / 2), y + (float) (Math.sin(angle) * radius / 2));
		}
		glEnd();
		
		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINES);
		glVertex2f(location.x, location.y);
		glVertex2f(x, y);
		glEnd();
	}

}
