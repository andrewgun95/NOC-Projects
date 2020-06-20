package noc.tutorial.intro;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

public class Intro extends Application {

	private Walker walker;

	@Override
	public void setup() {
		walker = new Walker(400f, 240f, 5f);
		frame(10);
	}

	@Override
	public void draw() {
		walker.step4c();
		walker.draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Intro());
	}

}
