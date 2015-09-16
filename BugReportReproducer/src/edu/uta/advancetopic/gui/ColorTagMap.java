package edu.uta.advancetopic.gui;

import java.awt.Color;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.stanford.nlp.util.Generics;
import edu.uta.advancetopic.model.AppModel;
import edu.uta.advancetopic.model.SrcModel;

public class ColorTagMap {
	private static String labels[] = { "package", "class", "method",
			"parameter", "output", "exception","line" };

	private static Set<String> tagsSet = null;
	private static Color colors[] = { Color.black, Color.red, Color.green,
			Color.blue, Color.yellow, Color.gray, Color.orange };
	private static Map<String, Color> tagToColorMap = null;

	public static Map<String, Color> getColorTagMap() {
			tagToColorMap = makeTagToColorMap();
		return tagToColorMap;
	}

	// Return tag set
	public static Set<String> getTagSet() {
		if (tagsSet == null) {
			tagsSet = new HashSet<String>();
			for (String s : labels) {
				tagsSet.add(s);
			}
		}
		return tagsSet;
	}

	// Get tag set for src Model
	public static Set<String> getSrcTagSet() {
		labels = SrcModel.getModel().getKeywordsList();
		if (tagsSet == null) {
			tagsSet = new HashSet<String>();
			for (String s : labels) {
				tagsSet.add(s);
			}
		} else {
			tagsSet.clear();
			for (String s : labels) {
				tagsSet.add(s);
			}
		}
		return tagsSet;
	}

	// Get tag set for app model
	public static Set<String> getAppTagSet() {
		labels = AppModel.getModel().getKeywordsList();
		if (tagsSet == null) {
			tagsSet = new HashSet<String>();
			for (String s : labels) {
				tagsSet.add(s);
			}
		} else {
			tagsSet.clear();
			for (String s : labels) {
				tagsSet.add(s);
			}
		}
		return tagsSet;
	}

	// Build a map from tag pattern to color
	private static Map<String, Color> makeTagToColorMap() {
		Map<String, Color> result = Generics.newHashMap();

		for (int i = 0; i < labels.length; i++) {
			result.put(labels[i], colors[i % colors.length]);
		}
		return result;
	}
	public static String[] getTags() {
		return labels;
	}

}
