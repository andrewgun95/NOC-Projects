package noc.tutorial.fractals.korch.triangle;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class KorchLine {

	PVector a, b, c;

	public KorchLine(PVector a, PVector b, PVector c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public void draw(){
		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINES);
		glVertex2f(a.x, a.y);
		glVertex2f(b.x, b.y);
		glEnd();
	}

}
