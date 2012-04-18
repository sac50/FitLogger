package com.cwru.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.cwru.controller.HomeScreen;

public class GraphBuilder {
	HashMap<Integer, Double> map;
	
	public String buildHTML(HashMap<Integer, Double> map, String title) {
		this.map = map;
		DateConverter converter = new DateConverter();
		
		int l = calcLongest();
		int width = calcWidth(l * 8);
		
		String html = "<html><body>" +
			"<script type=\"text/javascript\" src=\"wz_jsgraphics.js\"></script>" +
			"<script type=\"text/javascript\" src=\"line.js\">" +
			"</script>";
		if (HomeScreen.isTablet) {
			html = html + "<div id=\"lineCanvas\" style=\"overflow: auto; position:relative;height:600px;width:" + width * 2 + "px\"></div>";
		} else {
			html = html + "<div id=\"lineCanvas\" style=\"overflow: auto; position:relative;height:300px;width:" + width + "px\"></div>";
		}
			html = html + "<script type=\"text/javascript\">" +
			"var g = new line_graph();";
		
		Iterator<Entry<Integer, Double>> it = this.map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			if (pairs.getKey().toString().length() == 8) {
				html = html + "g.add('" + converter.intDateToString(Integer.parseInt(pairs.getKey().toString())) + "', " + pairs.getValue() + ");";
			} else {
				html = html + "g.add('" + pairs.getKey() + "', " + pairs.getValue() + ");";
			}
		}
		if (HomeScreen.isTablet) {
			if (this.map.size() < 8 && l < 4) {
				html = html + "g.render(\"lineCanvas\", \"" + title + "\", 450, " + 360 / this.map.size() + ");" +
						"</script></body></html>";
			}
			else {
				html = html + "g.render(\"lineCanvas\", \"" + title + "\", 450, " + l * 14 + ");" +
						"</script></body></html>";
			}
		} else {
			if (this.map.size() < 8 && l < 4) {
				html = html + "g.render(\"lineCanvas\", \"" + title + "\", 250, " + 180 / this.map.size() + ");" +
						"</script></body></html>";
			}
			else {
				html = html + "g.render(\"lineCanvas\", \"" + title + "\", 250, " + l * 7 + ");" +
						"</script></body></html>";
			}
		}
		
		return html;
	}
	
	/**
	 * Determines which x-axis label is the longest and returns its length.
	 * 
	 * @return int longest
	 */
	private int calcLongest() {
		int longest = 2;
		
		Iterator<Entry<Integer, Double>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			if (pairs.getKey().toString().length() > longest) {
				longest = pairs.getKey().toString().length() + 2;
			}
		}
		
		return longest;
	}
	
	/**
	 * Determines how wide the html segment containing the graph should be.
	 * 
	 * @param l
	 * @return int width
	 */
	private int calcWidth(int l) {
		int min = 400;
		int incr = 100;
		int size = map.size();
		while (size >= 12) {
			incr = incr + 100 + l * 2;
			size = size - 12;
		}
		int width = l * map.size() + incr;
		return width > min ? width : min;
	}
}