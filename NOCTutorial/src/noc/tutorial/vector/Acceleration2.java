package noc.tutorial.vector;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Acceleration2 extends Application {

	Mover mover;

	@Override
	public void setup() {
		mover = new Mover();
		frame(60);
	}

	@Override
	public void update(float delta) {
		mover.update(delta);

		edges();
	}

	private void edges() {
		// check edges
		if (mover.location.x < 0) mover.velocity.x = -mover.velocity.x;
		if (mover.location.x > 800) mover.velocity.x = -mover.velocity.x;
		if (mover.location.y < 0) mover.velocity.y = -mover.velocity.y;
		if (mover.location.y > 480) mover.velocity.y = -mover.velocity.y;
	}

	@Override
	public void draw() {
		mover.display();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Acceleration2());
	}

	class Mover {

		static final int numSegments = 100;
		static final double radius = 15;

		PVector location;
		PVector velocity;
		PVector acceleration;

		public Mover() {
			location = new PVector(400, 240);
			// zero velocity changed by acceleration
			velocity = new PVector();

			acceleration = new PVector();
		}

		public void update(float delta) {
			PVector point = new PVector(Mouse.getX(), Mouse.getY());
			point.sub(location);

			point.setMag(5f);

			acceleration.set(point.x, point.y);

			velocity.add(PVector.mul(acceleration, delta));
			location.add(velocity);

			velocity.limit(5);
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

}
