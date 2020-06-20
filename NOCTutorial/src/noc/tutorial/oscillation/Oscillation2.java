package noc.tutorial.oscillation;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMath;

public class Oscillation2 extends Application {

	Polar polar;

	ArrayList<Point2D.Float> points = new ArrayList<>();

	@Override
	public void setup() {
		polar = new Polar(0, 100);
	}

	float time = 0.0f;

	public void update(float delta) {
		if (polar.r >= 0) {
			// speed draw line
			polar.a += 0.02f;
			float noise = (float) PMath.noise(time += delta);

			float amount = PMath.map(noise, -0.5f, 0.5f, 0.0f, 0.2f);

			// width between line
			polar.r -= amount;
			
//			polar.r -= 0.1f;
		}

		Cartesian c = polar.toCartesian();

		points.add(new Point2D.Float(c.x, c.y));
	}

	@Override
	public void draw() {
		glPushMatrix();
		glTranslatef(400, 240, 0);

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_STRIP);
		for (Point2D.Float p : points) {
			glVertex2f(p.x, p.y);
		}
		glEnd();

		glPopMatrix();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation2());
	}

	class Cartesian {
		float x, y;

		Cartesian(float x_, float y_) {
			x = x_;
			y = y_;
		}

		Polar toPolar() {
			float angle = (float) Math.atan2(y, x);
			float radius = (float) Math.sqrt(x * x + y * y);
			return new Polar(angle, radius);
		}
	}

	class Polar {

		float a, r;

		Polar(float a_, float r_) {
			a = a_;
			r = r_;
		}

		Cartesian toCartesian() {
			return new Cartesian((float) (Math.cos(a) * r), (float) (Math.sin(a) * r));
		}
	}

}
