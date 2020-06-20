package noc.tutorial.force;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Force2 extends Application {

	// coefficient steel on steel
	static final float cfkinect = 0.08f;
	static final float cfstatic = 0.1f;

	Mover mover;

	@Override
	public void setup() {
		mover = new Mover(50, 220, 20);
		mover.applyForce(new PVector(500, 0));
	}

	@Override
	public void update(float delta) {
		PVector gravity = new PVector(0, -9.81f);
		// calculation weight (w = m * g)
		PVector weight = PVector.mul(gravity, mover.mass());
		// normal = weight
		float normal = weight.mag();

		if (mover.location.x > 400 && mover.location.x < 600) {
			PVector friction = new PVector();

			// calculate direction
			friction.set(PVector.mul(mover.velocity, -1));
			friction.norm();

			// calculate magnitude (coefficient * normal force)
			friction.scl(cfkinect * normal);

			// equilibrium
			if ((int) mover.velocity.mag() == 0) {
				mover.velocity.scl(0);
			} else
				mover.applyForce(friction);
		}

		mover.update(delta);

		// check edges
		if (mover.location.x > 800) mover.location.x = 0;
	}

	@Override
	public void draw() {
		// terrain
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_LINES);
		glVertex2f(0, 200);
		glVertex2f(800, 200);
		glEnd();
		// rough
		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINES);
		glVertex2f(400, 200);
		glVertex2f(600, 200);
		glEnd();

		mover.display();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Force2());
	}

}
