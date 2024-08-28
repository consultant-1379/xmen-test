/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2014
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.services.eniqintegration.remotecall;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.oss.services.eniqintegration.jcafilemanager.MockJcaFileManager;
import com.ericsson.oss.services.eniqintegration.jcafilemanager.MockSystemRecorder;

public class TAFBeanTest {

    MockJcaFileManager jcaFileManager;
    TAFBean testTafBean;
    MockSystemRecorder systemRecorder;

    @Before
    public void setUp() {
        systemRecorder = new MockSystemRecorder();
        jcaFileManager = new MockJcaFileManager();
        testTafBean = new TAFBean();
        testTafBean.setJcaFileManager(jcaFileManager);
        testTafBean.setSystemRecorder(systemRecorder);
    }

    @Test
    public void testRunTest() {

        testTafBean.runTest();

        assertNotNull("runTest() should set up a file resource with JcaFileManager", jcaFileManager.fileName);
        assertTrue("runTest() should write to file", jcaFileManager.bytesWritten > 0);
        assertEquals("Expected success to be logged", "Successfully wrote content to file.", systemRecorder.additionalInformation);
    }

}
