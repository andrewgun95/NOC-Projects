package noc.tutorial.oscillation.system;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Bolb {

	static final float density = 0.1f;

	public PVector location;
	public PVector velocity;
	public PVector acceleration;
	public PVector force;

	public float radius, mass;

	public Color color;

	public Bolb(float x, float y, float radius_) {
		radius = radius_;

		location = new PVector(x, y);
		velocity = new PVector();
		acceleration = new PVector();
		force = new PVector();

		mass = density * ((float) Math.PI * radius * radius);
		
		color = new Color(0.8f, 0.8f, 0.8f);
	}

	public void update(float delta) {

		acceleration.set(PVector.div(force, mass));

		velocity.add(PVector.mul(acceleration, delta));
		velocity.limit(10);

		location.add(velocity);

		force.scl(0);
	}

	public void applyForce(PVector f) {
		force.add(f);
	}

	public void draw() {
		glPushMatrix();
		glTranslatef(location.x, location.y, .0f);

		glColor3f((float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f);
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(.0f, .0f);
		for (int i = 0; i <= 100; i++) {
			double angle = i * ((Math.PI * 2) / 100);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < 100; i++) {
			double angle = i * ((Math.PI * 2) / 100);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glPopMatrix();
	}

	public void setColor(Color color_) {
		color = color_;
	}

}
