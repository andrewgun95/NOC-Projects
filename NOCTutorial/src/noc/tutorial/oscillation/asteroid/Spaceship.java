package noc.tutorial.oscillation.asteroid;

import project.noc.utils.PVector;

public class Spaceship {

	static final float mass = 10;

	Polygon polygon;

	PVector location;
	PVector velocity;
	PVector acceleration;
	PVector force;

	float angle;

	public Spaceship(float x, float y) {
		polygon = new Polygon(new Vertex2D[] { new Vertex2D(-15, -20), new Vertex2D(-10, -25), new Vertex2D(10, -25), new Vertex2D(15, -20), new Vertex2D(0, 10) });

		location = new PVector(x, y);
		velocity = new PVector();
		acceleration = new PVector();
		force = new PVector();
		angle = 0.0f;
	}

	public void update(float delta) {

		acceleration.set(PVector.div(force, mass));

		velocity.add(PVector.mul(acceleration, delta));
		velocity.limit(10);

		location.add(velocity);

		force.scl(0);
	}

	public void rotate(float a) {
		angle = a;
	}

	public void applyForce(float radius) {
		PVector f = new PVector((float) Math.cos(Math.toRadians(90 + angle)) * radius, (float) Math.sin(Math.toRadians(90 + angle)) * radius);
		force.add(f);
	}

	public void display() {
		polygon.display(location, angle);
	}

}
