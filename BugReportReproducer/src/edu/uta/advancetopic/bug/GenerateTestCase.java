package edu.uta.advancetopic.bug;

import java.io.*;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import edu.stanford.nlp.ling.CoreAnnotations.LEndAnnotation;
import edu.uta.advancetopic.testcase.AutoTest;

public class GenerateTestCase {
	public static String className;
	public static String packageName;

	public static void generate() {
		SrcBug bug = SrcBug.getBug();
		className = bug.getClassname().toLowerCase();
		packageName = bug.getPackagesString().toLowerCase();
		String[] packageList = packageName.split("\\.");
		String url = "src\\";
		for (String s : packageList) {
			url += s;
			url += File.separator;
		}
		File file = new File(url + "Test" + bug.getClassname() + ".java");
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Can't create file");
			e.printStackTrace();
		}

		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		PrintWriter pw = new PrintWriter(fos);

		writeHead(pw, bug);

		pw.append("public class Test" + bug.getClassname() + "{"
				+ System.lineSeparator());
		pw.append(System.lineSeparator());
		pw.flush();
		pw.append(System.lineSeparator());
		pw.append("public static " + bug.getClassname() + " " + className.toLowerCase()
				+ " =" + " new " + bug.getClassname() + "()" + ";");
		pw.append(System.lineSeparator());
		pw.flush();

		writeBefore(pw, bug);

		writeTear(pw, bug);

		writeTest(pw, bug);

		pw.append("}" + System.lineSeparator());
		pw.flush();

		pw.close();
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path = getClassPath(bug.getPackagesString());
		AutoTest.test(path,bug.getPackagesString()+".Test" + bug.getClassname());

	}
	
	private static String getClassPath(String path){
		String url = "bin\\";
		String[] packageList = path.split("\\.");
		for (String s : packageList) {
			url += s;
			url += File.separator;
		}
		return url;
	}

	public static void writeHead(PrintWriter pw, SrcBug bug) {
		pw.append(new String("package " + bug.getPackagesString() + ";"
				+ System.lineSeparator()));
		pw.flush();
		pw.append("import static org.junit.Assert.*;" + System.lineSeparator());
		pw.append(System.lineSeparator());
		pw.append("import org.junit.After;" + System.lineSeparator());
		pw.append("import org.junit.Before;" + System.lineSeparator());
		pw.append("import org.junit.Test;" + System.lineSeparator());
		pw.append("import " + bug.getPackagesString() + "."
				+ bug.getClassname() + ";" + System.lineSeparator());
		pw.flush();
	}

	public static void writeBefore(PrintWriter pw, SrcBug bug) {
		pw.append("@Before" + System.lineSeparator());
		pw.append("public void setUp() throws Exception {"
				+ System.lineSeparator());
		pw.flush();
		pw.flush();
		pw.append("}" + System.lineSeparator());
		pw.flush();
	}

	public static void writeTear(PrintWriter pw, SrcBug bug) {
		pw.append(System.lineSeparator());
		pw.append("@After" + System.lineSeparator());
		pw.append("public void tearDown() throws Exception {"
				+ System.lineSeparator());
		pw.flush();
		pw.append("}" + System.lineSeparator());
		pw.flush();
	}

	public static void writeTest(PrintWriter pw, SrcBug bug) {
		pw.append(System.lineSeparator());
		pw.append("@Test");
		if (bug.getException() != null) {
			pw.append(" (expected  =  " + bug.getException() + ".class )");
		}
		pw.append(System.lineSeparator());
		pw.flush();

		pw.append("public void test" + bug.getClassname() + "(){"
				+ System.lineSeparator());
		pw.flush();
		if (bug.getOutput() != null) {
			pw.append("assertEquals(" + className + "." + bug.getMethod() + "(");
			writeParameter(pw, bug);
			writeOutput(pw, bug);
		} else {
			pw.append(className + "." + bug.getMethod() + "(");
			writeParameter(pw, bug);
			pw.append(";" + System.lineSeparator());
		}
		pw.flush();

		pw.append("}" + System.lineSeparator());

	}

	private static void writeOutput(PrintWriter pw, SrcBug bug) {
		// TODO Auto-generated method stub
		pw.append("," + bug.getOutput() + ")");
		pw.append(";" + System.lineSeparator());
		pw.flush();
		
	}

	public static void writeParameter(PrintWriter pw, SrcBug bug) {
		int len;
		if ((len = bug.getParameter().size()) != 0) {
			for (int i = 0; i < len - 1; i++) {
				pw.append(bug.getParameter().get(i) + ", ");
			}
			pw.append(bug.getParameter().get(len - 1));
		}
		pw.append(")");
		pw.flush();
	}

}
