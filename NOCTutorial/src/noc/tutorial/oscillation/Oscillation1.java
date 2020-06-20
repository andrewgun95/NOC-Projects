package noc.tutorial.oscillation;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Oscillation1 extends Application {

	Polygon polygon;

	@Override
	public void setup() {
		polygon = new Polygon(new PVector(400, 240),
				new Vertex2D[] {

				new Vertex2D(-25, 0), new Vertex2D(-30, -50), new Vertex2D(50, -45), new Vertex2D(0, 25)

		});
	}

	public void update(float delta) {

		float momentarm = Mouse.getX() - polygon.location.x;

		final float force = 0.1f;

		polygon.applyTorque(force * momentarm);

		polygon.update(delta);
	}

	@Override
	public void draw() {
		polygon.display();
		
		glBegin(GL_LINES);
		glVertex2f(polygon.location.x, 240f);
		glVertex2f(Mouse.getX(), 240f);
		glEnd();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation1());
	}

}
