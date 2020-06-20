package noc.tutorial.fractals;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class CantorSet extends Application {

	@Override
	public void setup() {

	}

	@Override
	public void draw() {
		glColor3f(0.5f, 0.5f, 0.5f);
		cantor_set(200, 600, 240, 10);
	}

	public void cantor_set(float x1, float x2, float y, float detail) {
		line(x1, y, x2, y);
		
		float dist = x2 - x1;
		
		if(dist > detail){
			cantor_set(x1, x1 + dist / 3, y - 10, detail);
			cantor_set(x2 - dist / 3, x2, y - 10, detail);
		}
	}

	public void line(float x1, float y1, float x2, float y2) {
		glBegin(GL_LINES);
		glVertex2f(x1, y1);
		glVertex2f(x2, y2);
		glEnd();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new CantorSet());
	}

}
