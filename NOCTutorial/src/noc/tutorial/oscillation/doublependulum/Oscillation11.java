package noc.tutorial.oscillation.doublependulum;

import java.util.ArrayList;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Oscillation11 extends Application {

	DoublePendulum pendulum;

	@Override
	public void setup() {
		Pendulum a = new Pendulum(200, 25);
		Pendulum b = new Pendulum(100, 25, 90);

		pendulum = new DoublePendulum(400, 400, a, b);
	}

	@Override
	public void update(float delta) {
		pendulum.update(delta);
	}

	@Override
	public void draw() {
		glColor3f(0.5f, 0.5f, 0.5f);
		glLineWidth(1.0f);
		glBegin(GL_LINES);
		glVertex2f(0, 400);
		glVertex2f(800, 400);
		glEnd();

		pendulum.draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation11());
	}

	class DoublePendulum {

		float x, y;

		Pendulum a;
		float aVel;
		Pendulum b;
		float bVel;

		public DoublePendulum(float x, float y, Pendulum a, Pendulum b) {
			this.x = x;
			this.y = y;
			this.a = a;
			this.b = b;
		}

		public void update(float delta) {
			a.x = x + (float) (Math.sin(a.angle) * a.length);
			a.y = y - (float) (Math.cos(a.angle) * a.length);

			b.x = a.x + (float) (Math.sin(b.angle) * b.length);
			b.y = a.y - (float) (Math.cos(b.angle) * b.length);

			aVel += acc_a();
			if (aVel > 0.5f) aVel = 0.5f;
			a.angle += aVel;
			aVel *= 0.99999f;
			bVel += acc_b();
			if (bVel > 0.5f) bVel = 0.5f;
			b.angle += bVel;

			trajectory.add(new Vector2f(b.x, b.y));
		}

		float acc_a() {

			// gravity
			final float g = 0.1f;

			float n1 = g * (float) (Math.sin(b.angle) * Math.cos(a.angle - b.angle) - mu() * Math.sin(a.angle));
			float n2 = (float) ((b.length * bVel * bVel + a.length * aVel * aVel * Math.cos(a.angle - b.angle)) * Math.sin(a.angle - b.angle));
			float d = (float) (a.length * (mu() - Math.cos(a.angle - b.angle) * Math.cos(a.angle - b.angle)));

			return (n1 - n2) / d;
		}

		float acc_b() {

			// gravity
			final float g = 0.1f;

			float n1 = g * mu() * (float) (Math.sin(a.angle) * Math.cos(a.angle - b.angle) - Math.sin(b.angle));
			float n2 = (float) ((mu() * a.length * aVel * aVel + b.length * bVel * bVel * Math.cos(a.angle - b.angle)) * Math.sin(a.angle - b.angle));
			float d = (float) (b.length * (mu() - Math.cos(a.angle - b.angle) * Math.cos(a.angle - b.angle)));

			return (n1 + n2) / d;
		}

		float mu() {
			return 1 + (a.mass / b.mass);
		}

		ArrayList<Vector2f> trajectory = new ArrayList<>();

		public void draw() {
			glLineWidth(1.0f);
			a.draw(x, y);
			b.draw(a.x, a.y);

			glColor3f(0.8f, 0.8f, 1.0f);
			glLineWidth(2.0f);
			glBegin(GL_LINE_STRIP);
			for (Vector2f point : trajectory) {
				glVertex2f(point.x, point.y);
			}
			glEnd();

			if (trajectory.size() == 100) trajectory.remove(0);
		}

	}

}
