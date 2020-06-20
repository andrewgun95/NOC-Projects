package noc.tutorial.force.gravityattraction;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import project.noc.utils.PVector;

public class Force4_Practice2 extends Application {

	Mover[] mover;

	@Override
	public void setup() {

		mover = new Mover[50];
		for (int i = 0; i < mover.length; i++) {
			float randX = 50f + (float) (Math.random() * 700);
			float randY = 50f + (float) (Math.random() * 400);
			mover[i] = new Mover(randX, randY, 5 + (float) (Math.random() * 20), -1.0f + (float) (Math.random() * 2.0f));
		}
	}

	@Override
	public void update(float delta) {
		for (int i = 0; i < mover.length; i++) {
			// attraction
			Mover m1 = mover[i];

			for (int j = 0; j < mover.length; j++) {
				if (j != i) {
					Mover m2 = mover[j];
					PVector m1force = m1.attract(m2, 5, 25);

					m2.applyForce(m1force);
				}
			}

			m1.update(delta);

			if (m1.location.x < 0) m1.location.x = 800;
			if (m1.location.x > 800) m1.location.x = 0;
			if (m1.location.y < 0) m1.location.y = 480;
			if (m1.location.y > 480) m1.location.y = 0;
		}
	}

	@Override
	public void draw() {
		for (Mover m : mover) {
			m.display();
		}
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Force4_Practice2());
	}

}
