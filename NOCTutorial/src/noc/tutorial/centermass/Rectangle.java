package noc.tutorial.centermass;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Rectangle {

	PVector location;
	float side;

	Vertex2D[] vertices;

	public Rectangle(PVector loc, float s) {
		location = loc;
		side = s;
		vertices = norm_vertices(s);
	}

	private Vertex2D[] norm_vertices(float s) {
		Vertex2D[] vertices = new Vertex2D[4];
		vertices[0] = new Vertex2D(-side / 2, -side / 2);
		vertices[1] = new Vertex2D(side / 2, -side / 2);
		vertices[2] = new Vertex2D(side / 2, side / 2);
		vertices[3] = new Vertex2D(-side / 2, side / 2);
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

		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_POLYGON);
		for (Vertex2D vertex : vertices) {
			glVertex2f(vertex.x - cmass().x, vertex.y - cmass().y);
		}
		glEnd();

		glColor3f(0.5f, 0.5f, 0.5f);
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
