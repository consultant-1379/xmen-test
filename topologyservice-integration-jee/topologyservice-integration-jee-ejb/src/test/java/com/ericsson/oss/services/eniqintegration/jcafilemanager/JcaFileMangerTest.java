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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ericsson.oss.itpf.sdk.recording.SystemRecorder;
import com.ericsson.oss.itpf.sdk.resources.Resources;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Resources.class)
public class JcaFileMangerTest {

    final String testDirectory = System.getProperty("user.dir");
    final String missingDir = testDirectory + "\\Missing";
    final MockResource mockResource = new MockResource(testDirectory);
    final String fileName = testDirectory + File.separator + "testFolder.txt";
    final String missingDirFullName = missingDir + File.separator + "testFolder.txt";
    final MockJcaDirectoryOperator mockOperator = new MockJcaDirectoryOperator();
    private JcaFileManager jcaFileManager;

    // set up recorder
    private final SystemRecorder systemRecorder = new MockSystemRecorder();

    @Before
    public void setUp() {

        PowerMockito.mockStatic(Resources.class);
        PowerMockito.when(Resources.getFileSystemResource(testDirectory)).thenReturn(mockResource);
        PowerMockito.mockStatic(SystemRecorder.class);
        jcaFileManager = new JcaFileManager();
        jcaFileManager.setRecorder(systemRecorder);
        jcaFileManager.setDirectoryOperator(mockOperator);
        jcaFileManager.directoryOperator.setSystemRecorder(systemRecorder);
    }

    @Test
    public void setFileNameTest() {
        PowerMockito.when(Resources.getFileSystemResource(fileName)).thenReturn(mockResource);
        jcaFileManager.setFileName(fileName);
        assertEquals(mockResource, jcaFileManager.getResource());
    }

    @Test
    public void setFileNameTestDirectoryDoesNotExist() {
        PowerMockito.when(Resources.getFileSystemResource(missingDirFullName)).thenReturn(mockResource);
        jcaFileManager.setFileName(missingDirFullName);
        assertEquals(missingDir, mockOperator.getDirName());
    }

    @Test
    public void doResourcesExistsTest() {
        jcaFileManager.setResource(mockResource);
        assertEquals(true, jcaFileManager.exists());
    }

    @Test
    public void getAsTextTest() {
        jcaFileManager.setResource(mockResource);
        assertEquals("TEST", jcaFileManager.getAsText());
    }

    @Test
    public void writeTest() {
        jcaFileManager.setResource(mockResource);
        final byte[] testByte = new byte[4];
        assertEquals(5, jcaFileManager.write(testByte, true));
    }

    @Test
    public void deleteTest() {
        jcaFileManager.setResource(mockResource);
        assertEquals(true, jcaFileManager.delete());
    }

}
