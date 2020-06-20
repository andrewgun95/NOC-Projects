package noc.tutorial.vector;

import java.util.Random;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Ball {

	static Random random = new Random();
	static int numSegments = 100;
	static float radius = 25;

	PVector position;
	PVector velocity;

	public Ball() {
		position = new PVector(400, 240);
		velocity = new PVector(random.nextFloat() * 5, random.nextFloat() * 5);
	}

	public void update() {
		position.add(velocity);

		// check edges
		if (position.x > 800 || position.x < 0) velocity.x = -velocity.x;
		if (position.y > 480 || position.y < 0) velocity.y = -velocity.y;
	}

	public void draw() {
		glPushMatrix();
		glTranslatef(position.x, position.y, 0.0f);
		glRotatef(PVector.angle(velocity), 0, 0, 1);
		
		// draw circle
		glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
		glBegin(GL_TRIANGLE_FAN);
		// from 330 degree back to 360 degree (0 degree)
		int length = numSegments + 1;
		glVertex2f(0.0f, 0.0f);
		for (int i = 0; i < length; i++) {
			double degree = i * ((Math.PI * 2) / numSegments);
			glVertex2f(((float) Math.cos(degree) * radius),  ((float) Math.sin(degree) * radius));
		}
		glEnd();

		// draw circle line
		glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		glBegin(GL_LINE_STRIP);
		for (int i = 0; i < length; i++) {
			double degree = i * ((Math.PI * 2) / numSegments);
			glVertex2f(((float) Math.cos(degree) * radius), ((float) Math.sin(degree) * radius));
		}
		glEnd();

		glBegin(GL_LINES);
		glVertex2f(0.0f, 0.0f);
		glVertex2f(radius, 0.0f);
		glEnd();
		
		glPopMatrix();
	}

}
