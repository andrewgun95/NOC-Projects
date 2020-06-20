package noc.tutorial.system;

import java.util.ArrayList;
import java.util.LinkedList;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

import project.noc.utils.PMath;
import project.noc.utils.PVector;

public class System1 extends Application {

	ArrayList<Particle> particles;
	ParticlePool pool = new ParticlePool() {

		@Override
		Particle newObject() {
			return new Particle();
		}

	};

	@Override
	public void setup() {
		particles = new ArrayList<>();
	}

	@Override
	public void update(float delta) {
		Particle particle = pool.obtain();

		particle.init(Mouse.getX(), Mouse.getY(), 5);
		particle.applyFrc(new PVector(.0f, 1000f));
		particles.add(particle);

		int i = 0;
		while (i < particles.size()) {
			Particle p = particles.get(i);

			PVector gravity = new PVector(.0f, -9.81f);
			p.applyFrc(PVector.mul(gravity, p.mass));

			p.applyFrc(new PVector(PMath.random(-50, 50), 0));

			p.update(delta);

			if (p.isOver()) {
				pool.free(particles.get(i));
				particles.remove(i);
			} else
				i++;
		}
	}

	@Override
	public void draw() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		for (Particle p : particles) {
			p.draw();
		}
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new System1());
	}

	abstract class ParticlePool {

		ParticlePool next;
		LinkedList<Particle> pools = new LinkedList<>();

		abstract Particle newObject();

		Particle obtain() {
			return pools.size() == 0 ? newObject() : pools.pop();
		}

		void free(Particle particle) {
			particle.reset();
			pools.push(particle);
		}

	}

}
