package org.lwjgl.test;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import project.noc.utils.PVector;

public class Test1 extends Application {

	@Override
	public void setup() {
	}

	@Override
	public void draw() {
		frame(30);
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		
		PVector a = new PVector(10, 0);
		
		PVector r = PVector.rotate(a, 60);
		r = PVector.rotate(r, 30);
		
		System.out.println(r.dir());
		
		setup.start(new Test1());
	}

}
