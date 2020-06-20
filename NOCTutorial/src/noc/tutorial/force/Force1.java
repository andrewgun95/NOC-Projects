package noc.tutorial.force;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import project.noc.utils.PMath;
import project.noc.utils.PVector;

public class Force1 extends Application {

	Mover[] movers;

	@Override
	public void setup() {
		movers = new Mover[5];

		// initialization
		for (int i = 0; i < movers.length; i++) {
			movers[i] = new Mover(200 + PMath.random(400), 120 + PMath.random(240), Math.random() * 30);
		}
	}

	@Override
	public void update(float delta) {
		// gravity force with 1 unit mass
		for (Mover mover : movers) {
			// acceleration of gravity
			PVector gravity = new PVector(0.0f, -9.81f);
			// weight effect the mover object
			PVector weight = PVector.mul(gravity, mover.mass());
			mover.applyForce(weight);

			if (Mouse.isButtonDown(0)) {
				PVector wind = new PVector(10f, 0.0f);
				mover.applyForce(wind);
			}
			mover.update(delta);

			edges(mover);
		}

	}

	@Override
	public void draw() {
		for (Mover mover : movers) {
			mover.display();
		}
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Force1());
	}

	private void edges(Mover mover) {
		// check edges
		if (mover.location.x < 0) {
			mover.velocity.x = -mover.velocity.x;
			mover.location.x = 0;
		}
		if (mover.location.x > 800) {
			mover.velocity.x = -mover.velocity.x;
			mover.location.x = 800;
		}
		if (mover.location.y < 0) {
			mover.velocity.y = -mover.velocity.y;
			mover.location.y = 0;
		}
		if (mover.location.y > 480) {
			mover.velocity.y = -mover.velocity.y;
			mover.location.y = 480;
		}
	}

}
