package input.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import input.components.ComponentNode;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.exception.ParseException;

/**
 * Simple test cases for JSONParser
 * 
 * @author Michael Thomas
 * @author Michael Leiby
 * @Author Julia Hogg
 * 
 * @date 2/21/2023
 */

class JSONParserTest {
	public static String getFigureJSON(String filename) {
		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);

		return figureStr;
	}

	public static ComponentNode getFigureFromJson(String json) {
		JSONParser parser = new JSONParser();
		return parser.parse(json);
	}

	public static ComponentNode runFigureParseTest(String filename) {
		String figureStr = getFigureJSON(filename);
		return getFigureFromJson(figureStr);
	}

	public static Stream<String> provideStringsForCompareJsonToUnparse() {
		File f = new File("testFiles/");
		return Arrays.stream(f.list()).map(s -> {
			return "testFiles/" + s;
		});
	}

	@Test
	void emptyJsonStringTest() {
		JSONParser parser = new JSONParser();

		assertThrows(ParseException.class, () -> {
			parser.parse("{}");
		});
	}

	@ParameterizedTest
	@MethodSource("provideStringsForCompareJsonToUnparse")
	void compareJsonToUnparseTest(String filename) {
		System.out.println(filename);

		String nodeJson = getFigureJSON(filename);

		System.out.println("JSON representation: \n" + nodeJson);

		ComponentNode node = getFigureFromJson(nodeJson);

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);

		System.out.println("Unwrapped: \n" + sb.toString());
	}

	@Test
	void singleTriangleTest() {
		ComponentNode node = JSONParserTest.runFigureParseTest("testFiles/single_triangle.json");

		assertTrue(node instanceof FigureNode);
		FigureNode figNode = (FigureNode) node;

		List<PointNode> trianglePoints = List.of(
				new PointNode(0, 0),
				new PointNode(1, 1),
				new PointNode(1, 0));

		assertEquals("Right Triangle in the first quadrant.", figNode.getDescription());
		for (PointNode p : trianglePoints)
			assertTrue(figNode.getPointsDatabase().contains(p));
		assertEquals(3, figNode.getSegments().asUniqueSegmentList().size());
	}

	@Test
	void fivePointStarTest() {
		ComponentNode node = JSONParserTest.runFigureParseTest("testFiles/star.json");
		assertTrue(node instanceof FigureNode);
		FigureNode figNode = (FigureNode) node;

		List<PointNode> starPoints = List.of(
				new PointNode(0, 13),
				new PointNode(20, 13),
				new PointNode(3, 0),
				new PointNode(10, 20),
				new PointNode(17, 0));

		assertEquals("5-pointed star construction.", figNode.getDescription());
		for (PointNode p : starPoints)
			assertTrue(figNode.getPointsDatabase().contains(p));
		assertEquals(5, figNode.getSegments().asUniqueSegmentList().size());
	}

	@Test
	void perfectOctagonTest() {
		ComponentNode node = JSONParserTest.runFigureParseTest("testFiles/perfect_octagon.json");
		assertTrue(node instanceof FigureNode);
		FigureNode figNode = (FigureNode) node;

		List<PointNode> octagonPoints = List.of(
				new PointNode(2, 1),
				new PointNode(0, 3),
				new PointNode(0, 7),
				new PointNode(2, 9),
				new PointNode(6, 9),
				new PointNode(8, 7),
				new PointNode(8, 3),
				new PointNode(6, 1));

		assertEquals("Perfect Octagon in the first quadrant.", figNode.getDescription());
		for (PointNode p : octagonPoints)
			assertTrue(figNode.getPointsDatabase().contains(p));
		assertEquals(13, figNode.getSegments().asUniqueSegmentList().size());
	}
}
