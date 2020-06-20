package noc.tutorial.box2d;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Motion4 extends Application {

	World world;
	float accumulator;

	Ball ball;
	Slider slider;

	@Override
	public void setup() {

		world = new World(new Vec2(0.0f, -9.81f), false);
		accumulator = 0.0f;

		ball = new Ball(0.5f, 3.0f, 0.1f);
		ball.create(world);
		slider = new Slider(1.0f, 2.0f);
		slider.create(world);

		BodyDef bodyDef = new BodyDef();
		bodyDef.active = true;
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(0.0f, 0.0f);

		Body terrain = world.createBody(bodyDef);
		PolygonShape a = new PolygonShape();
		a.setAsEdge(new Vec2(0.0f, 0.0f), new Vec2(5.0f, 0.0f));
		FixtureDef fixtureDef = new FixtureDef();
		// coefficient friction asphalt
		fixtureDef.friction = 0.67f;
		fixtureDef.density = 1.0f;
		fixtureDef.shape = a;
		
		terrain.createFixture(fixtureDef);

		Body wall = world.createBody(bodyDef);
		PolygonShape b = new PolygonShape();
		b.setAsEdge(new Vec2(5.0f, 0.0f), new Vec2(5.0f, 3.0f));
		wall.createFixture(b, 1.0f);
	}

	@Override
	public void update(float delta) {
		physicsUpdate(delta, fps);
	}

	public void physicsUpdate(float delta, float fps) {

		final float fdelta = 1 / fps;

		float frameTime = Math.min(delta, 0.25f);

		accumulator += frameTime;

		while (accumulator >= fdelta) {
			world.step(fdelta, 8, 3);
			accumulator -= fdelta;
		}
	}

	@Override
	public void draw() {

		slider.display();
		ball.display();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_LOOP);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(5.0f, 0.0f);
		glVertex2f(5.0f, 3.0f);
		glVertex2f(0.0f, 3.0f);
		glEnd();
	}

	static final float PPM = 160f;
	static final float WIDTH = 5f;
	static final float HEIGHT = 3f;

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(WIDTH, HEIGHT, PPM);
		setup.start(new Motion4());
	}

}
