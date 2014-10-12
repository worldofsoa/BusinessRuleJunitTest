package com.worldofsoa.br.junit.test;

import oracle.rules.sdk2.datamodel.DecisionFunction;
import oracle.rules.sdk2.decisionpoint.DecisionPointDictionaryFinder;
import oracle.rules.sdk2.dictionary.RuleDictionary;
import oracle.rules.sdk2.exception.SDKWarning;
import oracle.rules.testfmwk.IDFTestCaseResult;
import oracle.rules.testfmwk.common.exception.InvalidTestSuiteException;
import oracle.rules.testfmwk.impl.DFTestSuiteResult;
import oracle.rules.testfmwk.impl.DFTestSuiteRunner;
import oracle.rules.testfmwk.model.TestCaseType;
import oracle.rules.testfmwk.model.TestSuite;
import oracle.rules.testfmwk.util.TestUtil;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Plugin for execute business rules tests.
 * 
 * @goal testBR
 * @phase test
 */
public class BusinessRuleJunitTest extends AbstractMojo {

	/**
	 * Target directory by save the results of tests in format HTML
	 * 
	 * @parameter property="project.build.directory"
	 * @required
	 */
	private File outputDirectory;

	/**
	 * Maven Project
	 * 
	 * @parameter property="project"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * Custom property, Testsuite.
	 * 
	 * @parameter
	 * @required
	 */
	private List<Testsuite> testsuites;

	public void execute() throws MojoExecutionException {
		getLog().info("Init Plugin Test-BR");
		File baseDir = project.getBasedir();

		// Directory to rules
		String pathRules = baseDir.getAbsolutePath() + File.separator
				+ "oracle" + File.separator + "rules";
		String pathTestSuite = baseDir.getAbsolutePath() + File.separator
				+ "testsuites" + File.separator + "rules";

		// Add jaxb classes
		addJaxbToClasspath(baseDir);

		// Execute TestsCases
		boolean resultPass = true;

		for (Testsuite testSuite : testsuites) {
			try {
				boolean testResult = testRulesTestSuite(pathRules
						+ File.separator + testSuite.getRule(),
						testSuite.getDecisionfunction(), pathTestSuite
								+ File.separator + testSuite.getTestsuite());
				if (!testResult) {
					resultPass = false;
				}
			} catch (Exception e) {
				getLog().debug(e);
				throw new MojoExecutionException(e.getMessage());
			}
		}

		if (resultPass) {
			// Passed
			getLog().info("Tests Passed!!!");
		} else {
			// Fail
			getLog().error("Tests Failed!!!");
			getLog().error(
					"Check the logs in " + outputDirectory.getAbsolutePath()
							+ " for more details.");
			throw new MojoExecutionException("Test Failed!!");
		}

	}

	/**
	 * Add jaxb clases to classpath.
	 * 
	 * @param baseDir
	 * @throws MojoExecutionException
	 */
	private void addJaxbToClasspath(File baseDir) throws MojoExecutionException {
		String pathClass = baseDir.getAbsolutePath() + File.separator
				+ ".rulesdesigner" + File.separator + "jaxb_classes";

		getLog().info("Add Folder Class to classpath: " + pathClass);

		try {
			URL[] urlsClass = new URL[1];
			urlsClass[0] = new File(pathClass).toURI().toURL();
			ClassLoader contextClassLoader = URLClassLoader.newInstance(
					urlsClass, Thread.currentThread().getContextClassLoader());

			Thread.currentThread().setContextClassLoader(contextClassLoader);

		} catch (MalformedURLException e) {
			getLog().error(e);
			throw new MojoExecutionException(e.getMessage());
		}
	}

