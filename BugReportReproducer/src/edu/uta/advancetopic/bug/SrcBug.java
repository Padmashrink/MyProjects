package edu.uta.advancetopic.bug;

import java.util.ArrayList;

import edu.uta.advancetopic.gui.GUI;

public class SrcBug extends Bug {
	private String packagesString = null;
	private String classname = null;
	private String method = null;
	private String output = null;
	private String exception = null;
	private int line = 0;
	private ArrayList<String> parameters = new ArrayList();
	private ResultValue result;
	private static SrcBug bug = null;

	public static SrcBug getBug() {
		if (bug == null)
			bug = new SrcBug();
		return bug;
	}

	private SrcBug() {
		super();
		setType("SrcBug");
	}

	public String getPackagesString() {
		return packagesString;
	}

	public void setPackagesString(String packagesString) {
		this.packagesString = packagesString;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public void setParameter(String parameter) {
		parameters.add(parameter);
	}

	public ArrayList<String> getParameter() {
		return parameters;
	}

	public ResultValue getResult() {
		return result;
	}

	public void setResult(ResultValue result) {
		this.result = result;
	}

	public void addAttr(String tag, String entity) {
		// TODO Auto-generated method stub
		switch (tag) {
		case "package":
			setPackagesString(entity);
			break;
		case "class":
			setClassname(entity);
			break;
		case "method":
			setMethod(entity);
			break;
		case "line":
			setLine(line);
			break;
		case "parameter":
			setParameter(entity);
			break;
		case "output":
			setOutput(entity);
			break;
		case "exception":
			setException(entity);
			break;

		default:
			break;
		}

	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@Override
	// Generate bug report
	public void generateReport() {
		if (packagesString == null || classname == null || method == null) {
			System.out.println(packagesString);
			System.out.println(classname);
			System.out.println(method);
			String message = "The bug report dosen't contain: ";
			if(packagesString==null){
				message +="package, ";
			}
			if(classname == null){
				message+="classname, ";
			}
			if(method == null){
				message+="method, ";
			}
			message.substring(0, message.length()-3);
			GUI.showBad(message);
			return;
		}
		GenerateTestCase.generate();
	}

	@Override
	public void clearBug() {
		// TODO Auto-generated method stub
		packagesString = null;
		classname = null;
		method = null;
		output = null;
		exception = null;
		line = 0;
		parameters = new ArrayList();
		result = null;
	}

}
