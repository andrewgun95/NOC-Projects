package project.noc.utils;

public class PMatrix {

	private static float cos(float angledeg) {
		return (float) Math.cos(Math.toRadians(angledeg));
	}

	private static float sin(float angledeg) {
		return (float) Math.sin(Math.toRadians(angledeg));
	}

	public static PVector rotate2D(float angledeg, PVector vector) {
		float x = vector.x * cos(angledeg) - vector.y * sin(angledeg);
		float y = vector.x * sin(angledeg) + vector.y * cos(angledeg);
		return new PVector(x, y);
	}

	public static PVector rotate2D_i(float angledeg, PVector vector) {
		float x = vector.x * cos(angledeg) + vector.y * sin(angledeg);
		float y = -(vector.x * sin(angledeg)) + vector.y * cos(angledeg);
		return new PVector(x, y);
	}

}
