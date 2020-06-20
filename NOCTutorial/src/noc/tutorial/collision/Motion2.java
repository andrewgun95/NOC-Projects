package noc.tutorial.collision;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Motion2 extends Application {

	Ball a, b;
	Collision1 collision;

	@Override
	public void setup() {
		a = new Ball(100, 225, 25);
		a.applyForce(new PVector(10000, 0));
		b = new Ball(700, 225, 25);
		b.applyForce(new PVector(-5000, 0));

		collision = new Collision1(a, b, 0.5f);
	}

	public void update(float delta) {

		a.update(delta);
		b.update(delta);

		collision.update(delta);
	}

	@Override
	public void draw() {
		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINES);
		glVertex2f(0, 200);
		glVertex2f(800, 200);
		glEnd();

		a.display();
		b.display();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Motion2());
	}

}
