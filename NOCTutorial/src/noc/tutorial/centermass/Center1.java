package noc.tutorial.centermass;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import project.noc.utils.PVector;

public class Center1 extends Application {

	Polygon polygon;
	Circle circle;
	Rectangle rectangle;
	
	@Override
	public void setup() {
		polygon = new Polygon(new PVector(400, 240),
				new Vertex2D[] {

				new Vertex2D(-100, 0), new Vertex2D(-10, -200), new Vertex2D(150, 50), new Vertex2D(0, 100)

		});
		
		circle = new Circle(new PVector(100, 240), 25);
	
		rectangle = new Rectangle(new PVector(600, 240), 50);		
	}

	@Override
	public void draw() {
		polygon.display();
		circle.display();
		rectangle.display();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Center1());
	}

}
