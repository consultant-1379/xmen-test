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

import java.net.URI;
import java.net.URL;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.jboss.arquillian.container.test.api.*;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ericsson.oss.services.topologyservice.testsuite.Deployments;
import com.ericsson.oss.services.topologyservice.testsuite.utils.TopologyCreator;

@RunWith(Arquillian.class)
@RunAsClient
public class TopologyServiceRestfulTest extends TopologyCreator {

    private static int noOfNodes = 10;

    @ArquillianResource
    URL deploymentUrl;

    @Deployment
    @OverProtocol("Servlet 3.0")
    public static Archive<?> getDeployment() {
        return Deployments.createDeployement(TopologyServiceRestfulTest.class);
    }

    @Before
    public void setUp() {
        try {
            prepareData(noOfNodes);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRunQueryToFindSameType() {

        final ClientRequest request = new ClientRequest(buildUri(CPP_NAMESPACE, MANAGED_ELEMENT).toString());
        request.accept("application/json");
        ClientResponse<String> response;
        try {
            response = request.get(String.class);

            assertEquals(Status.OK.getStatusCode(), response.getStatus());

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRunQueryToFindSpecifiedMO() {

        final ClientRequest request = new ClientRequest(buildUri(ERBS_NAMESPACE, ENODEB_FUNCTION, "MeContext=LTE01ERBS1").toString());
        request.accept("application/json");
        ClientResponse<String> response;
        try {
            response = request.get(String.class);

            assertEquals(Status.OK.getStatusCode(), response.getStatus());

        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testRunQueryToFindContainment() {

        final ClientRequest request = new ClientRequest(buildUri("containment", "MeContext=LTE01ERBS1").toString());
        request.accept("application/json");
        ClientResponse<String> response;
        try {
            response = request.get(String.class);

            assertEquals(Status.OK.getStatusCode(), response.getStatus());

        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    URI buildUri(final String... paths) {
        final UriBuilder builder = UriBuilder.fromUri(deploymentUrl.toString());
        for (final String path : paths) {
            builder.path(path);
        }
        return builder.build();

    }

}
