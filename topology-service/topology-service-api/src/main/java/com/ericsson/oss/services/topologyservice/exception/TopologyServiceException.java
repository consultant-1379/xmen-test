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

/**
 * 
 * Thrown to indicate topology service exception
 * 
 */
public class TopologyServiceException extends Exception {

    private static final long serialVersionUID = -4079794040463790063L;

    public TopologyServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TopologyServiceException(final String message) {
        super(message);
    }

}
