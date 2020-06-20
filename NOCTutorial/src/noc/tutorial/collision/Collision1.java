package noc.tutorial.collision;

import project.noc.utils.PVector;

public class Collision1 {

	Ball a;
	Ball b;

	float e;

	public Collision1(Ball ball1, Ball ball2, float restitution) {
		this.a = ball1;
		this.b = ball2;
		e = restitution;
	}

	public void update(float time) {

		float distance = PVector.dist(a.loc, b.loc);

		// collision detection
		if (distance <= a.radius + b.radius) {
			// correctness position
			a.loc.x = b.loc.x - (float) (a.radius + b.radius);
			b.loc.x = a.loc.x + (float) (a.radius + b.radius);

			// do collision response

			// velocity in x direction : ignore velocity y because line of action only acting on x direction
			float vel_a = a.vel.x;
			float vel_b = b.vel.x;
			// mass
			float mass_a = a.mass;
			float mass_b = b.mass;

			float newvel_a = ((mass_a - e * mass_b) / (mass_a + mass_b) * vel_a) + (((1 + e) * mass_b) / (mass_a + mass_b) * vel_b);
			float newvel_b = (((1 + e) * mass_a) / (mass_a + mass_b) * vel_a) + ((mass_b - e * mass_a) / (mass_a + mass_b) * vel_b);

			a.vel.x = newvel_a;
			b.vel.x = newvel_b;
		}

		float d1 = a.loc.x;
		// collision detection
		if (d1 <= a.radius) {
			// correctness position
			a.loc.x = (float) a.radius;

			// do collision response
			float newvel = -e * a.vel.x;
			a.vel.x = newvel;
		}

		float d2 = 800 - b.loc.x;
		// collision detection
		if (d2 <= b.radius) {
			// correctness position
			b.loc.x = 800 - (float) b.radius;

			// do collision response
			float newvel = -e * b.vel.x;
			b.vel.x = newvel;
		}

	}

}
