package noc.tutorial.distribution;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import project.noc.utils.PMath;

public class Uniform extends Application {

	private int[] distribution;

	@Override
	public void setup() {
		distribution = new int[20];
		frame(120);
	}

	@Override
	public void draw() {		
		int index = PMath.random(distribution.length);

		distribution[index]++;

		float recWidth = 800 / distribution.length;
		float recHeight = 0;

		for (int i = 0; i < distribution.length; i++) {
			// draw rectangle
			glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
			glRectf(i * recWidth, 10, (i * recWidth) + recWidth, 10 + (recHeight + distribution[i]));

			// draw rectangle line
			glLineWidth(2.0f);
			glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
			glBegin(GL_LINE_STRIP);
			glVertex2f(i * recWidth, 10);
			glVertex2f(i * recWidth, 10 + (recHeight + distribution[i]));
			glVertex2f((i * recWidth) + recWidth, 10 + (recHeight + distribution[i]));
			glVertex2f((i * recWidth) + recWidth, 10);
			glEnd();
		}

	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Uniform());
	}

}
