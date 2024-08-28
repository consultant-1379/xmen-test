/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2013
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.services.topologyservice.testsuite;

import java.io.File;

public class Artifact {

    public static final String MODEL_ROOT = "target/deployed-model";
    public static final String MODEL_REPO_XML_PATH = "target/deployed-model/modelRepo.xml";

    //sfw dist
    public static final String ITPF_SERVICE_FRAMEWORK_DIST = "com.ericsson.oss.itpf.sdk:service-framework-dist:jar";

    //manifest
    public static final File MANIFEST_MF_FILE = new File("src/test/resources/META-INF/MANIFEST.MF");

    //deployment structure
    public static final File DEPLOYMENT_STRUCTURE = new File("src/test/resources/META-INF/jboss-deployment-structure.xml");

}
