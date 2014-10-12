package com.worldofsoa.br.junit.test;

import com.worldofsoa.br.junit.stub.Failure;
import com.worldofsoa.br.junit.stub.Testcase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.maven.plugin.logging.Log;

import oracle.rules.testfmwk.IDFTestCaseResult;
import oracle.rules.testfmwk.impl.DFTestSuiteResult;

public class GenerateJunitReport {

	private File outputDirectory;
	
	private static final String OUT_DIR = "testBR";

	public GenerateJunitReport(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public void generateReportJunit4(DFTestSuiteResult dfTestSuiteResult,
			List<IDFTestCaseResult> testCaseResults, int numOk, int numFail,
			double timeToRunTests, Log log) {

		com.worldofsoa.br.junit.stub.Testsuite junitTestsuite = new com.worldofsoa.br.junit.stub.Testsuite();
		junitTestsuite.setFailures(String.valueOf(numFail));
		junitTestsuite.setTests(String.valueOf(numOk + numFail));
		junitTestsuite.setName(dfTestSuiteResult.getTestSuiteName());
		junitTestsuite.setTimestamp(String.valueOf(System.currentTimeMillis()));
		junitTestsuite.setSkipped("0");
		junitTestsuite.setErrors("0");
		junitTestsuite.setTime(String.valueOf(timeToRunTests));

		for (IDFTestCaseResult testcase : testCaseResults) {
			Testcase junitTestCase = new Testcase();
			junitTestCase.setName(testcase.getTestCaseName());
			junitTestCase.setClassname(dfTestSuiteResult.getTestSuiteName()
					+ "-" + dfTestSuiteResult.getDecisionFunctionName());
			junitTestCase
					.setStatus(String.valueOf(testcase.getTestCaseResult()));
			if (!testcase.getTestCaseResult()) {
				Failure fail = new Failure();
				fail.setMessage("test failure");
				fail.setContent(testcase.getComments());
				junitTestCase.getFailure().add(fail);
			}
			junitTestsuite.getTestcase().add(junitTestCase);
		}
		try {
			javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext
					.newInstance(junitTestsuite.getClass().getPackage()
							.getName());
			javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING,
					"UTF-8"); 
			marshaller.setProperty(
					javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FRAGMENT,
					Boolean.TRUE);
			File reportJunit = new File(outputDirectory.getAbsolutePath()
					+ File.separator + OUT_DIR + File.separator
					+ dfTestSuiteResult.getTestSuiteName() + "_"
					+ dfTestSuiteResult.getDecisionFunctionName() + ".xml");
			if (reportJunit.exists()) {
				reportJunit.mkdirs();
			}
			OutputStream os = new FileOutputStream(reportJunit);
			marshaller.marshal(junitTestsuite, os);

		} catch (javax.xml.bind.JAXBException ex) {
			// Exception to save testsuite with jaxb
			log.error("JAXB Exception: " + ex.getMessage());
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException Exception: " + e.getMessage());
		}
	}

}