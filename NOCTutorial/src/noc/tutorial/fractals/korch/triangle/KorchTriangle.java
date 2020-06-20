package noc.tutorial.fractals.korch.triangle;

import java.util.ArrayList;

import project.noc.utils.PVector;

public class KorchTriangle {

	ArrayList<KorchLine> pattern = new ArrayList<>();

	PVector a, b, c;

	public KorchTriangle(float x, float y, float side) {

		a = new PVector(x - side / 2, y - (float) (Math.sqrt(3.0) / 6.0) * side);
		b = new PVector(x + side / 2, y - (float) (Math.sqrt(3.0) / 6.0) * side);
		c = new PVector(x, y + (float) (Math.sqrt(3.0) / 3.0) * side);

		pattern.add(new KorchLine(a, b, c));
		pattern.add(new KorchLine(b, c, a));
		pattern.add(new KorchLine(c, a, b));
	}

	public void generate() {

		ArrayList<KorchLine> new_pattern = new ArrayList<>();
		for (KorchLine line : pattern) {

			float length = PVector.dist(line.a, line.b);
			PVector side = PVector.sub(line.b, line.a).nor();

			PVector n = PVector.add(line.a, PVector.mul(side, length / 3));
			PVector m = PVector.sub(line.b, PVector.mul(side, length / 3));

			PVector center = PVector.add(line.a, PVector.mul(side, length / 2));
			PVector bump = PVector.sub(center, line.c).nor();

			PVector o = PVector.add(center, PVector.mul(bump, (float) (Math.sqrt(3.0) / 6.0) * length));

			new_pattern.add(new KorchLine(n, o, m));
			new_pattern.add(new KorchLine(o, m, n));
		}

		pattern = new_pattern;
	}

	public void reset() {
		pattern.clear();
		pattern.add(new KorchLine(a, b, c));
		pattern.add(new KorchLine(b, c, a));
		pattern.add(new KorchLine(c, a, b));
	}

	public void draw() {
		for (KorchLine line : pattern) {
			line.draw();
		}
	}

}
