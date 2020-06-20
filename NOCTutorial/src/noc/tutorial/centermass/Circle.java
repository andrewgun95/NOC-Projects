package noc.tutorial.centermass;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Circle {

	static final int numSegments = 100;

	PVector location;
	double radius;

	Vertex2D[] vertices;

	public Circle(PVector loc, double r) {
		location = loc;
		radius = r;

		vertices = norm_vertices(r);
	}

	private Vertex2D[] norm_vertices(double r) {
		Vertex2D[] vertices = new Vertex2D[numSegments];

		for (int i = 0; i < vertices.length; i++) {
			double angle = i * ((Math.PI * 2) / numSegments);
			vertices[i] = new Vertex2D(
					(float) (Math.cos(angle) * radius), 
					(float) (Math.sin(angle) * radius)
			);
		}

		return vertices;
	}

	public Vertex2D cmass() {
		Vertex2D center = new Vertex2D();
		for (Vertex2D v : vertices) {
			center.add(v);
		}

		center.div(vertices.length);

		return center;
	}

	float angle = 0.0f;
	
	public void display() {
		glLoadIdentity();

		glPushMatrix();
		// correct origin position
		glTranslatef(location.x, location.y, 0);
		glRotatef(angle += 2.0f, 0, 0, 1);

		glBegin(GL_LINES);
		glVertex2f(0.0f, 0.0f);
		glVertex2f((float) radius, 0.0f);
		glEnd();

		glBegin(GL_LINE_LOOP);
		for (Vertex2D vertex : vertices) {
			glVertex2f(vertex.x - cmass().x, vertex.y - cmass().y);
		}
		glEnd();

		glPointSize(1.5f);
		glBegin(GL_POINTS);
		glVertex2f(0.0f, 0.0f);
		glEnd();

		glPopMatrix();
	}

}
