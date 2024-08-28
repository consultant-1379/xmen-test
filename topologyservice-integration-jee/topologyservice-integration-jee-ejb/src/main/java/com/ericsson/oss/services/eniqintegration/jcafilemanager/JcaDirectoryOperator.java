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

import java.io.Serializable;

import javax.ejb.*;
import javax.inject.Inject;

import com.ericsson.oss.itpf.sdk.recording.EventLevel;
import com.ericsson.oss.itpf.sdk.recording.SystemRecorder;
import com.ericsson.oss.itpf.sdk.resources.Resource;
import com.ericsson.oss.itpf.sdk.resources.Resources;

// Transactions are required for JCA implementations to work properly

@Stateful
public class JcaDirectoryOperator implements Serializable {

    private static final String LOG_RESOURCE = "JcaDirectoryOperator";
    private static final String LOG_EVENT_TYPE = "TOPOLOGY_SERVICE.JCA_DIRECTORY_OPERATION";

    @Inject
    private SystemRecorder systemRecorder;

    private static final long serialVersionUID = -3149024385154233260L;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createDirectoryWithinTransaction(final String directoryName) {

        final Resource resource = Resources.getFileSystemResource(directoryName);
        resource.createDirectory();

        systemRecorder.recordEvent(LOG_EVENT_TYPE, EventLevel.COARSE, "Directory creation", LOG_RESOURCE, "Directory " + directoryName
                + " has been created.");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteDirectoryWithinTransaction(final String directoryName) {
        final Resource resource = Resources.getFileSystemResource(directoryName);
        resource.deleteDirectory();

        systemRecorder.recordEvent(LOG_EVENT_TYPE, EventLevel.COARSE, "Directory deletion", LOG_RESOURCE, "Directory " + directoryName
                + " has been deleted.");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean isDirectoryExistsWithinTransaction(final String directoryName) {
        final Resource resource = Resources.getFileSystemResource(directoryName);
        return resource.isDirectoryExists();
    }

    // Added For Testing
    public void setSystemRecorder(final SystemRecorder systemRecorder) {
        this.systemRecorder = systemRecorder;
    }

}
