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
package com.ericsson.oss.services.eniqintegration.jcafilemanager;

import static org.junit.Assert.*;

import com.ericsson.oss.itpf.sdk.recording.SystemRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ericsson.oss.itpf.sdk.resources.Resources;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Resources.class)
public class JcaDirectoryOperatorTest {

    final static String testDirectory = "testDir";
    final MockResource mockResource = new MockResource(testDirectory);
    final MockResource mockResourceMissingDir = new MockResource(MockResource.MISSING_DIRECTORY);
    private final SystemRecorder mockSystemRecorder = new MockSystemRecorder();

    @Test
    public void testCreateDirectoryWithinTransactionResource() {

        final JcaDirectoryOperator jdo = new JcaDirectoryOperator();

        PowerMockito.mockStatic(Resources.class);
        PowerMockito.when(Resources.getFileSystemResource(testDirectory)).thenReturn(mockResource);
        jdo.setSystemRecorder(mockSystemRecorder);
        jdo.createDirectoryWithinTransaction(testDirectory);
        assertEquals(testDirectory, mockResource.lastCreatedDirectoryPath);
    }

    @Test
    public void testDeleteDirectoryWithinTransactionCallsResource() {

        final JcaDirectoryOperator jdo = new JcaDirectoryOperator();

        PowerMockito.mockStatic(Resources.class);
        PowerMockito.when(Resources.getFileSystemResource(testDirectory)).thenReturn(mockResource);
        jdo.setSystemRecorder(mockSystemRecorder);
        jdo.deleteDirectoryWithinTransaction(testDirectory);
        assertEquals(testDirectory, mockResource.lastDeletedDirectoryPath);
    }

    @Test
    public void testIsDirectoryExistsCallsResource() {

        final JcaDirectoryOperator jdo = new JcaDirectoryOperator();

        PowerMockito.mockStatic(Resources.class);
        PowerMockito.when(Resources.getFileSystemResource(testDirectory)).thenReturn(mockResource);
        PowerMockito.when(Resources.getFileSystemResource(MockResource.MISSING_DIRECTORY)).thenReturn(mockResourceMissingDir);
        jdo.setSystemRecorder(mockSystemRecorder);
        assertTrue(jdo.isDirectoryExistsWithinTransaction(testDirectory));
        assertFalse(jdo.isDirectoryExistsWithinTransaction(MockResource.MISSING_DIRECTORY));
    }

}
