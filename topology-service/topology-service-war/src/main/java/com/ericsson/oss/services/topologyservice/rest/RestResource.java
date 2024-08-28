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
package com.ericsson.oss.services.topologyservice.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;

import com.ericsson.oss.itpf.sdk.recording.ErrorSeverity;
import com.ericsson.oss.itpf.sdk.recording.SystemRecorder;
import com.ericsson.oss.services.topologyservice.api.MoEntity;
import com.ericsson.oss.services.topologyservice.api.TopologyServiceLocal;
import com.ericsson.oss.services.topologyservice.exception.NoDataAvailable;
import com.ericsson.oss.services.topologyservice.exception.TopologyServiceException;

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
@Path("/")
@Produces({ MediaType.APPLICATION_JSON })
@Stateless
public class RestResource {

    @Inject
    private Logger logger;

    @Inject
    private SystemRecorder recorder;

    @Inject
    private TopologyServiceLocal service;

    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public String message() {
        return "Restful Topology Service";
    }

    @Path("{name_space}/{type}")
    @GET
    public Response getAll(@PathParam("name_space") final String nameSpace, @PathParam("type") final String type,
                           @Context final HttpServletRequest req) {

        List<MoEntity> mos;
        try {
            mos = service.runQuery(nameSpace, type);
            return Response.status(Status.OK).entity(mos).build();
        } catch (final NoDataAvailable e) {
            logger.warn("NO data available", e);
            recorder.recordError("RestfulTopologyService.NoDataAvailable", ErrorSeverity.ERROR,
                    req.getRemoteHost() + "(" + req.getRemoteAddr() + ")", req.getRequestURI(), e.getMessage());
            return Response.status(Status.NO_CONTENT).build();
        } catch (final TopologyServiceException e) {
            logger.warn("Execute topology service exception", e);
            recorder.recordError("RestfulTopologyService.Exception", ErrorSeverity.ERROR, req.getRemoteHost() + "(" + req.getRemoteAddr() + ")",
                    req.getRequestURI(), e.getMessage());
            return Response.status(Status.NOT_FOUND).build();
        }

    }

    @Path("{name_space}/{type}/{base}")
    @GET
    public Response getAllFromBase(@PathParam("name_space") final String nameSpace, @PathParam("type") final String type,
                                   @PathParam("base") final String baseMo, @Context final HttpServletRequest req) {

        List<MoEntity> mos;
        try {
            mos = service.runQuery(nameSpace, type, baseMo);
            return Response.status(Status.OK).entity(mos).build();
        } catch (final NoDataAvailable e) {
            logger.warn("NO data available", e);
            recorder.recordError("RestfulTopologyService.NoDataAvailable", ErrorSeverity.ERROR,
                    req.getRemoteHost() + "(" + req.getRemoteAddr() + ")", req.getRequestURI(), e.getMessage());
            return Response.status(Status.NO_CONTENT).build();
        } catch (final TopologyServiceException e) {
            logger.warn("Execute topology service exception", e);
            recorder.recordError("RestfulTopologyService.Exception", ErrorSeverity.ERROR, req.getRemoteHost() + "(" + req.getRemoteAddr() + ")",
                    req.getRequestURI(), e.getMessage());
            return Response.status(Status.NOT_FOUND).build();
        }

    }

    @Path("containment/{base}")
    @GET
    public Response getContainment(@PathParam("base") final String baseMo, @Context final HttpServletRequest req) {

        List<MoEntity> mos;
        try {
            mos = service.runContainmentQuery(baseMo);
            return Response.status(Status.OK).entity(mos).build();
        } catch (final NoDataAvailable e) {
            logger.warn("NO data available", e);
            recorder.recordError("RestfulTopologyService.NoDataAvailable", ErrorSeverity.ERROR,
                    req.getRemoteHost() + "(" + req.getRemoteAddr() + ")", req.getRequestURI(), e.getMessage());
            return Response.status(Status.NO_CONTENT).build();
        } catch (final TopologyServiceException e) {
            logger.warn("Execute topology service exception", e);
            recorder.recordError("RestfulTopologyService.Exception", ErrorSeverity.ERROR, req.getRemoteHost() + "(" + req.getRemoteAddr() + ")",
                    req.getRequestURI(), e.getMessage());
            return Response.status(Status.NOT_FOUND).build();
        }
    }

}
