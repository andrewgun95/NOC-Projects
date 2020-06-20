package project.noc.utils;

/**
 * physics quantities which have magnitude and direction
 * 
 */
/**
 * @author Andreas
 *
 */
public class PVector {

	// vector component

	public float x, y;

	// initialize vector

	public PVector() {
		x = 0.0f;
		y = 0.0f;
	}

	public PVector(PVector vector) {
		x = vector.x;
		y = vector.y;
	}

	public PVector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * calculate vector component based of magnitude and angle and this method will override current vector component
	 * 
	 * @param magnitude
	 * @param angle
	 *            in degree
	 */
	public void calc(float magnitude, float angle) {
		// trigonometry function (a, o, h)
		// h -> magnitude
		// a -> x
		// o -> y

		// a = cos(angle) * h
		x = (float) (Math.cos(Math.toRadians((double) angle)) * magnitude);
		// b = sin(angle) * h
		y = (float) (Math.sin(Math.toRadians((double) angle)) * magnitude);
	}

	// basic vector operation

	public float mag() {
		return (float) Math.sqrt((double) (x * x + y * y));
	}

	public float magSqr() {
		return x * x + y * y;
	}

	/**
	 * normalize vector component into unit vector with magnitude equals 1
	 */
	public void norm() {
		float mag = mag();
		// square(x * x + y * y) / magnitude = 1
		// x + y / magnitude = 1
		x /= mag;
		y /= mag;
	}

	public PVector nor() {
		float mag = mag();
		// square(x * x + y * y) / magnitude = 1
		// x + y / magnitude = 1
		x /= mag;
		y /= mag;
		return this;
	}

	/**
	 * reverse vector component
	 */
	public void rev() {
		x = -x;
		y = -y;
	}

	public PVector set(PVector vector) {
		this.x = vector.x;
		this.y = vector.y;
		return this;
	}

	/**
	 * set vector component
	 * 
	 * @return vector
	 */
	public PVector set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * adding vector component by vector component
	 * 
	 * @param x
	 * @param y
	 * @return vector
	 */
	public PVector add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * adding vector by vector
	 * 
	 * @param vector
	 * @return vector
	 */
	public PVector add(PVector vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	/**
	 * subtract vector component by vector component
	 * 
	 * @param x
	 * @param y
	 * @return vector
	 */
	public PVector sub(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	/**
	 * subtract vector by vector
	 * 
	 * @param vector
	 * @return vector
	 */
	public PVector sub(PVector vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}

	/**
	 * scale vector by a scalar, some direction different length or magnitude
	 * 
	 * @return vector
	 */
	public PVector scl(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	/**
	 * vector divided by a scalar with some direction but decreasing length or magnitude
	 * 
	 * @param scalar
	 * @return vector
	 */
	public PVector div(float scalar) {
		x /= scalar;
		y /= scalar;
		return this;
	}

	public PVector limit(float max) {
		// c * c = a * a + b * b
		if ((x * x + y * y) > max * max) {
			norm();
			scl(max);
		}
		return this;
	}

	public PVector neg() {
		rev();
		return this;
	}

	public void setMag(float scalar) {
		norm();
		scl(scalar);
	}

	public PVector dir() {
		if(mag() == 0)
			throw new ArithmeticException("zero-vector has no direction");
		// same as normalize vector
		return this.scl(1.0f / mag());
	}


	// basic static operation

	/**
	 * perform add operation two vector
	 * 
	 * @param a
	 * @param b
	 * @return vector
	 */
	public static PVector add(PVector a, PVector b) {
		return new PVector(a.x + b.x, a.y + b.y);
	}

	/**
	 * perform sub operation two vector
	 * 
	 * @param a
	 * @param b
	 * @return vector
	 */
	public static PVector sub(PVector a, PVector b) {
		return new PVector(a.x - b.x, a.y - b.y);
	}

	/**
	 * perform cross product two vector, which is perpendicular with two vector.
	 * 
	 * @param a
	 * @param b
	 * @return scalar
	 */
	public static float crs(PVector a, PVector b) {
		return a.x * b.y - a.y * b.x;
	}

	/**
	 * perform dot product two vector, which is projection between two vector
	 * 
	 * @param a
	 * @param b
	 * @return scalar
	 */
	public static float dot(PVector a, PVector b) {
		return a.x * b.x + a.y * b.y;
	}

	/**
	 * perform multiplication between vector and scalar
	 * 
	 * @param vector
	 * @param scalar
	 * @return vector
	 */
	public static PVector mul(PVector vector, float scalar) {
		return new PVector(vector.x * scalar, vector.y * scalar);
	}

	/**
	 * perform division between vector and scalar
	 * 
	 * @param vector
	 * @param scalar
	 * @return vector
	 */
	public static PVector div(PVector vector, float scalar) {
		return new PVector(vector.x / scalar, vector.y / scalar);
	}

	public static float dist(PVector a, PVector b) {
		return PVector.sub(a, b).mag();
	}

	public static float dist2(PVector a, PVector b) {
		return PVector.sub(a, b).magSqr();
	}

	public static float proj(PVector a, PVector b) {
		return PVector.dot(a, b) / b.mag();
	}

	public static PVector rand() {
		PVector vector = new PVector();
		vector.calc(1, PMath.random(360));
		return vector;
	}

	public static PVector rand(float mag) {
		PVector vector = new PVector();
		vector.calc(mag, PMath.random(360));
		return vector;
	}

	public static float angle(PVector vector) {
		return (float) Math.toDegrees(Math.atan2(vector.y, vector.x));
	}

	
	public static PVector rotate(PVector vector, float angle) {			
		float x = vector.mag() * (float) (Math.cos(Math.atan2(vector.y, vector.x) + Math.toRadians(angle)));
		float y = vector.mag() * (float) (Math.sin(Math.atan2(vector.y, vector.x) + Math.toRadians(angle)));
		return new PVector(x, y);
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	@Override
	public boolean equals(Object other) {
		return this.x == ((PVector) other).x && this.y == ((PVector) other).y;
	}



}
