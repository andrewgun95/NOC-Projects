package noc.tutorial.extra.kinematics;

import java.util.ArrayList;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Inverse extends Application {

	final int segments = 20;

	ArrayList<Line> bodies = new ArrayList<>();
	Line head;

	@Override
	public void setup() {
		head = new Line(400, 240, 10);

		Line parent = head;
		for (int i = 0; i < segments; i++) {

			Line body = new Line(parent);
			bodies.add(body);

			parent = body;
		}
	}

	@Override
	public void update(float delta) {
		float x = Mouse.getX();
		float y = Mouse.getY();

		head.follow(new PVector(x, y));
		
		Line parent = head;
		for (int i = 0; i < segments; i++) {
			Line body = bodies.get(i);

			body.follow(parent);

			parent = body;
		}
	}

	@Override
	public void draw() {
		head.draw();
		
		for (Line line : bodies) {
			line.draw();
		}
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Inverse());
	}

	class Line {

		PVector s, e;
		float length;

		Line(float x, float y, float length) {
			this.s = new PVector(x, y);
			this.e = new PVector(x, y + length);
			this.length = length;
		}

		Line(Line parent) {
			this.e = parent.s;
			this.s = parent.e;
			this.length = parent.length;
		}

		void follow(Line parent) {
			follow(parent.s);
		}

		void follow(PVector target) {
			PVector dir = PVector.sub(s, target);
			dir.norm();
			s = PVector.add(target, dir.scl(length));
			e = target;
		}

		void draw() {
			glColor3f(0.5f, 0.5f, 0.5f);
			glLineWidth(1.0f);
			glBegin(GL_LINES);
			glVertex2f(s.x, s.y);
			glVertex2f(e.x, e.y);
			glEnd();
		}

	}

}
