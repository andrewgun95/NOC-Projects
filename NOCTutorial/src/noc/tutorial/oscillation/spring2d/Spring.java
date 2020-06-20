package noc.tutorial.oscillation.spring2d;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Spring {

	PVector anchor;
	// rest length
	float restlength;
	float currlength;

	float angle;

	public Spring(PVector anchor_, PVector bolb_loc, float bolb_rad) {
		anchor = anchor_;
		restlength = PVector.sub(bolb_loc, anchor_).mag() - bolb_rad;
		currlength = restlength;
		angle = .0f;
	}

	public void attachTo(Bolb bolb) {
		PVector length = PVector.sub(bolb.location, anchor);

		currlength = length.mag() - bolb.radius;
		angle = PVector.angle(length) + 90;
	}

	public void applyTo(Bolb bolb) {
		PVector force = PVector.sub(bolb.location, anchor);
		force.norm();

		final float k = 1000f;
		float displacement = currlength - restlength;
		force.scl(-k * displacement);

		bolb.applyForce(force);

		// friction
		final float e = 0.98f;
		bolb.velocity = bolb.velocity.scl(e);
	}

	public void draw(float amp) {
		final int n = 8;

		glPushMatrix();
		glTranslatef(anchor.x, anchor.y, .0f);
		glRotatef(angle, 0, 0, 1);

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

}
