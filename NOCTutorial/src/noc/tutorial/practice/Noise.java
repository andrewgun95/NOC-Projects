package noc.tutorial.practice;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMath;

public class Noise extends Application {

	public ArrayList<Point2D.Float> footstep = new ArrayList<>();

	private double xOffset;
	private double yOffset;

	@Override
	public void setup() {
		xOffset = Math.random() / 1000;
		yOffset = Math.random() / 1000;
	}

	private double xSpace = 0.0;
	private double ySpace = 0.0;

	@Override
	public void draw() {
		float x = (float) PMath.noise(xSpace += xOffset);
		float y = (float) PMath.noise(ySpace += yOffset);

		x = PMath.map(x, -0.5f, 0.5f, 0, 800);
		y = PMath.map(y, -0.5f, 0.5f, 0, 480);

		footstep.add(new Point2D.Float(x, y));

		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_QUADS);
		glVertex2f(x - 5f, y - 5f);
		glVertex2f(x - 5f, y + 5f);
		glVertex2f(x + 5f, y + 5f);
		glVertex2f(x + 5f, y - 5f);
		glEnd();

		// footstep
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_LINE_STRIP);
		for (Point2D.Float point : footstep) {
			glVertex2f(point.x, point.y);
		}
		glEnd();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Noise());
	}
}
