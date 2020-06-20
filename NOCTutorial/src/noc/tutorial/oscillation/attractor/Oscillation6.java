package noc.tutorial.oscillation.attractor;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

import project.noc.utils.PVector;

public class Oscillation6 extends Application {

	Mover[] mover;
	Attraction attraction;

	@Override
	public void setup() {
		mover = new Mover[5];
		for (int i = 0; i < mover.length; i++) {
			float randX = 50f + (float) (Math.random() * 700);
			float randY = 50f + (float) (Math.random() * 400);
			mover[i] = new Mover(randX, randY, 10 + (float) (Math.random() * 10), (float) (Math.random() * 5));
		}

		attraction = new Attraction(400, 240, 25);
	}

	@Override
	public void update(float delta) {
		for (Mover m : mover) {
			PVector force = attraction.attract(m);
			m.applyForce(force);

			m.update(delta);
		}
	}

	@Override
	public void draw() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		attraction.display();
		for (Mover m : mover) {
			m.display();
		}
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation6());
	}

}
