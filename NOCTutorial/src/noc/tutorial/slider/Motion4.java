package noc.tutorial.slider;

import java.awt.geom.Line2D;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import project.noc.utils.PIntersector;
import project.noc.utils.PVector;

public class Motion4 extends Application {

	Slider slider;
	Ball ball;

	@Override
	public void setup() {
		slider = new Slider(400, 200);
		ball = new Ball(100, 350, 20);
	}

	@Override
	public void update(float delta) {

		PVector gravity = new PVector(0, -9.81f);
		PVector weight = PVector.mul(gravity, ball.mass);
		ball.applyForce(weight);

		Line2D.Float line = slider.line();
		PVector point = ball.location;

		if (PIntersector.distanceLinePoint(line.x1, line.y1, line.x2, line.y2, point.x, point.y) <= ball.radius + 8) {

			// intersect point
			PVector p = PIntersector.nearestSegmentPoint(line.x1, line.y1, line.x2, line.y2, point.x, point.y, new PVector());

			// direction
			PVector normal = PVector.sub(point, p).nor();
			PVector tangen = slider.force();
			PVector fric = slider.friction();

			// penetration
			ball.location.set(PVector.add(p, PVector.mul(normal, ball.radius + 8)));

			// PVector momentum = PVector.mul(normal, ball.restitution * ball.velocity.mag());
			// ball.velocity.set(momentum);

			// force
			PVector _weight = PVector.mul(tangen, ball.mass * gravity.mag() * (float) Math.sin(slider.angle()));

			// friction
			final float coef_asphalt = 0.67f;
			PVector friction = PVector.mul(fric, coef_asphalt * ball.mass * gravity.mag() * (float) Math.cos(slider.angle()));

			// calculate net force
			PVector netforce = PVector.add(_weight, friction);

			// calculate torque
			float torque = -PVector.mul(friction, ball.radius).mag();
			ball.applyTorque(torque);

			ball.applyForce(netforce);
		}

		if (ball.location.y <= ball.radius + 10) {
			ball.location.y = ball.radius + 10;

			// float momentum = -ball.restitution * ball.velocity.y;
			// ball.velocity.y = momentum;
		}

		if (ball.location.x >= 800 - ball.radius) {
			ball.location.x = 800 - ball.radius;

			// float momentum = -ball.restitution * ball.velocity.y;
			// ball.velocity.x = momentum;
		}

		ball.update(delta);
	}

	@Override
	public void draw() {
		slider.display();
		ball.display();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Motion4());
	}

}
