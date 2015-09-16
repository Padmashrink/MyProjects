package edu.uta.advancetopic.bug;

public abstract class Bug {
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public abstract void clearBug();
	
	//  Add attributes to bugs
	public abstract void addAttr(String tag, String entity);
	
	public abstract void generateReport();
	

}
