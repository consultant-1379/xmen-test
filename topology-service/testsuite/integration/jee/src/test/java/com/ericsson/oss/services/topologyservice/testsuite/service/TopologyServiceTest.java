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
package com.ericsson.oss.services.topologyservice.testsuite.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.ericsson.oss.services.topologyservice.api.MoEntity;
import com.ericsson.oss.services.topologyservice.api.TopologyServiceLocal;
import com.ericsson.oss.services.topologyservice.exception.TopologyServiceException;
import com.ericsson.oss.services.topologyservice.testsuite.Deployments;
import com.ericsson.oss.services.topologyservice.testsuite.utils.TopologyCreator;

/**
 * Test the topology service interface
 */
@RunWith(Arquillian.class)
public class TopologyServiceTest extends TopologyCreator {

    @Inject
    private Logger logger;

    @Inject
    private TopologyServiceLocal topologyService;

    private static int noOfNodes = 10;

    @Deployment
    public static Archive<?> getDeployment() {
        return Deployments.createDeployement(TopologyServiceTest.class);
    }

    @Before
    public void setUp() {

        try {
            prepareData(noOfNodes);
        } catch (final Exception e) {
            logger.error("Can not prepare data", e);
        }
    }

    @Test
    public void testRunQueryToFindSameType() {
        List<MoEntity> moList;
        try {
            moList = topologyService.runQuery(CPP_NAMESPACE, MANAGED_ELEMENT);
            assertEquals(noOfNodes, moList.size());
        } catch (final TopologyServiceException e) {
            logger.warn("exception", e);
        }

    }

    @Test
    public void testRunQueryToFindSpecifiedMO() {

        List<MoEntity> moList;
        try {
            moList = topologyService.runQuery(ERBS_NAMESPACE, ENODEB_FUNCTION, "MeContext=LTE01ERBS1");
            assertEquals(1, moList.size());
        } catch (final TopologyServiceException e) {
            logger.warn("exception", e);
        }

    }

    @Test
    public void testRunQueryToFindContainment() {
        List<MoEntity> moList;
        try {
            moList = topologyService.runContainmentQuery("MeContext=LTE01ERBS1");
            assertEquals(4, moList.size());//1 managedElement, 1 EnodeBFunction, 2 EUtranCell
        } catch (final TopologyServiceException e) {
            logger.warn("exception", e);
        }

    }

    @Test(expected = TopologyServiceException.class)
    public void testRunQueryWithException() throws TopologyServiceException {
        topologyService.runQuery(CPP_NAMESPACE, ME_CONTEXT);
    }

}
