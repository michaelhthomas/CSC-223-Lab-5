package input.components.segment;

import input.components.point.PointNode;

/**
 * A 2D line segment
 * <p>
 * The order of the points does not matter.
 *
 * @author Michael Thomas
 * @author Julia Hogg
 * @date 2/14/2023
 */
public class SegmentNode {
	protected PointNode _point1;
	protected PointNode _point2;

	public PointNode getPoint1() {
		return _point1;
	}

	public PointNode getPoint2() {
		return _point2;
	}

	/**
	 * Create a new Segment with the specified points.
	 * 
	 * @param point 1
	 * @param point 2
	 */
	public SegmentNode(PointNode pt1, PointNode pt2) {
		_point1 = pt1;
		_point2 = pt2;
	}

	@Override
	public int hashCode() {
		return _point1.hashCode() + _point2.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		SegmentNode other = (SegmentNode) obj;
		return (_point1.equals(other._point1) && _point2.equals(other._point2)) ||
				(_point1.equals(other._point2) && _point2.equals(other._point1));
	}

	@Override
	public String toString() {
		return "Segment [" + _point1 + ", " + _point2 + "]";
	}
}
