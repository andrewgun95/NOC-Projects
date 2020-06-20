package noc.tutorial.oscillation.invertedpendulum;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;
import project.noc.utils.PVector;

public class Oscillation12 extends Application {

	Pendulum p;
	Cart c;

	@Override
	public void setup() {
		p = new Pendulum(200, 20, 15);
		c = new Cart(400, 240, 100, 25);
	}

	@Override
	public void update(float delta) {

		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) {
				if (Mouse.getEventButton() == 0) {
					c.applyForce(+5000);
				}
				if (Mouse.getEventButton() == 1) {
					c.applyForce(-5000);
				}
			}

		}

		PVector pivot = c.pivot();
		p.pivotTo(pivot.x, pivot.y);

		// gravity is positive
		final float g = 9.81f;

		// control force
		// equilibrium angle is 0
//		float force = 3 * (0 - (float) (Math.toDegrees(p.angle) * g));

		// smoother
		float force = (3 * (0 - (float)(Math.toDegrees(p.angle) * g))) - (float) Math.toDegrees(p.vel) * g;
		c.applyForce(force);

		float n1 = c.frc * (float) Math.cos(p.angle);
		float n2 = (float) (Math.sin(p.angle)) * ((c.mass + p.mass) * g - p.mass * p.length * p.vel * p.vel * (float) Math.cos(p.angle));
		float d = c.mass * p.length + p.mass * p.length * (float) (Math.sin(p.angle) * Math.sin(p.angle));

		float p_acc = (n1 + n2) / d;
		p.applyAcc(p_acc);

		float c_acc = (p.length * p_acc - g * (float) (Math.sin(p.angle))) / (float) (Math.cos(p.angle));
		c.applyAcc(c_acc);

		c.update(delta);
		p.update(delta);
	}

	@Override
	public void draw() {
		c.draw();
		p.draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation12());
	}

}
