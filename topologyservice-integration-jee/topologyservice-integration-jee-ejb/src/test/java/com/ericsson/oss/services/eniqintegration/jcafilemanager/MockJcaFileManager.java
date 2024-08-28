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

public class MockJcaFileManager extends JcaFileManager {

    private static final long serialVersionUID = -6194361721234434428L;

    public static final String UNDELETEABLE_FILENAME = "locked.file";
    public String fileName;
    public int bytesWritten;

    @Override
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int write(final byte[] content, final boolean append) {
        bytesWritten = content.length;
        return bytesWritten;
    }

    @Override
    public boolean delete() {
        if (fileName.equals(UNDELETEABLE_FILENAME)) {
            return false;
        } else {
            return true;
        }
    }
}
