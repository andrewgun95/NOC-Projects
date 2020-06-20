package noc.tutorial.slider;

import java.awt.geom.Line2D;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Slider {

	float width, height;

	public Slider(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public void display() {
		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_POLYGON);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(width, 0.0f);
		glVertex2f(0.0f, height);
		glEnd();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_LOOP);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(width, 0.0f);
		glVertex2f(0.0f, height);
		glEnd();
	}

	public Line2D.Float line() {
		return new Line2D.Float(0.0f, height, width, 0.0f);
	}

	public float angle() {
		return (float) Math.toDegrees(Math.atan2(height, width));
	}

	public PVector friction() {
		PVector a = new PVector(width, 0.0f);
		PVector b = new PVector(0.0f, height);

		return PVector.sub(b, a).nor();
	}

	public PVector force() {

		PVector a = new PVector(width, 0.0f);
		PVector b = new PVector(0.0f, height);

		return PVector.sub(a, b).nor();
	}

}
