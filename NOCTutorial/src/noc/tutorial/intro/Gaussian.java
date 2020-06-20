package noc.tutorial.intro;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.Random;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

public class Gaussian extends Application {
	
	private Random random;
	
	@Override
	public void setup() {
		random = new Random();
		frame(60);
	}

	@Override
	public void draw() {		
		float num = (float) random.nextGaussian();
		
		// center of the screen
		float mean = 400;
		
		float std = 100;
		
		new Circle(mean + (num * std), 240f, 10).draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Gaussian());
	}

	class Circle {

		static final int numSegments = 100;

		float x, y;
		float radius;

		public Circle(float x, float y, float radius) {
			this.x = x;
			this.y = y;
			this.radius = radius;
		}

		public void draw() {
			// draw circle
			glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
			glBegin(GL_TRIANGLE_FAN);
			// from 330 degree back to 360 degree (0 degree)
			int length = numSegments + 1;
			glVertex2f(x, y);
			for (int i = 0; i < length; i++) {
				double degree = i * ((Math.PI * 2) / numSegments);
				glVertex2f(x + ((float) Math.cos(degree) * radius), y + ((float) Math.sin(degree) * radius));
			}
			glEnd();

			// draw circle line
			glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
			glBegin(GL_LINE_STRIP);
			for (int i = 0; i < length; i++) {
				double degree = i * ((Math.PI * 2) / numSegments);
				glVertex2f(x + ((float) Math.cos(degree) * radius), y + ((float) Math.sin(degree) * radius));
			}
			glEnd();
		}

	}

}
