package edu.uta.advancetopic.gui;

import java.awt.Color;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JEditorPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Generics;
import edu.stanford.nlp.util.StringUtils;
import edu.uta.advancetopic.bug.AppBug;
import edu.uta.advancetopic.bug.Bug;
import edu.uta.advancetopic.bug.SrcBug;
import edu.uta.advancetopic.bug.GenerateTestCase;
import edu.uta.advancetopic.model.AppModel;
import edu.uta.advancetopic.model.Model;
import edu.uta.advancetopic.model.SrcModel;

public class Controller {
//	private AbstractSequenceClassifier<CoreLabel> classifier;
	private String taggedContents = null;
	private Map<String, Color> tagToColorMap;
	private Set<String> tagsSet = null;
	private static String labels[] ;
	private Bug bug;
	private Model model;
	private static boolean single = false;
	private static Controller controller;
	
	public static Controller getController() {
		if(single == false){
			controller = new Controller();
			single = true;
		}
		return controller;
	}
	
	private Controller() {
		setSrcModel();
	}
//Identify the key words from the text.
	public void extract() {
		GUI gui = GUI.getGUI();
//		bug.clearBug();
		if (!gui.getEditorPane().getContentType().equals("text/html")) {
//get text from user's input
			 DefaultStyledDocument doc = (DefaultStyledDocument) gui.getEditDoc();
		      String text = null;
		      try {
		        text = doc.getText(0, doc.getLength());
		      } catch (Exception e) {
		        e.printStackTrace();
		      }
		      //  Generate the tagged text.
		      String labeledText = model.classify(text);
		      
		      taggedContents = labeledText;
		      Set<String> tags = null;
		      if(model.getType().equals("SrcModel")){
					tags = ColorTagMap.getSrcTagSet();
					SrcBug.getBug().clearBug();
				}else {
					tags = ColorTagMap.getAppTagSet();
					AppBug.getBug().clearBug();
				}
		      String background = model.getBackground();
		      StringBuilder tagPattern = new StringBuilder();
		      for (String tag : tags) {
		        if (background.equals(tag)) { continue; }
		        if (tagPattern.length() > 0) { tagPattern.append('|'); }
		        tagPattern.append(tag);
		      }

		      Pattern startPattern = Pattern.compile("<(" + tagPattern + ")>");
		      Pattern endPattern = Pattern.compile("</(" + tagPattern + ")>");

		      String finalText = labeledText;
		      
//  Find entity from tagged text
		      Matcher m = startPattern.matcher(finalText);
		      while (m.find()) {
		        int start = m.start();
		        finalText = m.replaceFirst("");
		        m = endPattern.matcher(finalText);
		        if (m.find()) {
		          int end = m.start();
		          String tag = m.group(1);
		          
		          finalText = m.replaceFirst("");
		          AttributeSet attSet = getAttributeSet(tag);
		          try {
		            String entity = finalText.substring(start, end);
		            
		            bug.addAttr(tag, entity);
		            
		            
		            doc.setCharacterAttributes(start, entity.length(), attSet, false);
		          } catch (Exception ex) {
		            ex.printStackTrace();
		            System.exit(-1);
		          }
		        } else {
		          System.err.println("Couldn't find end pattern!");
		        }
		        m = startPattern.matcher(finalText);
		      }
		      gui.setText();
		    } else {
		String untaggedContents = gui.getText();
		if (untaggedContents == null) {
			untaggedContents = "";
		}
//		taggedContents = classifier.classifyWithInlineXML(untaggedContents);
		taggedContents = model.classify(untaggedContents);
		Set<String> tags = null;
		if(model.getType().equals("SrcModel")){
			tags = ColorTagMap.getSrcTagSet();
		}else {
			tags = ColorTagMap.getAppTagSet();
		}

		model.getBackground();
		StringBuilder tagPattern = new StringBuilder();
		for (String tag : tags) {
			if (tagPattern.length() > 0) {
				tagPattern.append('|');
			}
			tagPattern.append(tag);
		}

		Pattern startPattern = Pattern.compile("<(" + tagPattern + ")>");
		Pattern endPattern = Pattern.compile("</(" + tagPattern + ")>");

		String finalText = taggedContents;

		Matcher m = startPattern.matcher(finalText);
		while (m.find()) {
			String tag = m.group(1);
			String color = colorToHTML(tagToColorMap.get(tag));
			String newTag = "<span style=\"background-color: " + color
					+ "; color: white\">";
			finalText = m.replaceFirst(newTag);
			Matcher m1 = endPattern.matcher(finalText);
			m1.find(m.end());
			finalText = m1.replaceFirst("</span>");
			m = startPattern.matcher(finalText);
		}
		gui.setText();
		    }


		bug.generateReport();
	}


	public static String colorToHTML(Color color) {
	    String r = Integer.toHexString(color.getRed());
	    if (r.length() == 0) { r = "00"; }
	    else if (r.length() == 1) { r = "0" + r; }
	    else if (r.length() > 2) { throw new IllegalArgumentException("invalid hex color for red"+r); }

	    String g = Integer.toHexString(color.getGreen());
	    if (g.length() == 0) { g = "00"; }
	    else if (g.length() == 1) { g = "0" + g; }
	    else if (g.length() > 2) { throw new IllegalArgumentException("invalid hex color for green"+g); }

	    String b = Integer.toHexString(color.getBlue());
	    if (b.length() == 0) { b = "00"; }
	    else if (b.length() == 1) { b = "0" + b; }
	    else if (b.length() > 2) { throw new IllegalArgumentException("invalid hex color for blue"+b); }

	    return "#"+r+g+b;
	  }
	
	//Attribute set for entities
	  private AttributeSet getAttributeSet(String tag) {
		    MutableAttributeSet attr = new SimpleAttributeSet();
		    Color color = ColorTagMap.getColorTagMap().get(tag);
		    StyleConstants.setBackground(attr, color);
		    StyleConstants.setForeground(attr, Color.WHITE);
		    return attr;
		  }
	  
	  public Set<String> getTagSet(){
			if(tagsSet == null){
				tagsSet = new HashSet<String>();
				for(String s: labels){
					tagsSet.add(s);
				}
			}
			return tagsSet;
		}
	  
	  
	  //  Set the bug model for Src bug.
	  public void setSrcModel() {
		model = SrcModel.getModel();
		bug = model.getBug();
		labels = model.getKeywordsList();
	}
	  
	//  Set the bug model for App bug.
	  public void setAppModel() {
		model = AppModel.getModel();
		bug = model.getBug();
		labels = model.getKeywordsList();
	}
}