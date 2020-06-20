package noc.tutorial.distribution;

import java.util.Random;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Gaussian extends Application {

	private int[] distribution;
	private Random random;

	@Override
	public void setup() {
		distribution = new int[20];
		random = new Random();
		frame(120);
	}

	@Override
	public void draw() {		
		int mean = 10;
		double std = 3.0;
		
		int index = mean + (int) (random.nextGaussian() * std);

		if (index > distribution.length - 1) {
			index = distribution.length - 1;
		}

		if (index < 0) {
			index = 0;
		}

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
		setup.start(new Gaussian());
	}
}
