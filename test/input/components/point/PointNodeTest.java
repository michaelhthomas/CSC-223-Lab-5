package input.components.point;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PointNodeTest {
  @Test
  void pointNodeTest() {
    PointNode test = new PointNode(0, 0);

    assertEquals(PointNode.ANONYMOUS, test.getName());
    assertEquals(0, test.getX());
    assertEquals(0, test.getY());
  }

  @Test
  void pointNodeNameTest() {
    PointNode test = new PointNode("A", 0, 0);

    assertEquals("A", test.getName());
    assertEquals(0, test.getX());
    assertEquals(0, test.getY());
  }

  @Test
  void pointNodeEqualsTest() {
    PointNode test1 = new PointNode(5, 2.5);
    PointNode test2 = new PointNode(5, 2.5);
    PointNode test3 = new PointNode(2.5, 5);

    // exactly the same point
    assertTrue(test1.equals(test1), "points should have been equal, but were not");
    assertTrue(test3.equals(test3), "points should have been equal, but were not");

    // same coordinates
    assertTrue(test1.equals(test2), "points should have been equal, but were not");
    assertTrue(test2.equals(test1), "points should have been equal, but were not");

    // different coordinates
    assertFalse(test1.equals(test3), "points should not have been equal, but were");
    assertFalse(test3.equals(test2), "points should not have been equal, but were");
  }

  @Test
  void pointNodeEqualsNullTest() {
    PointNode test = new PointNode(0, 0);

    // equality to null
    assertDoesNotThrow(() -> {
      assertFalse(
          test.equals(null),
          "checking equality with null returned true");
    }, "checking equality with null threw an exception");
  }

  @Test
  void pointNodeEqualsNonPointNodeTest() {
    PointNode test = new PointNode(0, 0);

    // equality to incorrect object type
    assertDoesNotThrow(() -> {
      assertFalse(
          test.equals("test"),
          "checking equality with non-PointNode object returned true");
    }, "checking equality with non-PointNode object threw an exception");
  }

  @Test
  void pointNodeEqualsEpsilonTest() {
    PointNode test1 = new PointNode(Math.PI, Math.E);
    PointNode test2 = new PointNode(3.14159265359, 2.718281828);

    assertTrue(test1.equals(test2), "double equality test failed");
    assertTrue(test2.equals(test1), "double equality test failed");
  }

  @Test
  void pointNodeEqualsDifferentNamesTest() {
    // ensure that points with different names are still equal
    PointNode test1 = new PointNode("A", 1, 2);
    PointNode test2 = new PointNode("B", 1, 2);

    assertEquals(
        test1,
        test2,
        "PointNodes with different names should have been equal, but were not.");
  }

  @Test
  void pointNodeHashCodesAreEqual() {
    PointNode test1 = new PointNode(5, 2.5);
    PointNode test2 = new PointNode(5, 2.5);
    PointNode test3 = new PointNode(10, 15);

    // exactly the same point
    assertTrue(test1.hashCode() == test1.hashCode(), "point hash codes should have been equal, but were not");
    assertTrue(test3.hashCode() == test3.hashCode(), "point hash codes should have been equal, but were not");

    // same coordinates
    assertTrue(test1.hashCode() == test2.hashCode(), "point hash codes should have been equal, but were not");
    assertTrue(test2.hashCode() == test1.hashCode(), "point hash codes should have been equal, but were not");

    // different coordinates
    assertFalse(test1.hashCode() == test3.hashCode(), "point hash codes should not have been equal, but were");
    assertFalse(test3.hashCode() == test2.hashCode(), "point hash codes should not have been equal, but were");
  }

  @Test
  void pointNodeToStringNoNameTest() {
    PointNode test = new PointNode(5, 8);

    assertEquals("(5.0, 8.0)", test.toString());
  }

  @Test
  void pointNodeToStringWithNameTest() {
    PointNode test = new PointNode("A", 5, 8);

    assertEquals("A(5.0, 8.0)", test.toString());
  }
}
