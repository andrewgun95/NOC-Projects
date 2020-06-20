package noc.tutorial.oscillation.invertedpendulum;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Cart {

	static final float density = 0.01f;

	public PVector loc;
	public float vel;
	public float acc;
	public float frc;

	public float mass;
	public float width, height;

	Cart(float x, float y, float w, float h) {
		loc = new PVector(x, y);
		vel = .0f;
		acc = .0f;
		frc = .0f;

		width = w;
		height = h;

		mass = density * (w * h);
	}

	PVector pivot() {
		return loc;
	}

	void applyAcc(float a) {
		frc = mass * a;
	}

	void applyForce(float f) {
		frc += f;
	}

	void update(float delta) {
		// acceleration = force / mass
		acc = frc / mass;
		// velocity = velocity + acceleration * time;
		vel += acc * delta;
		if (vel > 10) {
			vel = 10;
		}
		// location = location + velocity
		loc.set(loc.x + vel, loc.y);

		// damping
		float e = 0.9f;
		vel *= e;
		
		// restoring force
		frc = 0;

		// collision detection
		if (loc.x < 0) {
			loc.x = 800;
		}
		if (loc.x > 800) {
			loc.x = 0;
		}
	}

	void draw() {

		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_QUADS);
		glVertex2f(loc.x - width / 2, loc.y - height / 2);
		glVertex2f(loc.x + width / 2, loc.y - height / 2);
		glVertex2f(loc.x + width / 2, loc.y + height / 2);
		glVertex2f(loc.x - width / 2, loc.y + height / 2);
		glEnd();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_LOOP);
		glVertex2f(loc.x - width / 2, loc.y - height / 2);
		glVertex2f(loc.x + width / 2, loc.y - height / 2);
		glVertex2f(loc.x + width / 2, loc.y + height / 2);
		glVertex2f(loc.x - width / 2, loc.y + height / 2);
		glEnd();

	}

}
