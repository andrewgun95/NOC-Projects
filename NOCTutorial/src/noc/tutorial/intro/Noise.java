package noc.tutorial.intro;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMath;

public class Noise extends Application {

	double time = 0.0f;

	@Override
	public void setup() {
		frame(60);
	}

	@Override
	public void draw() {		
		time += 0.01;
		// noise : value between -0.5 to 0.5
		float x = (float) PMath.noise(time);
				
		x = PMath.map(x, -0.5f, 0.5f, 0, 800f);

		new Circle(x, 240f, 40f).draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Noise());
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
