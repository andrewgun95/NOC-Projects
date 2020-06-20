package noc.tutorial.intro;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PMath;

public class Walker {

	private static Random random = new Random();

	public float x, y;
	public float distance;

	public ArrayList<Point2D.Float> footstep;

	public Walker(float x, float y, float distance) {
		this.x = x;
		this.y = y;
		this.distance = distance;

		footstep = new ArrayList<>();
	}

	public void step4c() {
		footstep.add(new Point2D.Float(x, y));

		// 0.5 change happen
		if (PMath.random(0.5f))
			x += distance;
		// 0.5 change happen
		else if (PMath.random(0.5f))
			y += distance;
		// 0.1 change happen
		else if (PMath.random(0.1f))
			x -= distance;
		// 0.1 change happen
		else if (PMath.random(0.1f)) y -= distance;

		x = constrain(x, 0f, 800f);
		y = constrain(y, 0f, 480f);
	}

	public void step4() {
		footstep.add(new Point2D.Float(x, y));

		int direction = random.nextInt(4);

		if (direction == 0) x += distance;
		if (direction == 1) y += distance;
		if (direction == 2) x -= distance;
		if (direction == 3) y -= distance;

		x = constrain(x, 0f, 800f);
		y = constrain(y, 0f, 480f);
	}

	public void step9d() {
		footstep.add(new Point2D.Float(x, y));

		// x : -1 1
		int stepX = PMath.random(-1, 1);
		// y : -1 1
		int stepY = PMath.random(-1, 1);

		x += (stepX * distance);
		y += (stepY * distance);

		x = constrain(x, 0f, 800f);
		y = constrain(y, 0f, 480f);
	}
	

	public void step9() {
		footstep.add(new Point2D.Float(x, y));

		// x : -1 0 1
		int stepX = random.nextInt(3) - 1;
		// y : -1 0 1
		int stepY = random.nextInt(3) - 1;

		x += (stepX * distance);
		y += (stepY * distance);

		x = constrain(x, 0f, 800f);
		y = constrain(y, 0f, 480f);
		
	}

	public void draw() {
		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_QUADS);
		glVertex2f(x - 5f, y - 5f);
		glVertex2f(x - 5f, y + 5f);
		glVertex2f(x + 5f, y + 5f);
		glVertex2f(x + 5f, y - 5f);
		glEnd();

		// footstep
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_LINE_STRIP);
		for (Point2D.Float point : footstep) {
			glVertex2f(point.x, point.y);
		}
		glEnd();
	}

	private float constrain(float value, float minvalue, float maxvalue) {
		return value < minvalue ? minvalue : value > maxvalue ? maxvalue : value;
	}
}
