package project.noc.utils;

public class PIntersector {

	public static boolean isPointInTriangle(PVector p, PVector a, PVector b, PVector c) {
		float px1 = p.x - a.x;
		float py1 = p.y - a.y;
		boolean side12 = (b.x - a.x) * py1 - (b.y - a.y) * px1 > 0;
		if ((c.x - a.x) * py1 - (c.y - a.y) * px1 > 0 == side12) return false;
		if ((c.x - b.x) * (p.y - b.y) - (c.y - b.y) * (p.x - b.x) > 0 != side12) return false;
		return true;
	}

	public static boolean isPointInTriangle(float px, float py, float ax, float ay, float bx, float by, float cx, float cy) {
		float px1 = px - ax;
		float py1 = py - ay;
		boolean side12 = (bx - ax) * py1 - (by - ay) * px1 > 0;
		if ((cx - ax) * py1 - (cy - ay) * px1 > 0 == side12) return false;
		if ((cx - bx) * (py - by) - (cy - by) * (px - bx) > 0 != side12) return false;
		return true;
	}

	/**
	 * Determines on which side of the given line the point is. Returns -1 if the point is on the left side of the line, 0 if the point is on the line and 1 if the point is on the right side of the
	 * line. Left and right are relative to the lines direction which is linePoint1 to linePoint2.
	 * 
	 * @param linePoint1
	 * @param linePoint2
	 * @param point
	 * @return
	 */
	public static int pointLineSide(PVector linePoint1, PVector linePoint2, PVector point) {
		return (int) Math.signum((linePoint2.x - linePoint1.x) * (point.y - linePoint1.y) - (linePoint2.y - linePoint1.y) * (point.x - linePoint1.x));
	}

	public static int pointLineSide(float linePoint1X, float linePoint1Y, float linePoint2X, float linePoint2Y, float pointX, float pointY) {
		return (int) Math.signum((linePoint2X - linePoint1X) * (pointY - linePoint1Y) - (linePoint2Y - linePoint1Y) * (pointX - linePoint1X));
	}

	public static boolean isPointInPolygon(PVector[] polygon, PVector point) {
		PVector lastVertice = polygon[polygon.length - 1];
		boolean oddNodes = false;
		for (int i = 0; i < polygon.length; i++) {
			PVector vertice = polygon[i];
			if ((vertice.y < point.y && lastVertice.y >= point.y) || (lastVertice.y < point.y && vertice.y >= point.y)) {
				if (vertice.x + (point.y - vertice.y) / (lastVertice.y - vertice.y) * (lastVertice.x - vertice.x) < point.x) {
					oddNodes = !oddNodes;
				}
			}
			lastVertice = vertice;
		}
		return oddNodes;
	}

	public static boolean isPointInPolygon(float[] polygon, int offset, int count, float x, float y) {
		boolean oddNodes = false;
		int j = offset + count - 2;
		for (int i = offset, n = j; i <= n; i += 2) {
			float yi = polygon[i + 1];
			float yj = polygon[j + 1];
			if ((yi < y && yj >= y) || (yj < y && yi >= y)) {
				float xi = polygon[i];
				if (xi + (y - yi) / (yj - yi) * (polygon[j] - xi) < x) oddNodes = !oddNodes;
			}
			j = i;
		}
		return oddNodes;
	}

	public static float distanceLinePoint(float startX, float startY, float endX, float endY, float pointX, float pointY) {
		float normalLength = (float) Math.sqrt((endX - startX) * (endX - startX) + (endY - startY) * (endY - startY));
		return Math.abs((pointX - startX) * (endY - startY) - (pointY - startY) * (endX - startX)) / normalLength;
	}

	public static float distanceSegmentPoint(float startX, float startY, float endX, float endY, float pointX, float pointY) {
		return PVector.dist(nearestSegmentPoint(startX, startY, endX, endY, pointX, pointY, new PVector()), new PVector(pointX, pointY));
	}

	public static float distanceSegmentPoint(PVector start, PVector end, PVector point) {
		return PVector.dist(nearestSegmentPoint(start, end, point, new PVector()), point);
	}

	public static PVector nearestSegmentPoint(PVector start, PVector end, PVector point, PVector nearest) {
		float length2 = PVector.dist2(start, end);
		if (length2 == 0) return nearest.set(start);
		float t = ((point.x - start.x) * (end.x - start.x) + (point.y - start.y) * (end.y - start.y)) / length2;
		if (t < 0) return nearest.set(start);
		if (t > 1) return nearest.set(end);
		return nearest.set(start.x + t * (end.x - start.x), start.y + t * (end.y - start.y));
	}

	public static PVector nearestSegmentPoint(float startX, float startY, float endX, float endY, float pointX, float pointY, PVector near) {
		final float xDiff = endX - startX;
		final float yDiff = endY - startY;
		float length2 = xDiff * xDiff + yDiff * yDiff;
		if (length2 == 0) return near.set(startX, startY);
		float t = ((pointX - startX) * (endX - startX) + (pointY - startY) * (endY - startY)) / length2;
		if (t < 0) return near.set(startX, startY);
		if (t > 1) return near.set(endX, endY);
		return near.set(startX + t * (endX - startX), startY + t * (endY - startY));
	}

}
