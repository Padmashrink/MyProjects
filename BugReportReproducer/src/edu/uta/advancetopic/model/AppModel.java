package edu.uta.advancetopic.model;

import java.io.File;
import java.util.ArrayList;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.uta.advancetopic.bug.AppBug;
import edu.uta.advancetopic.bug.Bug;

public class AppModel extends Model {
	private String[] keyWordsList = { "input", "oper", "env",
			"version", "os", "output" };
	private ArrayList<String> chooseElement = new ArrayList<String>();

	private static AppModel model;

	public static AppModel getModel() {
		if (model == null) {
			model = new AppModel();
		}
		return model;
	}

	public ArrayList<String> getChoosedElement() {
		return chooseElement;
	}

	public void chooseElement(String element) {
		chooseElement.add(element);
	}

	public void deleteElement(String element) {
		if (chooseElement.contains(element)) {
			chooseElement.remove(element);
		}
	}

	private AppModel() {
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
						"ner-app-model.ser.gz"));
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
			System.err.println(message);
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
		return AppBug.getBug();
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

	public Boolean checkBug() {
		for (String ele : chooseElement) {
			if (!checkElement(ele)) {
				return false;
			}
		}
		return true;
	}
	private boolean checkElement(String ele) {
		switch (ele) {
		case "input":
			if (AppBug.getBug().getInput() == null) {
				return false;
			}
			break;
		case "oper":
			if (AppBug.getBug().getOperation() == null) {
				return false;
			}
			break;
		case "env":
			if (AppBug.getBug().getEnvironment() == null) {
				return false;
			}
			break;
		case "version":
			if (AppBug.getBug().getVersion() == null) {
				return false;
			}
			break;
		case "OS":
			if (AppBug.getBug().getOS() == null) {
				return false;
			}
			break;
		case "output":
			if (AppBug.getBug().getResult() == null) {
				return false;
			}
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void setType() {
		// TODO Auto-generated method stub
		modelType = "AppModel";
	}


}
