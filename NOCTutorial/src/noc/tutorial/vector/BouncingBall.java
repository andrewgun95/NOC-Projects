package noc.tutorial.vector;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

public class BouncingBall extends Application {

	Ball ball;

	@Override
	public void setup() {
		frame(60);
		ball = new Ball();
	}

	@Override
	public void draw() {
		ball.update();
		ball.draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new BouncingBall());
	}
}
