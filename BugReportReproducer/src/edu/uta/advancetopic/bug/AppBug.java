package edu.uta.advancetopic.bug;

import java.util.ArrayList;

import edu.uta.advancetopic.gui.GUI;
import edu.uta.advancetopic.model.AppModel;

public class AppBug extends Bug {
	private String input = null;
	private String operation = null;
	private String environment = null;
	private String version = null;
	private String OS = null;
	private String result = null;
	private static AppBug bug = null;

	public static AppBug getBug() {
		if (bug == null)
			bug = new AppBug();
		return bug;
	}

	private AppBug() {
		// TODO Auto-generated constructor stub
		super();
		setType("AppBug");
	}

	@Override
	public void addAttr(String tag, String entity) {
		// TODO Auto-generated method stub
		switch (tag) {
		case "output":
			setResult(entity);
			break;
		case "env":
			setEnvironment(entity);
			break;
		case "version":
			setVersion(entity);
			break;
		case "os":
			setOS(entity);
			break;
		case "oper":
			setOperation(entity);
			break;
		case "input":
			setInput(entity);
			break;

		default:
			break;
		}

	}

	@Override
	public void generateReport() {
		// TODO Auto-generated method stub
		if(AppModel.getModel().getChoosedElement().size()==0){
			GUI.showAppBugWaring();
		}
		else if(AppModel.getModel().checkBug()){
			GUI.showGood();
		}else{
			String message = "The bug report donsn't include: ";
			for(String s: AppModel.getModel().getChoosedElement()){
				switch (s) {
				case "input":
					message+="input, ";
					break;
				case "oper":
					message+="operation, ";
					break;
				case "env":
					message+="enviorment, ";
					break;
				case "version":
					message+="version, ";
					break;
				case "os":
					message+="OS, ";
					break;
				case "output":
					message+="output, ";
					break;

				default:
					break;
				}
			}
			message.substring(0, message.lastIndexOf(",")-1);
			GUI.showBad(message);
		}
		

	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOS() {
		return OS;
	}

	public void setOS(String oS) {
		OS = oS;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public void clearBug() {
		// TODO Auto-generated method stub
		input = null;
		operation = null;
		environment = null;
		version = null;
		OS = null;
		result = null;
	}

}
