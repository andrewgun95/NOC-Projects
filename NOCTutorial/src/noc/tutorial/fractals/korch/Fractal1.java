package noc.tutorial.fractals.korch;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import project.noc.utils.PVector;

public class Fractal1 extends Application {

	Korch korch;
	float t = .0f;
	int n = 0;

	@Override
	public void setup() {
		korch = new Korch(new PVector(0, 10), new PVector(800, 10));
	}

	@Override
	public void update(float delta) {

		if ((t += delta) > 0.2f) {
			if (n < 10) {
				korch.generate();
				n++;
			}
			t = .0f;
		}

	}

	@Override
	public void draw() {

		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) {
				if (Mouse.getEventButton() == 0) {
					if (n < 10) {
						korch.generate();
						n++;
					}
				}
			}
		}

		korch.draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Fractal1());
	}

}
