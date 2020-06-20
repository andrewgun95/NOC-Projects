package noc.tutorial.fractals.korch;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class KorchLine {

	PVector s, e;
	
	public KorchLine(PVector s, PVector e){
		this.s = s;
		this.e = e;
	}
	
	public void draw(){
		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_LINES);
		glVertex2f(s.x, s.y);
		glVertex2f(e.x, e.y);
		glEnd();
	}
	
}
