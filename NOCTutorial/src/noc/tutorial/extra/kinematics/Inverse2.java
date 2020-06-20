package noc.tutorial.extra.kinematics;

import org.lwjgl.enviroment.Application;
import org.lwjgl.enviroment.LWJGLSetup;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glVertex2f;

import project.noc.utils.PVector;

public class Inverse2 extends Application {

	Tentacle tentacle;

	@Override
	public void setup() {
		tentacle = new Tentacle(400, 0, 10, 50);
	}

	@Override
	public void draw() {
		float x = Mouse.getX();
		float y = Mouse.getY();

		tentacle.follow(new PVector(x, y), true);

		tentacle.draw();
	}

	public static void main(String[] args) {
		LWJGLSetup setup = new LWJGLSetup(800, 480);
		setup.start(new Inverse2());
	}

	class Tentacle {

		Node[] nodes;

		float x, y;
		float length;

		Tentacle(float x, float y, float length, int segments) {
			this.x = x;
			this.y = y;
			this.length = length;

			nodes = new Node[segments];

			// construct
			Node current = new Node(x, y);
			for (int i = 0; i < nodes.length; i++) {
				nodes[i] = current;

				Node next = new Node(current.x, current.y + length);
				current.next = next;
				next.prev = current;

				current = next;
			}
		}

		void follow(PVector target, boolean fixed) {
			Node tail = nodes[nodes.length - 1];
			tail.set(target);

			Node node = tail.prev;
			while (node != null) {
				PVector a = node.next;
				PVector b = node;

				PVector dir = PVector.sub(b, a);
				dir.norm();

				node.set(PVector.add(a, dir.scl(length)));

				node = node.prev;

				if (fixed) {
					if (node.equals(nodes[0])) {
						reconstruct();
						break;
					}
				}
			}
		}

		void reconstruct() {
			Node head = nodes[0];

			Node node = head.next;
			while (node != null) {
				PVector a = node.prev;
				PVector b = node;

				PVector dir = PVector.sub(b, a);
				dir.norm();

				node.set(PVector.add(a, dir.scl(length)));

				node = node.next;
			}
		}

		void draw() {
			glColor3f(0.8f, 0.8f, 0.8f);
			glLineWidth(2.0f);
			glBegin(GL_LINE_STRIP);
			for (PVector point : nodes) {
				glVertex2f(point.x, point.y);
			}
			glEnd();

			glColor3f(0.5f, 0.5f, 0.5f);
			glPointSize(3.0f);
			glBegin(GL_POINTS);
			for (PVector point : nodes) {
				glVertex2f(point.x, point.y);
			}
			glEnd();
		}

		class Node extends PVector {

			Node prev;
			Node next;

			Node(float x, float y) {
				this.x = x;
				this.y = y;

				prev = null;
				next = null;
			}

		}

	}
}
