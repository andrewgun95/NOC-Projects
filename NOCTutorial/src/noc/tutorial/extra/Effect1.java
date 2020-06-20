package noc.tutorial.extra;

import java.util.ArrayList;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Effect1 extends Application {

	@Override
	public void setup() {
	}

	ArrayList<PVector> points = new ArrayList<>();
	int capacity = 20;

	@Override
	public void draw() {
		PVector mouse = new PVector(Mouse.getX(), Mouse.getY());
		points.add(mouse);

		if (points.size() == capacity) {
			points.remove(0);
		}

		float width1 = 0f;

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glBegin(GL_TRIANGLE_STRIP);
		glColor4f(0.8f, 0.8f, 0.8f, 0.0f);
		glVertex2f(points.get(0).x, points.get(0).y);
		if (points.size() > 1) {
			for (int i = 0; i < points.size() - 1; i++) {

				width1++;

				PVector a = points.get(i);
				PVector b = points.get(i + 1);

				PVector dir = PVector.sub(b, a).nor();
				PVector per = new PVector(-dir.y, dir.x);
				per.scl(width1 / 2f);

				PVector c = PVector.add(b, per);

				glColor4f(0.8f, 0.8f, 0.8f, 0.0f);
				glVertex2f(c.x, c.y);
				glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
				glVertex2f(b.x, b.y);
			}
		}
		glEnd();

		float width2 = 0f;

		glBegin(GL_TRIANGLE_STRIP);
		glColor4f(0.8f, 0.8f, 0.8f, 0.0f);
		glVertex2f(points.get(0).x, points.get(0).y);
		if (points.size() > 1) {
			for (int i = 0; i < points.size() - 1; i++) {

				width2++;

				PVector a = points.get(i);
				PVector b = points.get(i + 1);

				PVector dir = PVector.sub(b, a).nor();
				PVector per = new PVector(-dir.y, dir.x);
				per.scl(width2 / 2f);

				PVector d = PVector.sub(b, per);

				glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
				glVertex2f(b.x, b.y);
				glColor4f(0.8f, 0.8f, 0.8f, 0.0f);
				glVertex2f(d.x, d.y);
			}
		}
		glEnd();

		glDisable(GL_BLEND);
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Effect1());
	}

}