	/**
	 * Run Testsuite with business rule and decision function.
	 * 
	 * @param rule
	 * @param decisionFunction
	 * @param testSuite
	 * @return
	 * @throws Exception
	 */
	public boolean testRulesTestSuite(String rule, String decisionFunction,
			String testSuite) throws Exception {

		File testSuiteFile = null;
		RuleDictionary ruleDictionary = null;
		DecisionFunction decisionFuntion = null;
		String reportSummary = null;

		try {
			testSuiteFile = new File(testSuite);
		} catch (Exception fe) {
			getLog().error(
					"Exception in Reading Source Test Suite File: "
							+ fe.getMessage());
			throw fe;
		}

		if (rule != null && decisionFunction != null) {
			ruleDictionary = loadRuleDictionary(rule);
			if (ruleDictionary == null) {
				return false;
			}
			decisionFuntion = ruleDictionary.getDataModel()
					.getDecisionFunctionTable().getByName(decisionFunction);
		} else {
			getLog().error("Rule and/or decision function are required.");
			throw new Exception("Rule and/or decision function are required.");
		}

		String testSuiteName = testSuite.substring(
				testSuite.lastIndexOf(File.separator) + 1,
				testSuite.lastIndexOf("."));

		getLog().info(
				"Run TestSuite: " + testSuiteName + " with decision function: "
						+ decisionFunction);

		// Execute TestSuites
		DFTestSuiteResult dfTestSuiteResult = new DFTestSuiteResult(
				decisionFuntion.getName(), testSuiteName);
		DFTestSuiteRunner testRunner = new DFTestSuiteRunner(testSuiteFile,
				decisionFuntion, ruleDictionary, dfTestSuiteResult);

		List<String> listString = getTestCasesFromTestSuite(testSuiteFile);
		String[] testCaseArray = listString.toArray(new String[listString
				.size()]);
		Long initTime = System.currentTimeMillis();
		testRunner.runTests(testCaseArray);
		Long endTime = System.currentTimeMillis();
		Long dif = (endTime - initTime);
		double timeToRunTests = (dif * 1.0 / 1000);
		boolean resultPass = true;
		// Check if all test are ok
		List<IDFTestCaseResult> testCaseResults = dfTestSuiteResult
				.getTestCaseResults();
		int numOk = 0, numFail = 0;
		for (IDFTestCaseResult testCaseResult : testCaseResults) {
			if (!testCaseResult.getTestCaseResult()) {
				resultPass = false;
				numFail++;
			} else {
				numOk++;
			}
		}
		// Set testSuite result
		dfTestSuiteResult.setTestSuiteResult(resultPass);

		// Save reportSummary
		reportSummary = dfTestSuiteResult.getResultSummary();
		saveReport(reportSummary, testSuiteName, decisionFunction);
		getLog().info("Test Case Output : " + reportSummary);
		StringBuffer sb = new StringBuffer();
		sb.append("TestSuite: ").append(decisionFunction).append("-")
				.append(testSuiteName).append(", test cases: ")
				.append(testCaseResults.size()).append(", OK: ").append(numOk)
				.append(", Errors: ").append(numFail).append("\n");
		// Convert report to junit xml
		GenerateJunitReport junitReport = new GenerateJunitReport(
				outputDirectory);

		junitReport.generateReportJunit4(dfTestSuiteResult, testCaseResults,
				numOk, numFail, timeToRunTests, getLog());
		// Print run testCases
		if (resultPass) {
			getLog().info(sb.toString());
		} else {
			getLog().error(sb.toString());
		}
		return resultPass;
	}

	/**
	 * Save reports summaries.
	 * 
	 * @param reportSummary
	 * @param testSuite
	 * @param decisionRule
	 * @throws IOException
	 */
	private void saveReport(String reportSummary, String testSuite,
			String decisionRule) throws IOException {
		File toSave = new File(outputDirectory.getAbsolutePath()
				+ File.separator + "testBR");
		if (!toSave.exists()) {
			toSave.mkdirs();
		}
		File report = new File(toSave.getAbsolutePath() + File.separator
				+ testSuite + "_" + decisionRule + ".html");
		try {
			report.createNewFile();
			Files.write(report.toPath(), reportSummary.getBytes());
		} catch (IOException e) {
			getLog().error(e);
			throw e;
		}
	}

	/**
	 * Get Rule dictionary by dictionaryPath field value.
	 * 
	 * @param dictionaryPath
	 * @return
	 * @throws Exception
	 */
	private RuleDictionary loadRuleDictionary(String dictionaryPath)
			throws Exception {

		RuleDictionary ruledictionary = null;
		Reader reader = null;
		try {
			reader = new FileReader(new File(dictionaryPath));
			ruledictionary = RuleDictionary.readDictionary(reader,
					new DecisionPointDictionaryFinder(null));
			List<SDKWarning> warnings = new ArrayList<SDKWarning>();
			ruledictionary.update(warnings);
			if (warnings.size() > 0) {
				getLog().error(
						"Rule Dictionary returned the followingv validation warnings: "
								+ warnings);
				return null;
			}
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		return ruledictionary;
	}

	/**
	 * Get all testCase for a testsuite.
	 * 
	 * @param tsFile
	 * @return
	 * @throws InvalidTestSuiteException
	 */
	private static List<String> getTestCasesFromTestSuite(File tsFile)
			throws InvalidTestSuiteException {

		ArrayList<String> testCaseArrayList = new ArrayList<String>();
		ListIterator<TestCaseType> localListIterator;
		if (tsFile != null) {
			TestSuite localTestSuite = TestUtil.readTestSuite(tsFile);
			List<TestCaseType> localList = localTestSuite.getTestCase();
			for (localListIterator = localList.listIterator(); localListIterator
					.hasNext();) {
				TestCaseType localTestCaseType = (TestCaseType) localListIterator
						.next();
				testCaseArrayList.add(localTestCaseType.getName());
			}
		}
		return testCaseArrayList;
	}

}
