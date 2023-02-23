package input.components.segment;

import input.components.ComponentNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import utilities.io.StringUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

/**
 * A database of undirected and directed edges stored as adjacency lists.
 *
 * @author Michael Thomas
 * @author Jake Shore
 * @author Michael Leiby
 * @author Julia Hogg
 * @date 2/14/2023
 */
public class SegmentNodeDatabase implements ComponentNode {

	Map<PointNode, Set<PointNode>> _adjLists;

	/**
	 * Constructs a new empty PointSegmentDatabase.
	 */
	public SegmentNodeDatabase() {
		_adjLists = new HashMap<PointNode, Set<PointNode>>();
	}

	/**
	 * Constructs a new PointSegmentDatabase, using a new map of adjacency lists.
	 * 
	 * @param adjLists Map of adjacency lists
	 */
	public SegmentNodeDatabase(Map<PointNode, Set<PointNode>> adjLists) {
		_adjLists = new HashMap<PointNode, Set<PointNode>>(adjLists);
	}

	/**
	 * Calculates the number of undirected edges in the SegmentNodeDatabase.
	 * 
	 * @return number of undirected edges
	 */
	public int numUndirectedEdges() {
		int numOfUndirectedEdges = 0;
		Set<SegmentNode> segments = new LinkedHashSet<SegmentNode>();

		// counts the number of unique edges
		for (Entry<PointNode, Set<PointNode>> entry : _adjLists.entrySet()) {
			for (PointNode point : entry.getValue()) {
				SegmentNode segment = new SegmentNode(entry.getKey(), point);
				boolean added = segments.add(segment);
				if (!added) {
					numOfUndirectedEdges++;
				}
			}
		}
		return numOfUndirectedEdges;
	}

	/**
	 * Adds a directed edge to the SegmentNodeDatabase.
	 * 
	 * @param point1
	 * @param point2
	 */
	private void addDirectedEdge(PointNode point1, PointNode point2) {
		Set<PointNode> connectedPoints = _adjLists.get(point1);
		if (connectedPoints == null) {
			// creates adjacency list with one edge and adds to adjacency lists
			LinkedHashSet<PointNode> newPoint = new LinkedHashSet<PointNode>();
			newPoint.add(point2);
			_adjLists.put(point1, newPoint);
		} else {
			// adds to pre-existing adjacency list
			connectedPoints.add(point2);
		}
	}

	/**
	 * Adds an undirected edge to the SegmentNodeDatabase.
	 * 
	 * @param point1
	 * @param point2
	 */
	public void addUndirectedEdge(PointNode point1, PointNode point2) {
		addDirectedEdge(point1, point2);
		addDirectedEdge(point2, point1);
	}

	/**
	 * Adds a new adjacency list to the SegmentNodeDatabase.
	 * 
	 * @param point
	 * @param adjPoints
	 */
	public void addAdjacencyList(PointNode point, List<PointNode> adjPoints) {
		for (PointNode connectedPoint : adjPoints) {
			addUndirectedEdge(point, connectedPoint);
		}
	}

	/**
	 * returns a list of segmentNodes for each directed edge in the adjacency list.
	 * 
	 * @return List of SegmentNodes
	 */
	public List<SegmentNode> asSegmentList() {
		List<SegmentNode> segments = new ArrayList<SegmentNode>();

		// adds undirected edge to list of segment nodes
		for (Entry<PointNode, Set<PointNode>> entry : _adjLists.entrySet()) {
			for (PointNode point : entry.getValue()) {
				segments.add(new SegmentNode(entry.getKey(), point));
			}
		}
		return segments;
	}

	/**
	 * returns a list of segmentNodes from the SegmentNodeDatabase only
	 * counting each undirected edge once.
	 * 
	 * @return List of unique SegmentNodes
	 */
	public List<SegmentNode> asUniqueSegmentList() {

		List<SegmentNode> segments = new ArrayList<SegmentNode>();
		// adds undirected edges to list of segment nodes if not already in it
		for (Entry<PointNode, Set<PointNode>> entry : _adjLists.entrySet()) {
			for (PointNode point : entry.getValue()) {
				SegmentNode segment = new SegmentNode(entry.getKey(), point);
				if (!segments.contains(segment)) {
					segments.add(segment);
				}
			}
		}
		return segments;
	}

	public static SegmentNodeDatabase fromJson(Object json, PointNodeDatabase points) {
		JSONArray JSONsegments = (JSONArray) json;
		SegmentNodeDatabase segments = new SegmentNodeDatabase();

		for (Object segmentObj : JSONsegments) {
			JSONObject JSONsegment = (JSONObject) segmentObj;
			String rootPointName = JSONsegment.names().getString(0);
			PointNode rootPoint = points.getPoint(rootPointName);
			JSONArray adjacentPointNames = JSONsegment.getJSONArray(rootPointName);
			List<PointNode> adjacentPoints = new ArrayList<>();

			for (Object pointNameObj : adjacentPointNames) {
				String pointName = (String) pointNameObj;
				adjacentPoints.add(points.getPoint(pointName));
			}

			segments.addAdjacencyList(rootPoint, adjacentPoints);
		}

		return segments;
	}

	@Override
	public void unparse(StringBuilder sb, int level) {
		sb.append(StringUtilities.indent(level) + "{\n");
		for (Map.Entry<PointNode, Set<PointNode>> entry : _adjLists.entrySet()) {
			sb.append(StringUtilities.indent(level + 1) + entry.getKey().getName());
			sb.append(" :");
			for(PointNode value : entry.getValue()) {
				sb.append(" " + value.getName());
			}
			sb.append("\n");
		}
		sb.append(StringUtilities.indent(level) + "}\n");
	}

}
