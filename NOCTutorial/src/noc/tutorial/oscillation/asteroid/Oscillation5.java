package noc.tutorial.oscillation.asteroid;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Keyboard;

public class Oscillation5 extends Application {

	Spaceship spaceship;
	float angle;

	@Override
	public void setup() {
		spaceship = new Spaceship(400, 240);
		angle = 0.0f;
	}

	@Override
	public void update(float delta) {
		// counter clock-wise
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) angle += 10;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) angle -= 10;

		spaceship.rotate(angle);

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) spaceship.applyForce(100);

		spaceship.update(delta);

		if (spaceship.location.x < 0) spaceship.location.x = 800;
		if (spaceship.location.y < 0) spaceship.location.y = 480;
		if (spaceship.location.x > 800) spaceship.location.x = 0;
		if (spaceship.location.y > 480) spaceship.location.y = 0;
	}

	@Override
	public void draw() {
		spaceship.display();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation5());
	}

}
