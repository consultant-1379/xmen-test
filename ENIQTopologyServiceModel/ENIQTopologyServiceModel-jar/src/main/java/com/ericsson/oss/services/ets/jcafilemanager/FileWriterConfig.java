package com.ericsson.oss.services.ets.jcafilemanager;

import com.ericsson.oss.itpf.modeling.annotation.DefaultValue;
import com.ericsson.oss.itpf.modeling.annotation.EModel;
import com.ericsson.oss.itpf.modeling.annotation.configparam.ConfParamDefinition;
import com.ericsson.oss.itpf.modeling.annotation.configparam.ConfParamDefinitions;
import com.ericsson.oss.itpf.modeling.annotation.constraints.NotNull;

@EModel(namespace = "ENIQTopologyServiceModeledConfiguration", description = "ignore")
@ConfParamDefinitions
public class FileWriterConfig {

    @NotNull
    @ConfParamDefinition(description = "Configuration parameter used for ...")
    @DefaultValue("/var/tmp/topologyservice/MockFlowTest.txt")
    public String FilePath;

    @NotNull
    @ConfParamDefinition(description = "Configuration parameter used for ...")
    @DefaultValue("this is a test.")
    public String FileContent;

}
