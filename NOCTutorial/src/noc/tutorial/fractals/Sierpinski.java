package noc.tutorial.fractals;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glVertexPointer;

import project.noc.utils.PVector;

public class Sierpinski extends Application {



	@Override
	public void setup() {

	}

	@Override
	public void draw() {
		sierpinski(400, 240, 200, 25);
	}

	public void sierpinski(float x, float y, float side, float detail) {
		
		float area = 3 * side;
		float radius = (float) (Math.sqrt(3.0) / 3.0) * side;
		
		if(area < detail){	
			triangle(x, y, side);
		}else{
			PVector center = new PVector(x, y);

			PVector a = new PVector(x - side / 2, y - (float) (Math.sqrt(3.0) / 6.0) * side);			
			PVector dir_a = PVector.sub(a, center).nor();
			dir_a.scl(radius / 2);
			sierpinski(x + dir_a.x, y + dir_a.y, side / 2, detail);
			
			PVector b = new PVector(x + side / 2, y - (float) (Math.sqrt(3.0) / 6.0) * side);
			PVector dir_b = PVector.sub(b, center).nor();
			dir_b.scl(radius / 2);
			sierpinski(x + dir_b.x, y + dir_b.y, side / 2, detail);
			
			PVector c = new PVector(x, y + (float) (Math.sqrt(3.0) / 3.0) * side);
			PVector dir_c = PVector.sub(c, center).nor();
			dir_c.scl(radius / 2);
			sierpinski(x + dir_c.x, y + dir_c.y, side / 2, detail);
		}
	}

	public void triangle(float x, float y, float side) {

		glEnableClientState(GL_VERTEX_ARRAY);

		FloatBuffer vertices = BufferUtils.createFloatBuffer(6);

		vertices.put(x - side / 2);
		vertices.put(y - (float) (Math.sqrt(3.0) / 6.0) * side);
		vertices.put(x + side / 2);
		vertices.put(y - (float) (Math.sqrt(3.0) / 6.0) * side);
		vertices.put(x);
		vertices.put(y + (float) (Math.sqrt(3.0) / 3.0) * side);

		vertices.flip();

		glVertexPointer(2, 0, vertices);
		glColor3f(0.8f, 0.8f, 0.8f);
		glDrawArrays(GL_POLYGON, 0, 3);
		glColor3f(0.5f, 0.5f, 0.5f);
		glDrawArrays(GL_LINE_LOOP, 0, 3);

		glDisableClientState(GL_VERTEX_ARRAY);

	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Sierpinski());
	}

}
