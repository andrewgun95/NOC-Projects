package noc.tutorial.collision;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Ball {

	static final int numSegments = 100;
	static final float density = 0.01f;

	PVector loc;
	PVector vel;
	PVector acc;
	// force
	PVector frc;

	double radius;
	float mass;

	Color color;

	public Ball(float x, float y, double r) {
		loc = new PVector(x, y);
		vel = new PVector();
		acc = new PVector();
		frc = new PVector();

		radius = r;

		// mass = density * volume (circle * 1 pixel high)
		mass = density * (float) (Math.PI * r * r);

		color = new Color(0.8f, 0.8f, 0.8f);
	}

	public void update(float time) {
		acc.set(PVector.div(frc, mass));

		vel.add(PVector.mul(acc, time));
		vel.limit(10);

		loc.add(vel);

		// clear force
		frc.scl(0);
	}

	public void display() {
		glPushMatrix();
		glTranslatef(loc.x, loc.y, 0.0f);
		glRotatef(PVector.angle(vel), 0, 0, 1);

		// circle
		glColor3f((float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f);
		glBegin(GL_TRIANGLE_FAN);
		// numSegments + 1 back to 0 degree
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();
		// lines
		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glPopMatrix();
	}

	public void applyForce(PVector force) {
		// calculate net force
		frc.add(force);
	}

	public void setColor(Color color_) {
		color = color_;
	}

}
