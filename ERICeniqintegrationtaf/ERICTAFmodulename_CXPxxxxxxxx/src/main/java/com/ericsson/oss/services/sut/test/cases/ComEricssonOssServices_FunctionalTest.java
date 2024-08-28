package com.ericsson.oss.services.sut.test.cases ;

import com.ericsson.cifwk.taf.*;
import com.ericsson.cifwk.taf.annotations.*;
import com.ericsson.cifwk.taf.exceptions.*;
import com.ericsson.cifwk.taf.guice.*;
import com.ericsson.cifwk.taf.tools.cli.*;

import org.testng.annotations.Test;
import javax.inject.Inject;
import java.util.*;

import com.ericsson.oss.services.sut.test.operators.*;
import com.ericsson.oss.services.sut.test.getters.*;

public class ComEricssonOssServices_FunctionalTest extends TorTestCaseHelper implements TestCase {

    @Inject
    private OperatorRegistry<ComEricssonOssServicesOperator> comEricssonOssServicesProvider;

    @Inject
    private ComEricssonOssServicesGetter comEricssonOssServicesGetter;

//    @TestId(id = "datadriven", title = "DataDriven Example")
//    @Test
//    @DataDriven(name = "comericssonossservices_functionaltest")
//    public void shouldBePopulatedFromCsv(@Input("first") int x, @Input("second") int y, @Output("result") int expected) {
//          assertEquals(x + y, expected);
//    }


    /**
     * @DESCRIPTION Able to login to ENM server and verify that file exists
     * @PRE Able to login to server
     * @PRIORITY LOW
     */
    @TestId(id = "EQEV-12118_Func_1", title = "Test of CLI")
    @Context(context = {Context.CLI})
    @Test(groups={"ACCEPTANCE"})
    public void testOfCLI() {

        ComEricssonOssServicesCliOperator comEricssonOssServicesOperator = (ComEricssonOssServicesCliOperator) comEricssonOssServicesProvider.provide(ComEricssonOssServicesOperator.class);

        setTestInfo("login to server");
        Map<String, String> step1 = comEricssonOssServicesOperator.get("EQEV-12118_Func_1_Step1");
        CLIOperator.Result result1 = comEricssonOssServicesOperator.execute("login to server", step1);
        setTestStep("logged onto server");
        //TODO VERIFY:logged onto server

        setTestInfo("cd to directory");
        Map<String, String> step2 = comEricssonOssServicesOperator.get("EQEV-12118_Func_1_Step2");
        CLIOperator.Result result2 = comEricssonOssServicesOperator.execute("cd to directory", step2);
        setTestStep("pwd that your on correct directory");
        //TODO VERIFY:pwd that your on correct directory

        setTestInfo("execute ls -l on file");
        Map<String, String> step3 = comEricssonOssServicesOperator.get("EQEV-12118_Func_1_Step3");
        CLIOperator.Result result3 = comEricssonOssServicesOperator.execute("execute ls -l on file", step3);
        setTestStep("output matches expected result in csv");
        //TODO VERIFY:output matches expected result in csv
        
        setTestInfo("create config.properties");
        Map<String, String> step4 = comEricssonOssServicesOperator.get("EQEV-12118_Func_1_Step4");
        CLIOperator.Result result4 = comEricssonOssServicesOperator.execute("create config file", step4);
        setTestStep("output matches expected result in csv");
        //TODO VERIFY:output matches expected result in csv
        
        setTestInfo("cat config.properties");
        Map<String, String> step5 = comEricssonOssServicesOperator.get("EQEV-12118_Func_1_Step5");
        CLIOperator.Result result5 = comEricssonOssServicesOperator.execute("cat a file", step5);
        setTestStep("output matches expected result in csv");
        //TODO VERIFY:output matches expected result in csv

    }
}
	
