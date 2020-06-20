package noc.tutorial.extra.kinematics;

import java.util.ArrayList;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Forward extends Application {

	final int segments = 10;
	final int length = 25;

	Line root;
	ArrayList<Line> lines = new ArrayList<>();

	@Override
	public void setup() {
		root = new Line(400, 0, 90);

		Line parent = root;
		for (int i = 0; i < segments; i++) {

			Line line = new Line(parent, length, 0);
			lines.add(line);

			parent = line;
		}

	}

	@Override
	public void update(float delta) {

		for (Line line : lines) {
			float degree = interpolate(-5, 5, 0.01f);

			line.rotate(degree);
			line.calculate();
		}

	}

	float t = .0f;

	float interpolate(float a, float b, float s) {
		t += s;
		return a * (float) (Math.cos(t) * Math.cos(t)) + b * (float) (Math.sin(t) * Math.sin(t));
	}

	@Override
	public void draw() {
		for (Line line : lines) {
			line.draw();
		}
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Forward());
	}

	class Line {

		float x, y, angle;

		Line parent;
		float length;
		float rotate;

		/**
		 * create root
		 * 
		 * @param x
		 * @param y
		 */
		Line(float x, float y, float rotate) {
			this.x = x;
			this.y = y;
			this.angle = (float) (Math.toRadians(rotate));
		}

		/**
		 * create child
		 * 
		 * @param parent
		 * @param length
		 * @param rotate
		 */
		Line(Line parent, float length, float rotate) {
			this.parent = parent;
			this.length = length;
			this.rotate = (float) (Math.toRadians(rotate));
			calculate();
		}

		void rotate(float degree) {
			rotate = (float) (Math.toRadians(degree));
		}

		void calculate() {
			x = parent.x + (float) (length * Math.cos(parent.angle + this.rotate));
			y = parent.y + (float) (length * Math.sin(parent.angle + this.rotate));
			angle = parent.angle + this.rotate;
		}

		void draw() {
			glColor3f(0.5f, 0.5f, 0.5f);
			glLineWidth(3.0f);
			glBegin(GL_LINES);
			glVertex2f(parent.x, parent.y);
			glVertex2f(x, y);
			glEnd();
		}

	}

}
