package input.components.segment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import input.components.point.PointNode;

class SegmentNodeDatabaseTest {
	public SegmentNodeDatabase build() {
		//      A                                 
		//     / \                                
		//    B___C                               
		//   / \ / \                              
		//  /   X   \ 
		// D_________E
		//
		PointNode a = new PointNode("A", 3, 6);
		PointNode b = new PointNode("B", 2, 4);
		PointNode c = new PointNode("C", 4, 4);

		PointNode d = new PointNode("D", 0, 0);
		PointNode e = new PointNode("E", 6, 0);
		PointNode x = new PointNode("X", 3, 3);

		SegmentNodeDatabase db = new SegmentNodeDatabase();
				
		db.addUndirectedEdge(a, b);
		db.addUndirectedEdge(a, c);
		db.addUndirectedEdge(b, c);
		db.addUndirectedEdge(b, x);
		db.addUndirectedEdge(b, d);
		db.addUndirectedEdge(c, x);
		db.addUndirectedEdge(c, e);
		db.addUndirectedEdge(x, d);
		db.addUndirectedEdge(x, e);
		db.addUndirectedEdge(d, e);
		
		return db;
	}
	
	private SegmentNodeDatabase buildDirected() {
		//      A
		//     /|
		//	  / v
		//   B->C
		//
		PointNode a = new PointNode("A", 1, 1);
		PointNode b = new PointNode("B", 0, 0);
		PointNode c = new PointNode("C", 1, 0);
		
		Map<PointNode, Set<PointNode>> adjLists = new HashMap<PointNode, Set<PointNode>>();
		
		LinkedHashSet<PointNode> aPoint = new LinkedHashSet<PointNode>();
		LinkedHashSet<PointNode> bPoint = new LinkedHashSet<PointNode>();
		LinkedHashSet<PointNode> cPoint = new LinkedHashSet<PointNode>();
		
		aPoint.add(b);
		aPoint.add(c);
		bPoint.add(a);
		bPoint.add(c);
		
		adjLists.put(a, aPoint);
		adjLists.put(b, bPoint);
		adjLists.put(c, cPoint);
		
		SegmentNodeDatabase db = new SegmentNodeDatabase(adjLists);
	
		return db;
	}

	// Constructors

	@Test
	void segmentNodeDatabaseExistsTest() {
		SegmentNodeDatabase db = build();
		
		assertNotNull(db);
	}
	
	@Test
	void segmentNodeDatabaseUndirectedExistsTest() {
		SegmentNodeDatabase db = buildDirected();
		
		assertNotNull(db);
	}
	
	// numUndirectedEdges
	
	@Test
	void numUndirectedEdgesTest() {
		SegmentNodeDatabase db = build();
		
		assertEquals(10, db.numUndirectedEdges());
	}
	
	@Test
	void numUndirectedEdgesWithDirectedEdgeTest() {
		SegmentNodeDatabase db = buildDirected();
		
		assertEquals(1, db.numUndirectedEdges());
	}
	
	@Test
	void numUndirectedEdgesEmptyTest() {
		SegmentNodeDatabase db = new SegmentNodeDatabase();
		
		assertEquals(0, db.numUndirectedEdges());
	}
	
	// addDirectedEdge
	
	@Test
	void addDirectedEdgeTest() {
		SegmentNodeDatabase db = buildDirected();
		db.addUndirectedEdge(new PointNode("C", 0.0, 1.0), new PointNode("D", 0.0, 2.0));
		
		assertEquals(4, db.asUniqueSegmentList().size());
	}
	
	@Test
	void addDirectedEdgeRepeatTest() {
		SegmentNodeDatabase db = buildDirected();
		db.addUndirectedEdge(new PointNode("D", 0.0, 2.0), new PointNode("E", 1.0, 2.0));
		
		assertEquals(4, db.asUniqueSegmentList().size());
	}
	
	@Test
	void addDirectedEdgeTurnUndirectedToDirectedTest() {
		SegmentNodeDatabase db = buildDirected();
		db.addUndirectedEdge(new PointNode("C", 1.0, 0.0), new PointNode("B", 0.0, 0.0));
		List<SegmentNode> segments = db.asSegmentList();
		
		assertEquals("Segment [C(1.0, 0.0), B(0.0, 0.0)]", segments.get(4).toString());
		assertEquals(5, segments.size());
		assertEquals(2, db.numUndirectedEdges());
	}
    
	// addAdjacencyList
	
	@Test
	void addAdjacencyListTest() {
		//      A
		//     /|
		//	  / v
		//   B->C
		SegmentNodeDatabase db1 = new SegmentNodeDatabase();
		SegmentNodeDatabase db2 = buildDirected();
		PointNode a = new PointNode("A", 1, 1);
		PointNode b = new PointNode("B", 0, 0);
		PointNode c = new PointNode("C", 1, 0);
		List<PointNode> aPoint = new ArrayList<PointNode>();
		List<PointNode> bPoint = new ArrayList<PointNode>();
		List<PointNode> cPoint = new ArrayList<PointNode>();
		aPoint.add(b);
		aPoint.add(c);
		bPoint.add(a);
		bPoint.add(c);
		db1.addAdjacencyList(a, aPoint);
		db1.addAdjacencyList(b, bPoint);
		db1.addAdjacencyList(c, cPoint);
		List<SegmentNode> uniqueSegments1 = db1.asUniqueSegmentList();
		List<SegmentNode> uniqueSegments2 = db2.asUniqueSegmentList();
		
		for(SegmentNode segment : uniqueSegments1) {
			assertTrue(uniqueSegments2.contains(segment));
		}
	}
	
