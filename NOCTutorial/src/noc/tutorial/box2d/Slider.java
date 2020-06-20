package noc.tutorial.box2d;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Slider {

	float h, l;

	Body body;
	Fixture fixture;

	public Slider(float height, float length) {
		h = height;
		l = length;
	}

	public void create(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(0.0f, 0.0f);
		bodyDef.type = BodyType.STATIC;

		body = world.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.set(new Vec2[] { new Vec2(0.0f, 0.0f), new Vec2(l, 0.0f), new Vec2(0.0f, h) }, 3);

		FixtureDef fixtureDef = new FixtureDef();
		// coefficient friction asphalt
		fixtureDef.friction = 0.67f;
		fixtureDef.density = 1.0f;
		fixtureDef.shape = shape;

		fixture = body.createFixture(fixtureDef);
	}

	public void display() {

		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_POLYGON);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(l, 0.0f);
		glVertex2f(0.0f, h);
		glEnd();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_LOOP);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(l, 0.0f);
		glVertex2f(0.0f, h);
		glEnd();
	}

}
