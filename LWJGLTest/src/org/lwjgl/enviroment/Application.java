package org.lwjgl.enviroment;

abstract public class Application {

	public int fps = 60;

	abstract public void setup();

	abstract public void draw();

	public void update(float delta) {
	}

	public void fill() {
	}

	public void frame(int fps) {
		this.fps = fps;
	}

}