	@Test
	void addAdjacencyListRepeatTest() {
		SegmentNodeDatabase db = buildDirected();
		PointNode a = new PointNode("A", 1, 1);
		PointNode b = new PointNode("B", 0, 0);
		List<PointNode> aPoint = new ArrayList<PointNode>();
		aPoint.add(b);
		db.addAdjacencyList(a, aPoint);
		
		assertEquals(4, db.asSegmentList().size());
	}
	
	@Test
	void addAdjacencyListReverseTest() {
		SegmentNodeDatabase db = buildDirected();
		PointNode b = new PointNode("B", 0, 0);
		PointNode c = new PointNode("C", 1, 0);
		List<PointNode> bPoint = new ArrayList<PointNode>();
		bPoint.add(c);
		db.addAdjacencyList(b, bPoint);
		
		assertEquals(5, db.asSegmentList().size());
		assertEquals(2, db.numUndirectedEdges());
	}
	
	// asSegmentList
	
	@Test
	void asSegmentListTest() { 
		SegmentNodeDatabase db = build();
		List<SegmentNode> segments = db.asSegmentList();
		
		assertEquals("Segment [A(3.0, 6.0), B(2.0, 4.0)]", segments.get(0).toString());
		assertEquals("Segment [B(2.0, 4.0), A(3.0, 6.0)]", segments.get(2).toString());
		assertEquals(20, segments.size());
	}
	
	@Test
	void asSegmentListEmptyTest() { 
		SegmentNodeDatabase db = new SegmentNodeDatabase();
		List<SegmentNode> segments = db.asSegmentList();
		
		assertEquals(0, segments.size());
	}
	
	@Test
	void asSegmentListUndirectedTest() { 
		SegmentNodeDatabase db = buildDirected();
		List<SegmentNode> segments = db.asSegmentList();
		
		assertEquals("Segment [A(1.0, 1.0), B(0.0, 0.0)]", segments.get(0).toString());
		assertEquals("Segment [B(0.0, 0.0), A(1.0, 1.0)]", segments.get(2).toString());
		assertEquals(4, segments.size());
	}
	
	// asUniqueSegmentList
	
	@Test
	void asUniqueSegmentListTest() { 
		SegmentNodeDatabase db = build();
		List<SegmentNode> uniqueSegments = db.asUniqueSegmentList();
		
		// ensures there are no repeated segments
		assertEquals("Segment [A(3.0, 6.0), B(2.0, 4.0)]", uniqueSegments.get(0).toString());
		assertEquals("Segment [A(3.0, 6.0), C(4.0, 4.0)]", uniqueSegments.get(1).toString());
		assertEquals("Segment [B(2.0, 4.0), C(4.0, 4.0)]", uniqueSegments.get(2).toString());
		assertEquals("Segment [B(2.0, 4.0), X(3.0, 3.0)]", uniqueSegments.get(3).toString());
		assertEquals("Segment [B(2.0, 4.0), D(0.0, 0.0)]", uniqueSegments.get(4).toString());
		assertEquals("Segment [C(4.0, 4.0), X(3.0, 3.0)]", uniqueSegments.get(5).toString());
		assertEquals("Segment [C(4.0, 4.0), E(6.0, 0.0)]", uniqueSegments.get(6).toString());
		assertEquals("Segment [X(3.0, 3.0), D(0.0, 0.0)]", uniqueSegments.get(7).toString());
		assertEquals("Segment [X(3.0, 3.0), E(6.0, 0.0)]", uniqueSegments.get(8).toString());
		assertEquals("Segment [D(0.0, 0.0), E(6.0, 0.0)]", uniqueSegments.get(9).toString());
		assertEquals(10, uniqueSegments.size());
	}
	
	@Test
	void asUniqueSegmentListEmptyTest() { 
		SegmentNodeDatabase db = new SegmentNodeDatabase();
		List<SegmentNode> uniqueSegments = db.asSegmentList();
		
		assertEquals(0, uniqueSegments.size());
	}
	
	@Test
	void asUniqueSegmentListUndirectedTest() { 
		SegmentNodeDatabase db = buildDirected();
		List<SegmentNode> uniqueSegments = db.asUniqueSegmentList();
		
		// ensures there are no repeated segments
		assertEquals("Segment [A(1.0, 1.0), B(0.0, 0.0)]", uniqueSegments.get(0).toString());
		assertEquals("Segment [A(1.0, 1.0), C(1.0, 0.0)]", uniqueSegments.get(1).toString());
		assertEquals("Segment [B(0.0, 0.0), C(1.0, 0.0)]", uniqueSegments.get(2).toString());
		assertEquals(3, uniqueSegments.size());
	}

}