package input.parser;

import org.json.JSONObject;

/**
 * Parses FigureNodes from their representations in JSON files.
 * 
 * @author Michael Thomas
 * @author Michael Leiby
 * @author Julia Hogg
 * @date 2/14/2023
 */

import org.json.JSONTokener;

import input.components.*;
import input.exception.ParseException;

public class JSONParser {
	protected ComponentNode _astRoot;

	public JSONParser() {
		_astRoot = null;
	}

	private ParseException error(String message) {
		return new ParseException("Parse error: " + message);
	}

	public ComponentNode parse(String str) throws ParseException {
		// Parsing is accomplished via the JSONTokenizer class.
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject JSONroot = (JSONObject) tokenizer.nextValue();

		try {
			return parse(JSONroot);
		} catch (Exception e) {
			throw error(e.getMessage());
		}
	}

	public ComponentNode parse(JSONObject JSONroot) {
		JSONObject JSONfigure = (JSONObject) JSONroot.get("Figure");

		return FigureNode.fromJson(JSONfigure);
	}
}