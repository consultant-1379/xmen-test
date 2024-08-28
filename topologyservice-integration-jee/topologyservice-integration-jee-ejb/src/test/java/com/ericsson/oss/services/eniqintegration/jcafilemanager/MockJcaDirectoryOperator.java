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

import com.ericsson.oss.itpf.sdk.recording.SystemRecorder;
import com.ericsson.oss.itpf.sdk.resources.Resource;
import com.ericsson.oss.itpf.sdk.resources.Resources;

public class MockJcaDirectoryOperator extends JcaDirectoryOperator {
    final static String MISSING_DIRECTORY = "\\Missing";
    String dirName = "";
    private SystemRecorder systemRecorder;

    /**
     *
     */
    private static final long serialVersionUID = -3149024385154233260L;

    @Override
    public boolean isDirectoryExistsWithinTransaction(final String directoryName) {
        final Resource resource = Resources.getFileSystemResource(directoryName);

        return !directoryName.equals(System.getProperty("user.dir") + MISSING_DIRECTORY);
    }

    @Override
    public void createDirectoryWithinTransaction(final String directoryName) {
        setDirName(directoryName);
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(final String dirName) {
        this.dirName = dirName;
    }

    @Override
    public void setSystemRecorder(final SystemRecorder systemRecorder) {
        this.systemRecorder = systemRecorder;
    }
}
