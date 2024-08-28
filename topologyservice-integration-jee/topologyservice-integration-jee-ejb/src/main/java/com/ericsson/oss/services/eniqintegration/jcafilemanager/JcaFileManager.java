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

import java.io.File;
import java.io.Serializable;

import javax.ejb.*;
import javax.inject.Inject;

import com.ericsson.oss.itpf.sdk.recording.*;
import com.ericsson.oss.itpf.sdk.resources.Resource;
import com.ericsson.oss.itpf.sdk.resources.Resources;

// Transactions are required for JCA implementations to work properly

@Stateful
public class JcaFileManager implements Serializable {

    private static final String LOG_RESOURCE = "JcaFileManager";
    private static final String LOG_EVENT_TYPE = "TOPOLOGY_SERVICE.JCA_FILE_OPERATION";

    @Inject
    private SystemRecorder systemRecorder;

    @Inject
    JcaDirectoryOperator directoryOperator;

    private String fileName;
    /**
     * 
     */
    private static final long serialVersionUID = -686274406639758633L;

    private Resource resource;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void setFileName(final String fileName) {

        this.fileName = fileName;
        final String directoryPath = getPath(fileName);
        if (!directoryOperator.isDirectoryExistsWithinTransaction(directoryPath)) {
            systemRecorder.recordEvent(LOG_EVENT_TYPE, EventLevel.COARSE, "File creation", LOG_RESOURCE, "Required directory " + directoryPath
                    + " does not exist so it will be created.");
            directoryOperator.createDirectoryWithinTransaction(directoryPath);
        }
        this.resource = Resources.getFileSystemResource(fileName);
        systemRecorder.recordEvent(LOG_EVENT_TYPE, EventLevel.COARSE, "File creation", LOG_RESOURCE, "Got file system resource " + fileName);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int write(final byte[] content, final boolean append) {

        int response = 0;

        try {
            response = resource.write(content, append);
            systemRecorder.recordEvent(LOG_EVENT_TYPE, EventLevel.COARSE, "File writing", LOG_RESOURCE, "Written " + content.length
                    + " bytes to file " + fileName);
        } catch (final Exception e) {
            final String errorMessage = "Error writing file " + fileName + ": " + e.getMessage() + "\nCause: " + e.getCause().getMessage();
            systemRecorder.recordError("FILE WRITING ERROR", ErrorSeverity.ERROR, "File writing", LOG_RESOURCE, "Error writing file " + errorMessage);
            throw e;
        }

        return response;
    }

    public String getAsText() {
        return resource.getAsText();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean delete() {
        final boolean response = resource.delete();
        systemRecorder.recordEvent(LOG_EVENT_TYPE, EventLevel.COARSE, "File deletion", LOG_RESOURCE, "File " + fileName + " has been deleted.");
        return response;
    }

    public boolean exists() {
        return resource.exists();
    }

    private String getPath(final String fileName) {
        final File file = new File(fileName);
        final String absolutePath = file.getAbsolutePath();
        final String filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));

        return filePath;
    }

    // Added For Testing
    public void setRecorder(final SystemRecorder recorder) {
        this.systemRecorder = recorder;
    }

    // Added For Testing
    public void setDirectoryOperator(final JcaDirectoryOperator directoryOperator) {
        this.directoryOperator = directoryOperator;
    }

    // Added For Testing
    public Resource getResource() {
        return resource;
    }

    // Added For Testing
    public void setResource(final Resource resource) {
        this.resource = resource;
    }
}
