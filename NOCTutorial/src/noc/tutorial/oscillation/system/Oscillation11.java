package noc.tutorial.oscillation.system;

import java.awt.Color;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import project.noc.utils.PVector;

public class Oscillation11 extends Application {

	Bolb a;
	Bolb b;
	Bolb c;
	Spring spring1;
	Spring spring2;
	Spring spring3;

	@Override
	public void setup() {
		a = new Bolb(200, 240, 10);
		b = new Bolb(300, 240, 10);
		c = new Bolb(250, 320, 10);
		spring1 = new Spring(a, b);
		spring2 = new Spring(b, c);
		spring3 = new Spring(a, c);
	}

	boolean drag = false;
	PVector dragOffset = new PVector();

	@Override
	public void update(float delta) {

		PVector mouse = new PVector(Mouse.getX(), Mouse.getY());

		PVector length = PVector.sub(mouse, a.location);

		while (Mouse.next()) {

			if (Mouse.getEventButton() == 0) {
				if (Mouse.getEventButtonState()) {
					if (length.mag() < a.radius) {
						a.setColor(new Color(0.6f, 0.6f, 0.6f));
						drag = true;
						dragOffset.set(length);
					}
				} else {
					a.setColor(new Color(0.8f, 0.8f, 0.8f));
					drag = false;
				}
			}

		}

		if (drag) {
			a.location.set(PVector.sub(mouse, dragOffset));
		}

		a.update(delta);
		b.update(delta);
		c.update(delta);

		spring1.update();
		spring2.update();
		spring3.update();
	}

	@Override
	public void draw() {
		spring1.draw(5);
		spring2.draw(5);
		spring3.draw(5);

		a.draw();
		b.draw();
		c.draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation11());
	}

}
