package input.components.segment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import input.components.point.PointNode;

public class SegmentNodeTest {

	@Test
	void segmentNodeTest() {
		PointNode point1 = new PointNode(0, 0);
		PointNode point2 = new PointNode(1, 1);
		SegmentNode segment = new SegmentNode(point1, point2);

		assertEquals(point1, segment.getPoint1());
		assertEquals(point2, segment.getPoint2());
	}

	@Test
	void segmentNodeNullPointsTest() {
		SegmentNode segment = new SegmentNode(null, null);

		assertEquals(null, segment.getPoint1());
		assertEquals(null, segment.getPoint2());
	}

	@Test
	void equalsNotEqualTest() {
		SegmentNode segment1 = new SegmentNode(new PointNode(0, 0), new PointNode(1, 1));
		SegmentNode segment2 = new SegmentNode(new PointNode(2, 2), new PointNode(3, 3));

		assertEquals(false, segment1.equals(segment2));
	}

	@Test
	void equalsSameTest() {
		SegmentNode segment1 = new SegmentNode(new PointNode(0, 0), new PointNode(1, 1));
		SegmentNode segment2 = new SegmentNode(new PointNode(0, 0), new PointNode(1, 1));

		assertEquals(true, segment1.equals(segment2));
	}

	@Test
	void equalsFlippedTest() {
		SegmentNode segment1 = new SegmentNode(new PointNode(0, 0), new PointNode(1, 1));
		SegmentNode segment2 = new SegmentNode(new PointNode(1, 1), new PointNode(0, 0));

		assertEquals(true, segment1.equals(segment2));
	}

	@Test
	void equalsOtherTypeTest() {
		SegmentNode segment = new SegmentNode(new PointNode(0, 0), new PointNode(1, 1));
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		assertEquals(false, segment.equals(map));
	}

	@Test
	void equalsSelfTest() {
		SegmentNode segment = new SegmentNode(new PointNode(0, 0), new PointNode(1, 1));

		assertEquals(true, segment.equals(segment));
	}

	@Test
	void equalsNullTest() {
		SegmentNode segment1 = new SegmentNode(new PointNode(0, 0), new PointNode(1, 1));
		SegmentNode segment2 = null;

		assertEquals(false, segment1.equals(segment2));
	}

	@Test
	void toStringSimpleTest() {
		SegmentNode segment = new SegmentNode(new PointNode("A", 0, 0), new PointNode("B", 1, 1));

		assertEquals("Segment [A(0.0, 0.0), B(1.0, 1.0)]", segment.toString());
	}

	@Test
	void toStringNullPointTest() {
		SegmentNode segment = new SegmentNode(new PointNode("A", 0, 0), null);

		assertEquals("Segment [A(0.0, 0.0), null]", segment.toString());
	}
}
