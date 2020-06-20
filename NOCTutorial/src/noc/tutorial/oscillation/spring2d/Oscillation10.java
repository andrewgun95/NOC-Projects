package noc.tutorial.oscillation.spring2d;

import java.awt.Color;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Oscillation10 extends Application {

	Bolb bolb;
	Spring spring;

	boolean drag;
	PVector dragOffset;

	@Override
	public void setup() {
		bolb = new Bolb(400, 250, 25);
		spring = new Spring(new PVector(400, 450), bolb.location, bolb.radius);

		drag = false;
		dragOffset = new PVector();
	}

	@Override
	public void update(float delta) {

		PVector mouse = new PVector(Mouse.getX(), Mouse.getY());

		PVector length = PVector.sub(mouse, bolb.location);

		while (Mouse.next()) {

			if (Mouse.getEventButton() == 0) {
				if (Mouse.getEventButtonState()) {
					if (length.mag() < bolb.radius) {
						bolb.setColor(new Color(0.6f, 0.6f, 0.6f));
						drag = true;
						dragOffset.set(length);
					}
				} else {
					bolb.setColor(new Color(0.8f, 0.8f, 0.8f));
					drag = false;
				}
			}

		}

		if (drag) {
			bolb.location.set(PVector.sub(mouse, dragOffset));
			
		}

		spring.attachTo(bolb);
		spring.applyTo(bolb);
		
		// apply another force
		PVector gravity = new PVector(.0f, -9.81f);
		bolb.applyForce(PVector.mul(gravity, bolb.mass));
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			PVector wind = new PVector(1000f, .0f);
			bolb.applyForce(wind);
		}
		
		bolb.update(delta);
	}

	@Override
	public void draw() {
		bolb.draw();
		spring.draw(10);

		axis();
	}

	public void axis() {
		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_LINES);
		glVertex2f(0, 450);
		glVertex2f(800, 450);
		glEnd();

		glColor3f(0.8f, 0.8f, 0.8f);
		glBegin(GL_LINES);
		glVertex2f(400, 0);
		glVertex2f(400, 450);
		glEnd();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation10());
	}

}
