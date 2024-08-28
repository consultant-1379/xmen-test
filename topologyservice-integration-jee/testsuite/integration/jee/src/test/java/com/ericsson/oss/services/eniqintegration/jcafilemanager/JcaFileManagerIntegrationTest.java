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
package com.ericsson.oss.services.eniqintegration.jcafilemanager;

import static org.junit.Assert.*;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.*;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.oss.services.eniqintegration.arquillianconfig.Artifact;
import com.ericsson.oss.services.eniqintegration.arquillianconfig.BaseDeployment;

@RunWith(Arquillian.class)
public class JcaFileManagerIntegrationTest {

    public static final String TEST_DIRECTORY = System.getProperty("java.io.tmpdir") + "topologyservice-integration-jee-testsuite-integration-jee";
    private final static String DEPLOYMENT_NAME = "jca-file-manager-test-war";

    @Inject
    JcaFileManager jcaFileManager;

    @Inject
    JcaDirectoryOperator jcaDirectoryOperator;

    @ArquillianResource
    private Deployer deployer;

    @Deployment(name = "jca-file-connector-rar", testable = false, managed = false)
    public static Archive<?> depoloyAICoreRARService() {
        return Artifact.getServiceFrameworkRar();
    }

    @Deployment(name = DEPLOYMENT_NAME, testable = true, managed = false)
    public static Archive<?> getDeployment() {
        return BaseDeployment.createDeployment(JcaFileManagerIntegrationTest.class);
    }

    @Test
    @InSequence(1)
    @OperateOnDeployment("jca-file-connector-rar")
    public void deployFileConnectionRar() throws Exception {
        this.deployer.deploy("jca-file-connector-rar");
    }

    @Test
    @InSequence(2)
    @OperateOnDeployment(DEPLOYMENT_NAME)
    public void deployJcaTestWar() throws Exception {
        this.deployer.deploy(DEPLOYMENT_NAME);
    }

    @Test
    @InSequence(3)
    @OperateOnDeployment(DEPLOYMENT_NAME)
    public void createTestDirectory() {

        if (jcaDirectoryOperator.isDirectoryExistsWithinTransaction(TEST_DIRECTORY)) {
            jcaDirectoryOperator.deleteDirectoryWithinTransaction(TEST_DIRECTORY);
        }

        jcaDirectoryOperator.createDirectoryWithinTransaction(TEST_DIRECTORY);

        assertTrue("Can't create test directory", jcaDirectoryOperator.isDirectoryExistsWithinTransaction(TEST_DIRECTORY));
    }

    @Test
    @InSequence(4)
    @OperateOnDeployment(DEPLOYMENT_NAME)
    public void JCAFileWriterTest() {
        // Operations on File Resource

        final String fileName = TEST_DIRECTORY + File.separator + "testFolder.txt";

        jcaFileManager.setFileName(fileName);

        if (jcaFileManager.exists()) {
            jcaFileManager.delete();
        }

        final String data = "testdata";
        jcaFileManager.write(data.getBytes(), false);
        assertEquals("Assert if two string are equal", data, jcaFileManager.getAsText());
    }

    @Test
    @InSequence(999)
    @OperateOnDeployment(DEPLOYMENT_NAME)
    public void deleteTestDirectory() {

        if (jcaDirectoryOperator.isDirectoryExistsWithinTransaction(TEST_DIRECTORY)) {
            jcaDirectoryOperator.deleteDirectoryWithinTransaction(TEST_DIRECTORY);
        }

        jcaDirectoryOperator.deleteDirectoryWithinTransaction(TEST_DIRECTORY);

        assertFalse("Can't delete test directory", jcaDirectoryOperator.isDirectoryExistsWithinTransaction(TEST_DIRECTORY));
    }

}
