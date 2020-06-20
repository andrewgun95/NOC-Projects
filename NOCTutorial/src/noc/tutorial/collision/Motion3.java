package noc.tutorial.collision;

import java.awt.Color;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Motion3 extends Application {

	static float e = 0.8f;

	Ball[] balls;

	Momentum collision;

	Rectangle rect;

	@Override
	public void setup() {
		balls = new Ball[10];

		// ball's
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				balls[y * 3 + x] = new Ball(200 + x * 50, 200 + y * 50, 20);
			}
		}

		balls[9] = new Ball(600, 100, 25);
		balls[9].setColor(new Color(0.6f, 0.6f, 0.6f));

		collision = new Momentum(balls, e);

		rect = new Rectangle(100, 100, 400, 400);
	}

	public void update(float delta) {

		PVector gravity = new PVector(0, 0);

		for (int i = 0; i < balls.length; i++) {
			Ball ball = balls[i];
			PVector weight = PVector.mul(gravity, ball.mass);
			ball.applyForce(weight);

			// friction
			ball.vel.scl(0.99f);

			// change color
			if (i != balls.length - 1) {
				if (ball.loc.x < rect.x1 || ball.loc.x > rect.x2) {
					ball.setColor(new Color(0.6f, 0.6f, 0.6f));
				}
				if (ball.loc.y < rect.y1 || ball.loc.y > rect.y2) {
					ball.setColor(new Color(0.6f, 0.6f, 0.6f));
				}
			}

			ball.update(delta);
		}

		if (balls[9].vel.mag() < 0.2f) {
			// control ball's player
			PVector mouse = new PVector(Mouse.getX(), Mouse.getY());
			PVector direction = PVector.sub(mouse, balls[9].loc);

			// debug force
			glColor3f(0.5f, 0.5f, 0.5f);
			glBegin(GL_LINES);
			glVertex2f(balls[9].loc.x, balls[9].loc.y);
			glVertex2f(balls[9].loc.x + direction.nor().scl(100).x, balls[9].loc.y + direction.nor().scl(100).y);
			glEnd();

			while (Mouse.next()) {

				if (Mouse.getEventButtonState()) {
					if (Mouse.getEventButton() == 0) {

						PVector force = PVector.sub(mouse, balls[9].loc);
						force.norm();
						force.scl(10000);

						balls[9].applyForce(force);
					}
				}

			}
		}

		collision.update(delta);
	}

	@Override
	public void draw() {
		for (Ball ball : balls) {
			ball.display();
		}

		// draw bound
		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_LOOP);
		glVertex2f(rect.x1, rect.y1);
		glVertex2f(rect.x2, rect.y1);
		glVertex2f(rect.x2, rect.y2);
		glVertex2f(rect.x1, rect.y2);
		glEnd();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Motion3());
	}

	class Rectangle {

		float x1, y1, x2, y2;

		public Rectangle(float x1_, float y1_, float x2_, float y2_) {
			x1 = x1_;
			y1 = y1_;
			x2 = x2_;
			y2 = y2_;
		}
	}

}
