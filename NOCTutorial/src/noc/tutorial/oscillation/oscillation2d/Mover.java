package noc.tutorial.oscillation.oscillation2d;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Mover {

	static final int numSegments = 100;
	static final double radius = 25;

	ArrayList<Point2D.Float> points = new ArrayList<>();

	public Oscillator2D oscillator;
	float freq, period;

	public Mover(float x, float y, float freq_, float period_) {
		oscillator = new Oscillator2D(x, y);
		freq = freq_;
		period = period_;
	}

	public void display() {

		float x = oscillator.atX(100, 1, 1);
		float y = oscillator.atY(100, freq, period);

		points.add(new Point2D.Float(x, y));

		glPushMatrix();

		glTranslatef(x, y, 0);
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_TRIANGLE_FAN);
		for (int i = 0; i <= numSegments; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();
		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f((float) (Math.cos(angle) * radius), (float) (Math.sin(angle) * radius));
		}
		glEnd();

		glPopMatrix();

		glBegin(GL_LINES);
		glVertex2f(400, 240);
		glVertex2f(x, y);
		glEnd();

		glColor3f(1.0f, 0.8f, 0.8f);
		glBegin(GL_LINE_STRIP);
		for (Point2D.Float p : points) {
			glVertex2f(p.x, p.y);
		}
		glEnd();

		if (points.size() == 200) {
			points.remove(0);
		}
	}

}
