package noc.tutorial.collision;

import project.noc.utils.PMatrix;
import project.noc.utils.PVector;

public class Momentum {

	Ball[] balls;

	float e;

	public Momentum(Ball[] balls, float restitution) {
		this.balls = balls;
		e = restitution;
	}

	public void update(float time) {

		for (int i = 0; i < balls.length; i++) {
			Ball a = balls[i];

			for (int j = 0; j < balls.length; j++) {
				Ball b = balls[j];

				if (i != j) {
					float distance = PVector.dist(a.loc, b.loc);

					// collision detection
					if (distance <= a.radius + b.radius) {

						// correctness position
						PVector p_a = PVector.sub(a.loc, b.loc);
						p_a.norm();
						p_a.scl((float) (a.radius + b.radius));

						a.loc.set(PVector.add(b.loc, p_a));

						PVector p_b = PVector.sub(b.loc, a.loc);
						p_b.norm();
						p_b.scl((float) (a.radius + b.radius));

						b.loc.set(PVector.add(a.loc, p_b));

						// normal velocity

						PVector norm_a = PMatrix.rotate2D_i(PVector.angle(PVector.sub(a.loc, b.loc)), a.vel);
						PVector norm_b = PMatrix.rotate2D_i(PVector.angle(PVector.sub(b.loc, a.loc)), b.vel);

						float v_a = norm_a.x;
						float v_b = norm_b.x;

						float m_a = a.mass;
						float m_b = b.mass;

						float nv_a = ((m_a - e * m_b) / (m_a + m_b) * v_a) + (((1 + e) * m_b) / (m_a + m_b) * v_b);
						float nv_b = (((1 + e) * m_a) / (m_a + m_b) * v_a) + ((m_b - e * m_a) / (m_a + m_b) * v_b);

						// new velocity based of normal velocity
						PVector vel_a = PMatrix.rotate2D(PVector.angle(PVector.sub(a.loc, b.loc)), new PVector(-nv_a, norm_a.y));
						PVector vel_b = PMatrix.rotate2D(PVector.angle(PVector.sub(b.loc, a.loc)), new PVector(-nv_b, norm_b.y));

						a.vel.set(vel_a);
						b.vel.set(vel_b);
					}

				}
			}

			// collision walls
			if (a.loc.x <= a.radius) {
				a.loc.x = (float) a.radius;

				float newvel = -e * a.vel.x;
				a.vel.x = newvel;
			}
			if (a.loc.x >= 800 - a.radius) {
				a.loc.x = (float) (800 - a.radius);

				float newvel = -e * a.vel.x;
				a.vel.x = newvel;
			}
			if (a.loc.y <= a.radius) {
				a.loc.y = (float) a.radius;

				float newvel = -e * a.vel.y;
				a.vel.y = newvel;
			}
			if (a.loc.y >= 480 - a.radius) {
				a.loc.y = (float) (480 - a.radius);

				float newvel = -e * a.vel.y;
				a.vel.y = newvel;
			}
		}

	}
}
