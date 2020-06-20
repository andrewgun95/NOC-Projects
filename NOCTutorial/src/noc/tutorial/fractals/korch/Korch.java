package noc.tutorial.fractals.korch;

import java.util.ArrayList;

import project.noc.utils.PVector;

public class Korch {

	ArrayList<KorchLine> pattern = new ArrayList<>();

	public Korch(PVector s, PVector e) {
		pattern.add(new KorchLine(s, e));
	}

	public void generate() {
		ArrayList<KorchLine> new_pattern = new ArrayList<>();

		for (KorchLine line : pattern) {

			float length = PVector.dist(line.s, line.e);
			
			PVector side = PVector.sub(line.e, line.s).nor();
			side.scl(length / 3);

			PVector a = PVector.add(line.s, side);
			PVector b = PVector.add(a, PVector.rotate(side, 60));
			PVector c = PVector.sub(line.e, side);
			
			new_pattern.add(new KorchLine(line.s, a));
			new_pattern.add(new KorchLine(a, b));
			new_pattern.add(new KorchLine(b, c));
			new_pattern.add(new KorchLine(c, line.e));
		}

		pattern = new_pattern;
	}
	
	public void draw(){
		for (KorchLine line : pattern) {
			line.draw();
		}
	}

}
