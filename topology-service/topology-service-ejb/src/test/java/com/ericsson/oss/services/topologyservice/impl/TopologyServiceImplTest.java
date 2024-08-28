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
package com.ericsson.oss.services.topologyservice.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;

import com.ericsson.oss.itpf.datalayer.dps.DataBucket;
import com.ericsson.oss.itpf.datalayer.dps.DataPersistenceService;
import com.ericsson.oss.itpf.datalayer.dps.persistence.PersistenceObject;
import com.ericsson.oss.itpf.datalayer.dps.query.*;
import com.ericsson.oss.services.topologyservice.api.MoEntity;
import com.ericsson.oss.services.topologyservice.exception.TopologyServiceException;

@RunWith(MockitoJUnitRunner.class)
public class TopologyServiceImplTest {

    @Mock
    DataPersistenceService dataPersistenceService;

    @Mock
    DataBucket dataBucket;

    @Mock
    QueryBuilder queryBuilder;

    @Mock
    Query query;

    @Mock
    QueryExecutor queryExecutor;

    @Mock
    Logger logger;

    private TopologyServiceImpl topologyServiceImpl;

    Collection<PersistenceObject> persistenceObjects = new ArrayList<>();
    private final static String fdn = "fdn for test";
    private final static String nameSpace = "ERBS_NODE_MODEL";
    private final static String type = "MeContext";

    @Before
    public void setUp() {

        topologyServiceImpl = new TopologyServiceImpl();

        final MockedEJBInjector injector = new MockedEJBInjector();

        injector.assign(DataPersistenceService.class, dataPersistenceService);
        injector.assign(Logger.class, logger);

        try {
            injector.inject(topologyServiceImpl);
        } catch (final IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        when(dataPersistenceService.getQueryBuilder()).thenReturn(queryBuilder);
        when(dataPersistenceService.getLiveBucket()).thenReturn(dataBucket);
        when(queryBuilder.createContainmentQuery(fdn)).thenReturn(query);
        when(queryBuilder.createTypeQuery(nameSpace, type)).thenReturn(query);
        when(queryBuilder.createTypeQuery(nameSpace, type, fdn)).thenReturn(query);
        when(dataBucket.getQueryExecutor()).thenReturn(queryExecutor);
        when(queryExecutor.execute(query)).thenAnswer(new Answer<Iterator<PersistenceObject>>() {
            @Override
            public Iterator<PersistenceObject> answer(final InvocationOnMock invocation) throws Throwable {
                return persistenceObjects.iterator();
            }
        });

    }

    @Test
    public void testRunQuery() {

        List<MoEntity> mos;
        try {
            mos = topologyServiceImpl.runQuery(nameSpace, type);
            verify(queryBuilder).createTypeQuery(nameSpace, type);
            assertEquals(0, mos.size());
        } catch (final TopologyServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testRunQueryWithBaseMO() {

        List<MoEntity> mos;
        try {
            mos = topologyServiceImpl.runQuery(nameSpace, type, fdn);
            verify(queryBuilder).createTypeQuery(nameSpace, type, fdn);
            assertEquals(0, mos.size());
        } catch (final TopologyServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testRunContainmentQuery() {

        List<MoEntity> mos;
        try {
            mos = topologyServiceImpl.runContainmentQuery(fdn);
            verify(queryBuilder).createContainmentQuery(fdn);
            assertEquals(0, mos.size());
        } catch (final TopologyServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
