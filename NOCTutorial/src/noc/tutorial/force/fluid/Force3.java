package noc.tutorial.force.fluid;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

import project.noc.utils.PVector;

public class Force3 extends Application {

	static final float wdensity = 0.9f;
	static final float rdensity = 2.7f;

	Fluid fluid;
	Box[] wood;
	Box[] rock;

	@Override
	public void setup() {
		fluid = new Fluid(0, 0, 800, 250);
		wood = new Box[3];
		for (int i = 0; i < wood.length; i++) {
			float size = (float) (20 + Math.random() * 10);
			wood[i] = new Box(i * 100 + 50, 400, size, wdensity);
		}

		rock = new Box[3];
		for (int i = 0; i < rock.length; i++) {
			float size = (float) (20 + Math.random() * 10);
			rock[i] = new Box(i * 100 + 550, 400, size, rdensity);
		}
	}

	@Override
	public void update(float delta) {
		PVector gravity = new PVector(0.0f, -9.81f);

		for (Box w : wood) {
			// calculate weight : mass * gravity
			PVector weight = PVector.mul(gravity, w.mass());
			w.applyForce(weight);

			if (fluid.contains(w)) {
				PVector friction = new PVector();
				// calculate direction : -v
				friction.set(w.velocity);
				friction.norm();
				friction.rev();

				// calculate magnitude
				float speed = w.velocity.mag();
				// coefficient is bigger because area of box per 25 meter
				float dragcoef = 100f;
				// in water
				float density = 1;

				friction.scl(0.5f * density * speed * speed * w.side * dragcoef);

				w.applyForce(friction);

				// bouyancy force for box : simply just density * (opposite) gravity * side * side
				PVector bouyancy = new PVector(); 
				bouyancy.set(PVector.mul(gravity, density * w.side * w.side).neg());

				w.applyForce(bouyancy);
			}

			// check edges
			if (w.location.y < 0) {
				w.location.y = 0;
				w.velocity.y = -w.velocity.y;
			}

			w.update(delta);
		}

		for (Box r : rock) {
			PVector weight = PVector.mul(gravity, r.mass());
			r.applyForce(weight);

			if (fluid.contains(r)) {
				PVector friction = new PVector();

				friction.set(r.velocity);
				friction.norm();
				friction.rev();

				float speed = r.velocity.mag();
				float dragcoef = 100f;
				// in water
				float density = 1;

				friction.scl(0.5f * density * speed * speed * r.side * dragcoef);

				r.applyForce(friction);

				PVector bouyancy = new PVector();
				bouyancy.set(PVector.mul(gravity, density * r.side * r.side).neg());

				r.applyForce(bouyancy);
			}

			// check edges
			if (r.location.y < 0) {
				r.location.y = 0;
				r.velocity.y = -r.velocity.y;
			}

			r.update(delta);
		}
	}

	@Override
	public void draw() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		fluid.display();
		// display wood
		for (Box w : wood) {
			w.display();
		}
		// display rock
		for (Box r : rock) {
			r.display();
		}

	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Force3());
	}

}
