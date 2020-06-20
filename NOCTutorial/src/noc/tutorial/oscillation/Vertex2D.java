package noc.tutorial.oscillation;

public class Vertex2D {

	public float x, y;

	public Vertex2D() {
	}

	public Vertex2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vertex2D add(Vertex2D v) {
		x += v.x;
		y += v.y;
		return this;
	}

	public Vertex2D div(float value) {
		x /= value;
		y /= value;
		return this;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

}
