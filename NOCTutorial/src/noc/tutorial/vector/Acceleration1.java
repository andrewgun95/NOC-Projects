package noc.tutorial.vector;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

public class Acceleration1 extends Application {

	Mover mover;

	@Override
	public void setup() {
		mover = new Mover();
	}

	@Override
	public void update(float delta) {		
		mover.update(delta);
		edges();
	}

	public void edges() {
		if (mover.location.x < 0) mover.location.x = 800;
		if (mover.location.x > 800) mover.location.x = 0;
		if (mover.location.y < 0) mover.location.y = 480;
		if (mover.location.y > 480) mover.location.y = 0;
	}

	@Override
	public void draw() {
		mover.display();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Acceleration1());
	}

}
