package noc.tutorial.extra;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Momentum extends Application {

	Mover mover;

	@Override
	public void setup() {
		mover = new Mover(100, 220, 20);
		mover.applyForce(new PVector(500, 0));
	}

	@Override
	public void update(float delta) {
		PVector gravity = new PVector(0, -9.81f);
		mover.applyForce(PVector.mul(gravity, mover.mass()));

		// apply momentum
		if(Mouse.isButtonDown(0)){
			mover.applyMomentum(new PVector(5f, 10f));
		}
		
		// check edges
		if (mover.location.y <= 220) {
			mover.velocity.y = -mover.velocity.y;
			mover.location.y = 220;
		}
		if (mover.location.x >= 800) {
			mover.location.x = 0;
		}

		mover.update(delta);
	}

	@Override
	public void draw() {
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_LINES);
		glVertex2f(0, 200);
		glVertex2f(800, 200);
		glEnd();

		mover.display();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Momentum());
	}

}
