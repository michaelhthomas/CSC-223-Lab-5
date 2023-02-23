
package input.components.point;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import input.components.ComponentNode;
import utilities.io.StringUtilities;

/**
 * A database of 2D points
 *
 * @author Michael Thomas
 * @author Jake Shore
 * @author Michael Leiby
 * @author Julia Hogg
 * @date 2/14/2023
 */
public class PointNodeDatabase implements ComponentNode {

	Set<PointNode> _points;

	/**
	 * Constructs a new empty PointNodeDatabase.
	 */
	public PointNodeDatabase() {
		_points = new LinkedHashSet<>();
	}

	/**
	 * Constructs a new PointNodeDatabase, inserting all PointNodes in the given
	 * collection.
	 * 
	 * @param list List of PointNodes to initialize the database with.
	 */
	public PointNodeDatabase(List<PointNode> list) {
		_points = new LinkedHashSet<>(list);
	}

	/**
	 * Adds the given point to the database.
	 * 
	 * @param point Point to add.
	 */
	public void put(PointNode point) {
		_points.add(point);
	}

	/**
	 * Checks if the given point is present in the database.
	 * 
	 * @param point
	 * @return true if the point is present, else false.
	 */
	public boolean contains(PointNode point) {
		return _points.contains(point);
	}

	/**
	 * Checks if there is a point with the given coordinates in the database.
	 * 
	 * @param x
	 * @param y
	 * @return true if the point is present, else false.
	 */
	public boolean contains(double x, double y) {
		return _points.contains(new PointNode(x, y));
	}

	/**
	 * Returns the name of the point in the database with the same coordinates
	 * as the given point.
	 * 
	 * @param point Point whose name should be located.
	 * @return the name of the given point if it is present in the database, else
	 *         null.
	 */
	public String getName(PointNode point) {
		PointNode found = getPoint(point);
		return found != null ? found._name : null;
	}

	/**
	 * Returns the name of the point with the given coordinates in the database.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return the name of the given point if it is present in the database, else
	 *         null.
	 */
	public String getName(double x, double y) {
		return getName(new PointNode(x, y));
	}

	/**
	 * Gets the point in the database with the given name.
	 * 
	 * @param name Name to locate.
	 * @return the point whose name is equal to the given name if such a point
	 *         exists, else null.
	 */
	public PointNode getPoint(String name) {
		for (PointNode p : _points) {
			if (p.getName().equals(name))
				return p;
		}
		return null;
	}

	/**
	 * Gets the point in the database with the same coordinates as the given point.
	 * 
	 * @param point Point to locate.
	 * @return the point equal to the given point in the database if it exists,
	 *         else null.
	 */
	public PointNode getPoint(PointNode point) {
		for (PointNode p : _points) {
			if (p.equals(point))
				return p;
		}
		return null;
	}

	/**
	 * Gets the point in the database with the given coordinates.
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 * @return the point with the given coordinates the database if it exists, else
	 *         null.
	 */
	public PointNode getPoint(double x, double y) {
		return getPoint(new PointNode(x, y));
	}

	@Override
	public void unparse(StringBuilder sb, int level) {
		sb.append(StringUtilities.indent(level) + "{\n");
		for (PointNode point : _points)
			point.unparse(sb, level + 1);
		sb.append(StringUtilities.indent(level) + "}\n");
	}

	public static PointNodeDatabase fromJson(Object json) {
		JSONArray JSONpoints = (JSONArray) json;
		PointNodeDatabase points = new PointNodeDatabase();

		for (Object pointObj : JSONpoints) {
			JSONObject JSONpoint = (JSONObject) pointObj;
			PointNode point = new PointNode(
					JSONpoint.getString("name"),
					JSONpoint.getDouble("x"),
					JSONpoint.getDouble("y"));
			points.put(point);
		}

		return points;
	}

}
