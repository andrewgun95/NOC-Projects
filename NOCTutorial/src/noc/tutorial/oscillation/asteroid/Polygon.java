package noc.tutorial.oscillation.asteroid;

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

public class Polygon {

	Vertex2D[] vertices;

	public Polygon(Vertex2D[] ver) {
		vertices = ver;
	}

	public Vertex2D cmass() {

		Vertex2D center = new Vertex2D();

		for (Vertex2D v : vertices) {
			center.add(v);
		}

		center.div(vertices.length);

		return center;
	}

	public void display(PVector location, float angle) {
		glLoadIdentity();

		glPushMatrix();
		glTranslatef(location.x, location.y, 0);

		glRotatef(angle, 0, 0, 1);

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
