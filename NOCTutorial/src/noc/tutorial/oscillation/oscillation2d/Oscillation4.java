package noc.tutorial.oscillation.oscillation2d;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

public class Oscillation4 extends Application {

	Mover[] mover;

	@Override
	public void setup() {
		mover = new Mover[2];
		for (int i = 0; i < mover.length; i++) {
			int freq = (int) (Math.random() * 10);
			while (freq % 2 != 0 || freq == 0) {
				freq = (int) (Math.random() * 10);
			}

			int period = (int) (Math.random() * 10);
			while (period % 2 != 0 || period == 0) {
				period = (int) (Math.random() * 10);
			}

			System.out.println(freq + ", " + period);

			mover[i] = new Mover(400, 240, freq, period);
		}
	}

	@Override
	public void draw() {
		for (Mover m : mover) {
			m.display();
		}
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Oscillation4());
	}

}
