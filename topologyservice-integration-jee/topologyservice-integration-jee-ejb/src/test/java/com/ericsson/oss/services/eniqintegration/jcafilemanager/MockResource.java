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

import java.io.*;

import com.ericsson.oss.itpf.sdk.resources.Resource;

public class MockResource implements Resource {

    static final String MISSING_DIRECTORY = "MissingDirectory";
    final String directoryPath;
    static final String TEST_STRING = "TEST";
    static final int BYTE = 5;
    final byte[] testBytes = new byte[5];
    static final long MOCK_TIME = 199943653;
    String lastCreatedDirectoryPath = "";
    String lastDeletedDirectoryPath = "";

    /**
     * @param testDirectory
     */
    public MockResource(final String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public InputStream getInputStream() {
        final InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 5;
            }
        };
        return inputStream;
    }

    @Override
    public byte[] getBytes() {
        return testBytes;
    }

    @Override
    public String getAsText() {
        return TEST_STRING;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public long getLastModificationTimestamp() {
        return MOCK_TIME;
    }

    @Override
    public boolean delete() {
        return true;
    }

    @Override
    public boolean supportsWriteOperations() {
        return true;
    }

    @Override
    public int write(final byte[] content, final boolean append) {
        return BYTE;
    }

    @Override
    public OutputStream getOutputStream() {
        final OutputStream outputStream = new OutputStream() {
            @Override
            public void write(final int placeHolder) throws IOException {
            }
        };
        return outputStream;
    }

    @Override
    public void createDirectory() {
        lastCreatedDirectoryPath = directoryPath;
    }

    @Override
    public void deleteDirectory() {
        lastDeletedDirectoryPath = directoryPath;
    }

    @Override
    public boolean isDirectoryExists() {
        return !directoryPath.equals(MISSING_DIRECTORY);

    }

}
