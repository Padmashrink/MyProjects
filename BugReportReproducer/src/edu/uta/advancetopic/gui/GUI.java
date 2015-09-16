package edu.uta.advancetopic.gui;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import edu.stanford.nlp.util.StringUtils;
import edu.uta.advancetopic.model.AppModel;
import edu.uta.advancetopic.model.Model;
import edu.uta.advancetopic.model.SrcModel;

public class GUI {
	private static Controller controller = Controller.getController();
	private static GUI gui = null;

	private static MutableAttributeSet defaultAttrSet = new SimpleAttributeSet();
	private JFileChooser fileChooser = new JFileChooser();
	private JFrame frame;
	private JEditorPane editorPane;
	private JToolBar tagPanel;
	private static final int HEIGHT = 600;
	private static final int WIDTH = 650;
	private ActionListener actor = new ActionPerformer();

	private File loadedFile;
	private String taggedContents = null;
	private JMenuItem saveUntagged = null;
	private JMenuItem saveTaggedAs = null;

	private JButton extractButton = null;
	// private JMenuItem extract = null;

	private static final String initText = "";

	private static final int FONTSIZE = 22;

	private GUI() {
	}

	public static GUI getGUI() {
		if (gui == null) {
			gui = new GUI();
		}
		return gui;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			gui = getGUI();
			gui.createAndShowGUI();
		});
	}

	private void createAndShowGUI() {
		// TODO Auto-generated method stub
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		frame = new JFrame("Bug Report Reproducer");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));

		frame.setJMenuBar(addMenuBar());
		tagPanel = new JToolBar(SwingConstants.VERTICAL);
		tagPanel.setFloatable(false);
		frame.getContentPane().add(tagPanel, BorderLayout.EAST);

		Set<String> tags = ColorTagMap.getTagSet();

		for (String tag : tags) {
			Color color = ColorTagMap.getColorTagMap().get(tag);
			JButton b = new JButton(tag, new ColorIcon(color));
			tagPanel.add(b);
		}

		ButtonGroup modelGroup = new ButtonGroup();

		JRadioButton srcModelButton = new JRadioButton("srcBug");
		srcModelButton.setSelected(true);
		srcModelButton.addActionListener(actor);
		JRadioButton appModelButton = new JRadioButton("appBug");
		appModelButton.addActionListener(actor);
		modelGroup.add(srcModelButton);
		modelGroup.add(appModelButton);

		tagPanel.add(srcModelButton);
		tagPanel.add(appModelButton);

		tagPanel.revalidate();
		tagPanel.repaint();

		buildContentPanel();
		buildExtractButton();

		extractButton.setEnabled(true);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	// create menu bar
	private JMenuBar addMenuBar() {
		JMenuBar menubar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);

		JMenu editMenu = new JMenu("Edit");
		menubar.add(editMenu);
		/**
		 * FILE MENU
		 */

		JMenuItem openFile = new JMenuItem("Open File");
		openFile.setMnemonic('O');
		openFile.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F, InputEvent.CTRL_MASK));
		openFile.addActionListener(actor);
		fileMenu.add(openFile);

		fileMenu.add(new JSeparator());

		saveUntagged = new JMenuItem("Save Untagged File");
		saveUntagged.setMnemonic('S');
		saveUntagged.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_S, InputEvent.CTRL_MASK));
		saveUntagged.addActionListener(actor);
		saveUntagged.setEnabled(false);
		fileMenu.add(saveUntagged);

		JMenuItem saveUntaggedAs = new JMenuItem("Save Untagged File As ...");
		saveUntaggedAs.setMnemonic('U');
		saveUntaggedAs.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_U, InputEvent.CTRL_MASK));
		saveUntaggedAs.addActionListener(actor);
		fileMenu.add(saveUntaggedAs);

		saveTaggedAs = new JMenuItem("Save Tagged File As ...");
		saveTaggedAs.setMnemonic('T');
		saveTaggedAs.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_T, InputEvent.CTRL_MASK));
		saveTaggedAs.addActionListener(actor);
		saveTaggedAs.setEnabled(false);
		fileMenu.add(saveTaggedAs);

		fileMenu.add(new JSeparator());

		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic('x');
		exit.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		exit.addActionListener(actor);
		fileMenu.add(exit);

		/**
		 * EDIT MENU
		 */

		JMenuItem cut = new JMenuItem("Cut");
		cut.setMnemonic('X');
		cut.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X,
				InputEvent.CTRL_MASK));
		cut.addActionListener(actor);
		editMenu.add(cut);

		JMenuItem copy = new JMenuItem("Copy");
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_C, InputEvent.CTRL_MASK));
		copy.addActionListener(actor);
		editMenu.add(copy);

		JMenuItem paste = new JMenuItem("Paste");
		paste.setMnemonic('V');
		paste.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_V, InputEvent.CTRL_MASK));
		paste.addActionListener(actor);
		editMenu.add(paste);

		JMenuItem clear = new JMenuItem("Clear");
		clear.setMnemonic('C');
		clear.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_L, InputEvent.CTRL_MASK));
		clear.addActionListener(actor);
		editMenu.add(clear);

		/**
		 * CLASSIFIER MENU
		 */

		return menubar;
	}

	private class ActionPerformer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String com = e.getActionCommand();

			switch (com) {
			case "Open File": {
				File file = getFile(true);
				if (file != null) {
					openFile(file);
				}
				break;
			}
			case "Load URL":
				String url = getURL();
				if (url != null) {
					openURL(url);
				}
				break;
			case "Exit":
				exit();
				break;
			case "Clear":
				clearDocument();
				break;
			case "Cut":
				cutDocument();
				break;
			case "Copy":
				copyDocument();
				break;
			case "Paste":
				pasteDocument();
				break;
			case "Run Classify":
				controller.extract();
				break;
			case "Save Untagged File":
				saveUntaggedContents(loadedFile);
				break;
			case "Save Untagged File As ...":
				saveUntaggedContents(getFile(false));
				break;
			case "Save Tagged File As ...":
				File f = getFile(false);
				if (f != null) {
					// i.e., they didn't cancel out of the file dialog
					taggedContents = editorPane.getText();
					saveFile(f, taggedContents);
				}
				break;
			case "srcBug":
				controller.setSrcModel();
				setSrcTag();
				break;
			case "appBug":
				controller.setAppModel();
				setAppTag();
				break;

			default:
				System.err.println("Unknown Action: " + e);
				break;
			}
		}
	}

	public File getFile(boolean open) {
		File file = null;
		int returnVal;
		if (open) {
			returnVal = fileChooser.showOpenDialog(frame);
		} else {
			returnVal = fileChooser.showSaveDialog(frame);
		}
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (open && !checkFile(file)) {
				file = null;
			}
		}
		return file;
	}

	public void saveUntaggedContents(File file) {
		try {
			String contents;
			if (editorPane.getContentType().equals("text/html")) {
				contents = editorPane.getText();
			} else {
				Document doc = editorPane.getDocument();
				contents = doc.getText(0, doc.getLength());
			}
			saveFile(file, contents);
			saveUntagged.setEnabled(true);
			loadedFile = file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void saveFile(File file, String contents) {
		StringUtils.printToFile(file, contents);
	}

	public String getURL() {
		String url = JOptionPane.showInputDialog(frame, "URL: ", "Load URL",
				JOptionPane.QUESTION_MESSAGE);
		return url;
	}

	public boolean checkFile(File file) {
		if (file.isFile()) {
			fileChooser.setCurrentDirectory(file.getParentFile());
			return true;
		} else {
			String message = "File Not Found: " + file.getAbsolutePath();
			displayError("File Not Found Error", message);
			return false;
		}
	}

	public void displayError(String title, String message) {
		JOptionPane.showMessageDialog(frame, message, title,
				JOptionPane.ERROR_MESSAGE);
	}

	public void openFile(File file) {
		openURL(file.toURI().toString());
		loadedFile = file;
		saveUntagged.setEnabled(true);
	}

	public void openURL(String url) {
		try {
			editorPane.setPage(url);
		} catch (Exception e) {
			System.err.println("Error loading |" + url + "|");
			e.printStackTrace();
			displayError("Error Loading URL " + url, "Message: " + e.toString());
			return;
		}
		loadedFile = null;
		String text = editorPane.getText();
		taggedContents = null;
		if (!editorPane.getContentType().equals("text/html")) {
			editorPane.setContentType("text/rtf");
			Document doc = editorPane.getDocument();
			try {
				doc.insertString(0, text, defaultAttrSet);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			editorPane.revalidate();
			editorPane.repaint();
			editorPane.setEditable(true);
		} else {
			editorPane.setEditable(false);
			editorPane.getText();
		}

		saveUntagged.setEnabled(false);
		saveTaggedAs.setEnabled(false);
	}

	static void exit() {
		// ask if they're sure?
		System.exit(-1);
	}

	public void clearDocument() {
		editorPane.setContentType("text/rtf");
		Document doc = new DefaultStyledDocument();
		editorPane.setDocument(doc);
		
		 StyleConstants.setFontFamily(defaultAttrSet, "Lucinda Sans Unicode");

		try {
			doc.insertString(0, " ", defaultAttrSet);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		editorPane.setEditable(true);
		editorPane.revalidate();
		editorPane.repaint();

		saveUntagged.setEnabled(false);
		saveTaggedAs.setEnabled(false);

		taggedContents = null;
		loadedFile = null;

	}

	public void cutDocument() {
		editorPane.cut();
		saveTaggedAs.setEnabled(false);
	}

	public void copyDocument() {
		editorPane.copy();
	}

	public void pasteDocument() {
		editorPane.paste();
		saveTaggedAs.setEnabled(false);
	}

	public String getText() {
		// TODO Auto-generated method stub
		return editorPane.getText();
	}

	private void buildContentPanel() {

		editorPane = new JEditorPane();
		editorPane.setContentType("text/rtf");
		editorPane.addKeyListener(new InputListener());

		StyleConstants.setFontFamily(defaultAttrSet, "Lucida Sans");
		StyleConstants.setFontSize(defaultAttrSet, FONTSIZE);

		Document doc = new DefaultStyledDocument();

		editorPane.setDocument(doc);
		try {
			doc.insertString(0, initText, defaultAttrSet);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		JScrollPane scrollPane = new JScrollPane(editorPane);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		editorPane.setEditable(true);
	}

	private class InputListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
			saveTaggedAs.setEnabled(false);
		}

	}

	private void buildExtractButton() {
		if (extractButton == null) {
			JPanel buttonPanel = new JPanel();
			extractButton = new JButton("Run Classify");
			buttonPanel.add(extractButton);
			frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			extractButton.addActionListener(actor);
		}
	}

	public void setText() {
		DefaultStyledDocument doc = (DefaultStyledDocument) editorPane
				.getDocument();

		editorPane.setDocument(doc);
		try {
			doc.insertString(0, initText, defaultAttrSet);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		editorPane.revalidate();
		editorPane.repaint();
	}

	public Document getEditDoc() {
		return editorPane.getDocument();
	}

	public JEditorPane getEditorPane() {
		return editorPane;
	}

	// This single choice button used to let user choose the src bug type.
	// Source bug means we can generate the test cases from this bug.
	private void setSrcTag() {
		tagPanel.removeAll();
		Set<String> tags = ColorTagMap.getSrcTagSet();

		for (String tag : tags) {
			Color color = ColorTagMap.getColorTagMap().get(tag);
			JButton b = new JButton(tag, new ColorIcon(color));
			tagPanel.add(b);
		}
		ButtonGroup modelGroup = new ButtonGroup();

		JRadioButton srcModelButton = new JRadioButton("srcBug");
		srcModelButton.setSelected(true);
		srcModelButton.addActionListener(actor);
		JRadioButton appModelButton = new JRadioButton("appBug");
		appModelButton.addActionListener(actor);
		modelGroup.add(srcModelButton);
		modelGroup.add(appModelButton);

		tagPanel.add(srcModelButton);
		tagPanel.add(appModelButton);

		tagPanel.revalidate();
		tagPanel.repaint();
	}

	// This single choice button used to let user choose the App bug type.
	// App bug means user find a bug from some operation, but we can't generate
	// the test cases from this bug.
	private void setAppTag() {
		tagPanel.removeAll();
		Set<String> tags = ColorTagMap.getAppTagSet();
		for (String tag : tags) {
			JToolBar toolBar = new JToolBar();
			Color color = ColorTagMap.getColorTagMap().get(tag);
			JButton button = new JButton(tag, new ColorIcon(color));
			JCheckBox box = new JCheckBox();
			toolBar.add(button);
			toolBar.add(box);
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT,10,15));
			toolBar.setSize(50, 0);
			box.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if(box.isSelected()){
						AppModel.getModel().chooseElement(tag);
					}else{
						AppModel.getModel().deleteElement(tag);
					}
				}
			});
			tagPanel.add(toolBar);
		}
		ButtonGroup modelGroup = new ButtonGroup();
		JRadioButton srcModelButton = new JRadioButton("srcBug");
		// srcModelButton.setSelected(true);
		srcModelButton.addActionListener(actor);
		JRadioButton appModelButton = new JRadioButton("appBug");
		appModelButton.setSelected(true);
		appModelButton.addActionListener(actor);
		modelGroup.add(srcModelButton);
		modelGroup.add(appModelButton);

		tagPanel.add(srcModelButton);
		tagPanel.add(appModelButton);
		
		tagPanel.setLayout(new GridLayout(15, 1));
		tagPanel.revalidate();
		tagPanel.repaint();
	}

	public static void showBad(String message) {
		JOptionPane.showMessageDialog(null, "This is a bad bug report! "+message,
				"Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showGood() {
		JOptionPane.showMessageDialog(null, "This is a good bug report!",
				"Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showAppBugWaring() {
		JOptionPane.showMessageDialog(null, "Please choose some basic element!",
				"Error", JOptionPane.ERROR_MESSAGE);
	}
	

	static class ColorIcon implements Icon {
		Color color;

		public ColorIcon(Color c) {
			color = c;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			g.setColor(color);
			g.fillRect(x, y, getIconWidth(), getIconHeight());
		}

		@Override
		public int getIconWidth() {
			return 10;
		}

		@Override
		public int getIconHeight() {
			return 10;
		}
	}

}
