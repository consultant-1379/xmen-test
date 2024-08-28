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
package com.ericsson.oss.services.topologyservice.api;

import java.util.List;

import com.ericsson.oss.services.topologyservice.exception.TopologyServiceException;

/**
 * 
 * The implementation of this interface provides method to run query to retrieve topology data. This interface is intended to be extended by local
 * interface {@link TopologyServiceLocal} and remote interface {@link TopologyServiceRemote} And it's better NOT to implement this one directly
 */
public interface TopologyService {

    List<MoEntity> runQuery(final String nameSpace, final String type) throws TopologyServiceException;

    List<MoEntity> runQuery(final String nameSpace, final String type, final String baseMo) throws TopologyServiceException;

    List<MoEntity> runContainmentQuery(final String baseMo) throws TopologyServiceException;
}
