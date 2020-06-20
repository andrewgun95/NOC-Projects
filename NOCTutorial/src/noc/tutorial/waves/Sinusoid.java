package noc.tutorial.waves;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Sinusoid extends Application {

	@Override
	public void setup() {
	}

	@Override
	public void draw() {
		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_STRIP);
		int amp = 100;
		for (int x = 0; x <= 800; x++) {
			int y = 240;
			y += amp * Math.sin(Math.toRadians(x));
			glVertex2d(x, y);
		}
		glEnd();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Sinusoid());
	}

}
