package noc.tutorial.oscillation.spring;

import java.awt.Color;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Oscillation9 extends Application {

	Spring spring;
	Bolb bolb;

	boolean drag;
	PVector dragOffset;

	@Override
	public void setup() {
		spring = new Spring(400, 400, 100);
		bolb = new Bolb(spring, 35);

		drag = false;
		dragOffset = new PVector();
	}

	@Override
	public void update(float delta) {

		PVector mouse = new PVector(Mouse.getX(), Mouse.getY());

		PVector length = PVector.sub(mouse, bolb.location);

		while (Mouse.next()) {

			if (Mouse.getEventButton() == 0) {
				if (Mouse.getEventButtonState()) {
					if (length.mag() < bolb.radius) {
						bolb.setColor(new Color(0.6f, 0.6f, 0.6f));
						drag = true;
						dragOffset.set(length);
					}
				} else {
					bolb.setColor(new Color(0.8f, 0.8f, 0.8f));
					drag = false;
				}
			}

		}

		if (drag) {
			bolb.location.y = mouse.y - dragOffset.y;
		}

		// spring follow bolb's position
		spring.connect(bolb);

		// spring force
		// force = k * x
		final float k = 200f;
		float dlength = spring.currlength - spring.restlength;

		bolb.applyForce(new PVector(.0f, k * dlength));

		// friction
		final float e = 0.98f;
		bolb.velocity.y = e * bolb.velocity.y;

		// constraint
		bolb.location.y = bolb.location.y > 350 ? 350 : bolb.location.y;

		bolb.update(delta);
	}

	@Override
	public void draw() {
		axis();

		spring.draw(20);
		bolb.draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation9());
	}

	public void axis() {
		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_LINES);
		glVertex2f(400, 0);
		glVertex2f(400, 480);
		glEnd();
		glBegin(GL_LINES);
		glVertex2f(0, 400);
		glVertex2f(800, 400);
		glEnd();
	}

}
