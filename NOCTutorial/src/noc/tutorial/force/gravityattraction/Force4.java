package noc.tutorial.force.gravityattraction;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import project.noc.utils.PVector;

public class Force4 extends Application {

	Mover earth;
	Mover moon;
	Attraction sun;

	@Override
	public void setup() {
		sun = new Attraction(400, 240, 30);
		earth = new Mover(400, 400, 10, 3.5f);
		moon = new Mover(400, 440, 5, 7.0f);
	}

	@Override
	public void update(float delta) {
		PVector sunforce = sun.attract(earth);
		earth.applyForce(sunforce);
		
		PVector earthforce = earth.attract(moon, 0.1f, 1f);
		moon.applyForce(earthforce);
		
		earth.update(delta);
		moon.update(delta);		
	}

	@Override
	public void draw() {
		sun.display();

		earth.display();
		moon.display();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Force4());
	}

}
