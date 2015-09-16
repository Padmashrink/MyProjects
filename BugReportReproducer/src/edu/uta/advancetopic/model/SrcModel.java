package edu.uta.advancetopic.model;

import java.io.File;
import java.util.ArrayList;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.uta.advancetopic.bug.Bug;
import edu.uta.advancetopic.bug.SrcBug;

public class SrcModel extends Model {

	// Src bug's key words list
	private String[] keyWordsList = { "package", "class", "method",
			"parameter", "output", "exception","line" };
	private static SrcModel model;

	public static SrcModel getModel() {
		if (model == null) {
			model = new SrcModel();
		}
		return model;
	}

	private SrcModel() {
		// TODO Auto-generated constructor stub
		setModels(null);
		setType();
	}

	@Override
	public void setModels(File file) {
		// TODO Auto-generated method stub
		try {
			if (file != null) {
				classifier = CRFClassifier.getClassifier(file);
			} else {
				// default classifier in jar
				classifier = CRFClassifier.getClassifier(new File(
						"ner-bug-model.ser.gz"));
			}
		} catch (Throwable e) {
			// we catch Throwable, since we'd also like to be able to get an
			// OutOfMemoryError
			String message;
			if (file != null) {
				message = "Error loading CRF: " + file.getAbsolutePath();
			} else {
				message = "Error loading default CRF";
			}
			String title = "CRF Load Error";
			String msg = e.toString();
		}
	}

	@Override
	public void loadModels(String modelPath) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getKeywordsList() {
		// TODO Auto-generated method stub
		return keyWordsList;
	}

	@Override
	public Bug getBug() {
		// TODO Auto-generated method stub
		return SrcBug.getBug();
	}

	@Override
	public String getBackground() {
		// TODO Auto-generated method stub
		return classifier.backgroundSymbol();
	}

	@Override
	public AbstractSequenceClassifier<CoreLabel> getClassifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType() {
		// TODO Auto-generated method stub
		modelType = "SrcModel";
	}


}
