package noc.tutorial.oscillation.system;

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

	static final float k = 100f;
	static final float damping = 0.98f;

	float restLength;
	float currLength;

	Bolb a, b;

	float angle;

	public Spring(Bolb a_, Bolb b_) {
		a = a_;
		b = b_;

		PVector len = PVector.sub(b_.location, a_.location);
		currLength = len.mag();

		angle = PVector.angle(len) + 90;

		// calculate rest length
		restLength = currLength;
	}

	public void update() {
		PVector len = PVector.sub(b.location, a.location);
		currLength = len.mag();

		angle = PVector.angle(len) + 90;

		float displacement = currLength - restLength;

		// force a -> b
		PVector fa = PVector.sub(a.location, b.location);
		fa.norm();
		fa.scl(-k * displacement);

		a.applyForce(fa);

		a.velocity = a.velocity.scl(damping);

		// force b -> a
		PVector fb = PVector.sub(b.location, a.location);
		fb.norm();
		fb.scl(-k * displacement);

		b.applyForce(fb);

		b.velocity = b.velocity.scl(damping);
	}

	public void draw(float amp) {
		final int n = 10;

		glPushMatrix();
		glTranslatef(a.location.x, a.location.y, .0f);
		glRotatef(angle, 0, 0, 1);

		final float lambda = currLength / n;
		final float w = (float) (2 * Math.PI) / lambda;
		float angle = .0f;

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_STRIP);
		for (float y = 0; y > -currLength; y -= 0.1f) {
			glVertex2f(amp * (float) Math.sin(angle += (w * 0.1f)), y);
		}
		glEnd();

		glPopMatrix();
	}

}
