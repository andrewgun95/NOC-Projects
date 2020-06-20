package noc.tutorial.collision;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Motion1 extends Application {

	Wheel wheel;

	@Override
	public void setup() {
		wheel = new Wheel(125, 220, 20);
		wheel.applyForce(new PVector(5000, 0));
	}

	@Override
	public void update(float delta) {
		wheel.update(delta);

		if (wheel.location.x < 0 || wheel.location.x > 800)
			wheel.velocity.x = -wheel.velocity.x;
	}

	@Override
	public void draw() {
		glBegin(GL_LINES);
		glVertex2f(0, 200);
		glVertex2f(800, 200);
		glEnd();

		wheel.display();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Motion1());
	}

}
