package edu.uta.advancetopic.model;

import java.io.File;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.uta.advancetopic.bug.Bug;

public abstract class Model {
	protected AbstractSequenceClassifier<CoreLabel> classifier;
	public abstract AbstractSequenceClassifier<CoreLabel> getClassifier();
	protected String modelType = null;

	public void setModels(File file) {
	}

	public void loadModels(String modelPath) {
	}
	

	public abstract String[] getKeywordsList();

	// Classify key words from input text.
	public String classify(String bugDescription) {
		return classifier.classifyWithInlineXML(bugDescription);
	}

	public abstract Bug getBug();
	
	public abstract void setType();
	public String getType(){
		return modelType;
	}
	
	public abstract String getBackground();
}
