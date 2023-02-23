package input.components.point;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class PointNodeDatabaseTest {
  private List<PointNode> _pointList = List.of(
      new PointNode(0, 0),
      new PointNode(1, 1),
      new PointNode(0, 1));

  private List<PointNode> _pointListNamed = List.of(
      new PointNode("A", 0, 0),
      new PointNode("B", 1, 1),
      new PointNode("C", 0, 1));

  @Test
  void pointNodeDatabaseTest() {
    PointNodeDatabase test = new PointNodeDatabase();

    assertDoesNotThrow(() -> {
      test.put(new PointNode(0, 0));
    });
  }

  @Test
  void pointNodeDatabaseTestInitialList() {
    PointNodeDatabase pointDatabase = new PointNodeDatabase(_pointList);

    for (PointNode p : _pointList)
      assertTrue(pointDatabase.contains(p), "point database did not contain " + p + " but was expected to.");
  }

  @Test
  void pointNodeDatabaseTestAddContains() {
    PointNodeDatabase pointDatabase = new PointNodeDatabase();

    // add all points
    for (PointNode p : _pointList) {
      pointDatabase.put(p);
    }

    // make sure all points are present
    for (PointNode p : _pointList) {
      assertTrue(pointDatabase.contains(p), "point database did not contain " + p + " but was expected to.");
    }
  }

  @Test
  void pointNodeDatabaseTestAddContainsByCoordinates() {
    PointNodeDatabase pointDatabase = new PointNodeDatabase();

    // add all points
    for (PointNode p : _pointList) {
      pointDatabase.put(p);
    }

    // make sure all points are present
    for (PointNode p : _pointList) {
      assertTrue(pointDatabase.contains(p.getX(), p.getY()),
          "point database did not contain " + p + " but was expected to.");
    }
  }

  @Test
  void pointNodeDatabaseTestGetNameByPoint() {
    PointNodeDatabase pointDatabase = new PointNodeDatabase(_pointListNamed);

    for (PointNode p : _pointListNamed) {
      assertEquals(p.getName(), pointDatabase.getName(p));
    }
  }

  @Test
  void pointNodeDatabaseTestGetNameByCoordinates() {
    PointNodeDatabase pointDatabase = new PointNodeDatabase(_pointListNamed);

    for (PointNode p : _pointListNamed) {
      assertEquals(p.getName(), pointDatabase.getName(p.getX(), p.getY()));
    }
  }

  @Test
  void pointNodeDatabaseTestGetPointByPoint() {
    PointNodeDatabase pointDatabase = new PointNodeDatabase(_pointListNamed);

    for (PointNode p : _pointListNamed) {
      assertEquals(p, pointDatabase.getPoint(p));
    }
  }

  @Test
  void pointNodeDatabaseTestGetPointByCoordinates() {
    PointNodeDatabase pointDatabase = new PointNodeDatabase(_pointListNamed);

    for (PointNode p : _pointListNamed) {
      assertEquals(p, pointDatabase.getPoint(p.getX(), p.getY()));
    }
  }
}
