package noc.tutorial.oscillation.attractor;

public class Oscillator {

	float amp, angle;
	int i;
	
	public Oscillator(float amp_) {
		amp = amp_;
		i = 0;
	}

	public float oscillate(float velocity) {
		return amp * (float) (Math.sin(Math.toRadians(angle += velocity)));
	}

}
