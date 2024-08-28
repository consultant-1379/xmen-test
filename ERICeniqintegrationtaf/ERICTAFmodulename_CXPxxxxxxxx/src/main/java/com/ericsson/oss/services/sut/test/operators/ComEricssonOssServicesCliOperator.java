package com.ericsson.oss.services.sut.test.operators;

import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.Operator;
import com.ericsson.cifwk.taf.tools.cli.CLIOperator;

import java.util.Map;

@Operator(context = Context.CLI)
public class ComEricssonOssServicesCliOperator extends CLIOperator implements ComEricssonOssServicesOperator {

    public Map<String, String> get(String step) {
        return loadData("ComEricssonOssServices_CliTestData.csv", step);
    }

}

