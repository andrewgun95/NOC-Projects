package noc.tutorial.vector;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2d;

import project.noc.utils.PVector;

public class Pointing extends Application {

	static final int numSegments = 100;
	static final double radius = 2;

	PVector pointing;
	PVector center;

	@Override
	public void setup() {
		frame(60);

		pointing = new PVector();
		center = new PVector();
	}

	@Override
	public void draw() {
		glPushMatrix();
		glTranslatef(400, 240, 0.0f);

		pointing.set(Mouse.getX(), Mouse.getY());
		center.set(400, 240);

		pointing.sub(center);
		
//		pointing.norm();
//		pointing.scl(100);
		
		pointing.limit(100);

		System.out.println(PVector.angle(pointing));
		
		// drawing content
		glColor4f(0.7f, 0.7f, 0.7f, 1.0f);
		glBegin(GL_TRIANGLE_FAN);
		// center point
		glVertex2d(0.0, 0.0);
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * (2 * Math.PI / numSegments);
			glVertex2d(Math.cos(angle) * radius, Math.sin(angle) * radius);
		}
		glEnd();

		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glLineWidth(1);
		glBegin(GL_LINES);
		glVertex2d(0.0, 0.0);
		glVertex2d(pointing.x, pointing.y);
		glEnd();

		glPopMatrix();

		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glRectf(0.0f, 0.0f, pointing.mag(), 50);

	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Pointing());
	}

}
