package noc.tutorial.oscillation.oscillation2d;

public class Oscillator2D {

	float x, y;
	float ax, ay;

	Oscillator2D(float x_, float y_) {
		x = x_;
		y = y_;
		ax = .0f;
		ay = .0f;
	}

	public float atX(float amplitude, float frequency, float period) {
		ax += (0.02f * frequency / period);

		return x + amplitude * (float) Math.sin(ax);
	}

	public float atY(float amplitude, float frequency, float period) {
		ay += (0.02f * frequency / period);

		return y + amplitude * (float) Math.sin(ay);
	}

}
