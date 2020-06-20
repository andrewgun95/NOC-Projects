package noc.tutorial.force.gravityattraction;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import project.noc.utils.PVector;

public class Force4_Practice1 extends Application {

	Attraction centeroid;

	Mover[] mover;

	@Override
	public void setup() {
		centeroid = new Attraction(400, 240, 50);

		mover = new Mover[20];
		for (int i = 0; i < mover.length; i++) {
			float randX = 50f + (float) (Math.random() * 700);
			float randY = 50f + (float) (Math.random() * 400);
			mover[i] = new Mover(randX, randY, 5 + (float) (Math.random() * 20), (float) (Math.random() * 5));
		}
	}

	@Override
	public void update(float delta) {
		for (int i = 0; i < mover.length; i++) {
			Mover m = mover[i];

			PVector cforce = centeroid.attract(m);
			m.applyForce(cforce);

			m.update(delta);
		}
	}

	@Override
	public void draw() {
		centeroid.display();

		for (Mover m : mover) {
			m.display();
		}
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Force4_Practice1());
	}

}
