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
package com.ericsson.oss.services.topologyservice.exception;

public class ServiceExecutionException extends TopologyServiceException {

    private static final long serialVersionUID = 2862185804624626838L;

    /**
     * @param message
     */
    public ServiceExecutionException(final String message) {
        super(message);
    }

    /**
     * 
     * @param message
     * @param e
     */
    public ServiceExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
