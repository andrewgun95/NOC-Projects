package noc.tutorial.oscillation.spring;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Spring {

	// number of oscillate
	static final float n = 8f;

	float x, y;
	float restlength, currlength;

	public Spring(float x_, float y_, float length_) {
		x = x_;
		y = y_;
		restlength = length_;
		currlength = length_;
	}

	public void draw(float amp) {
		glPushMatrix();
		glTranslatef(x, y, .0f); 

		final float lambda = currlength / n;
		final float w = (float) (2 * Math.PI) / lambda;
		float angle = .0f;

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_STRIP);
		for (float y = 0; y > -currlength; y -= 0.1f) {
			glVertex2f(amp * (float) Math.sin(angle += (w * 0.1f)), y);
		}
		glEnd();

		glPopMatrix();
	}

	public void connect(Bolb bolb) {
		currlength = y - (bolb.location.y + bolb.radius);
		currlength = currlength < (50 - bolb.radius / 2) ? (50 - bolb.radius / 2) : currlength;
	}

}
