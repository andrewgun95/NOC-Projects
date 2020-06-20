package noc.tutorial.fractals.korch;

import project.noc.utils.PVector;

public class SnowFlake {

	public Korch korch1;
	public Korch korch2;
	public Korch korch3;

	public SnowFlake(float x, float y, float side) {
		PVector a = new PVector(x - side / 2, y - (float) (Math.sqrt(3.0) / 6.0) * side);
		PVector b = new PVector(x + side / 2, y - (float) (Math.sqrt(3.0) / 6.0) * side);
		PVector c = new PVector(x, y + (float) (Math.sqrt(3.0) / 3.0) * side);
		
		korch1 = new Korch(a, c);
		korch2 = new Korch(c, b);
		korch3 = new Korch(b, a);
	}
	
	public void generate(){
		korch1.generate();
		korch2.generate();
		korch3.generate();
	}
	
	public void draw(){
		korch1.draw();
		korch2.draw();
		korch3.draw();
	}

}
