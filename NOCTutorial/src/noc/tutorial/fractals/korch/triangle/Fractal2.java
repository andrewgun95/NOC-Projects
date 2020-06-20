package noc.tutorial.fractals.korch.triangle;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

public class Fractal2 extends Application {

	KorchTriangle korch;
	int n = 0;
	float t = .0f;
	
	@Override
	public void setup() {
		korch = new KorchTriangle(400, 240, 400);
	}
	
	
	@Override
	public void update(float delta) {
		if ((t += delta) > 0.1f) {
			if (n < 10) {
				korch.generate();
				n++;
			}else{
				korch.reset();
				n = 0;
			}
			t = .0f;
		}
	}

	@Override
	public void draw() {
		korch.draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Fractal2());
	}

}
