package noc.tutorial.system;

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

public class Particle {

	static final float density = 0.1f;

	PVector vel;
	PVector loc;
	PVector acc;
	PVector frc;

	float radius;
	float alpha;
	float mass;

	public Particle() {
		loc = new PVector();
		vel = new PVector();
		acc = new PVector();
		frc = new PVector();

		radius = .0f;
		alpha = .0f;
		mass = .0f;
	}

	public void init(float x, float y, float r) {
		loc.set(x, y);

		radius = r;
		alpha = 1.0f;

		mass = density * ((float) Math.PI * r * r);
	}

	public void update(float delta) {
		acc.set(PVector.div(frc, mass));

		vel.add(PVector.mul(acc, delta));
		vel.limit(10);

		loc.add(vel);
		frc.scl(.0f);

		alpha -= 0.01f;
	}

	public boolean isOver() {
		return alpha < 0.0f;
	}

	public void applyFrc(PVector frc_) {
		frc.add(frc_);
	}

	public void draw() {

		glPushMatrix();
		glTranslatef(loc.x, loc.y, 0);

		glColor4f(0.8f, 0.8f, 0.8f, alpha);
		glBegin(GL_TRIANGLE_FAN);
		for (int i = 0; i <= 100; i++) {
			double angle = i * ((Math.PI * 2) / 100);
			glVertex2f((float) Math.cos(angle) * radius, (float) Math.sin(angle) * radius);
		}
		glEnd();

		glColor4f(0.5f, 0.5f, 0.5f, alpha);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < 100; i++) {
			double angle = i * ((Math.PI * 2) / 100);
			glVertex2f((float) Math.cos(angle) * radius, (float) Math.sin(angle) * radius);
		}
		glEnd();

		glPopMatrix();
	}

	public void reset() {
		loc.set(.0f, .0f);
		vel.set(.0f, .0f);
		acc.set(.0f, .0f);
		frc.set(.0f, .0f);

		radius = .0f;
		alpha = .0f;
		mass = .0f;
	}

}
