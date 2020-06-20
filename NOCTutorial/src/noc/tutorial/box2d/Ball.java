package noc.tutorial.box2d;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Ball {

	static final int numSegments = 100;

	Vec2 loc;
	float r;

	Body body;
	Fixture fixture;

	public Ball(float x, float y, float radius) {
		loc = new Vec2(x, y);
		r = radius;
	}

	public void create(World world) {

		BodyDef bodyDef = new BodyDef();
		bodyDef.active = true;
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(loc.x, loc.y);

		body = world.createBody(bodyDef);

		CircleShape shape = new CircleShape();
		shape.m_radius = r;

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.restitution = 0.2f;
		fixtureDef.density = 0.1f;
		fixtureDef.shape = shape;

		fixture = body.createFixture(fixtureDef);
	}

	public void display() {
		glPushMatrix();

		Vec2 loc = body.getWorldCenter();

		glTranslatef(loc.x, loc.y, 0.0f);
		glRotatef((float) Math.toDegrees(body.getAngle()), 0, 0, 1);

		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_TRIANGLE_FAN);
		for (int i = 0; i < numSegments + 1; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f((float) (Math.cos(angle) * r), (float) (Math.sin(angle) * r));
		}
		glEnd();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < numSegments; i++) {
			double angle = i * (Math.PI * 2 / numSegments);
			glVertex2f((float) (Math.cos(angle) * r), (float) (Math.sin(angle) * r));
		}
		glEnd();

		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_LINES);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(r, 0.0f);
		glEnd();

		glPopMatrix();
	}

}
