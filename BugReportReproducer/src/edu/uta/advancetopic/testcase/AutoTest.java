package edu.uta.advancetopic.testcase;

import java.io.File;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.taskdefs.Javac.ImplementationSpecificArgument;
import org.apache.tools.ant.types.Path;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import edu.uta.advancetopic.gui.GUI;

public class AutoTest {

	public static void test(String classPath,String className) {
		File buildFile = new File("build.xml");
		Project p = new Project();
		p.init();
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		helper.parse(p, buildFile);
		p.executeTarget(p.getDefaultTarget());
		
		
		
		try {
			Class<?> class1 = Class.forName(className);
			JUnit4TestAdapter adapter = new JUnit4TestAdapter(class1);
			TestSuite suite = new TestSuite("test for base testcase.");
			suite.addTest(adapter);
			TestResult result =TestRunner.run(suite);
			if(result.errorCount()==0){
				GUI.showGood();
				
			}else {
				String message = "There are " + result.errorCount() + " bugs that cannot be found by test case!"; 
				GUI.showBad(message);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
