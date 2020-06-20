package noc.tutorial.oscillation;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Oscillation8 extends Application {

	Pendulum pendulum;

	@Override
	public void setup() {
		pendulum = new Pendulum(400, 240, 50);
		pendulum.angle = 90;
	}

	public void update(float delta){
		pendulum.update(delta);
	}
	
	@Override
	public void draw() {
		pendulum.display();
		axis();
	}

	public void axis() {
		glColor3f(0.5f, 0.5f, 0.5f);
		// y axis
		glBegin(GL_LINES);
		glVertex2f(400, 0);
		glVertex2f(400, 480);
		glEnd();
		// x axis
		glBegin(GL_LINES);
		glVertex2f(0, 240);
		glVertex2f(800, 240);
		glEnd();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation8());
	}

}
