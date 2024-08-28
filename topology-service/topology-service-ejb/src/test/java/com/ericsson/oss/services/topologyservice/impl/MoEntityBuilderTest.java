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
import org.mockito.runners.MockitoJUnitRunner;

import com.ericsson.oss.itpf.datalayer.dps.persistence.ManagedObject;
import com.ericsson.oss.itpf.datalayer.dps.persistence.PersistenceObject;
import com.ericsson.oss.services.topologyservice.api.AttributeItem;
import com.ericsson.oss.services.topologyservice.api.MoEntity;

@RunWith(MockitoJUnitRunner.class)
public class MoEntityBuilderTest {

    private final Map<String, Object> ALL_ATTRIBUTES = new HashMap<>(2);
    private final static String ATTRIBUTE1_NAME = "userId";
    private final static String ATTRIBUTE1_VALUE = "ericsson";
    private final static String ATTRIBUTE2_NAME = "category";
    private final static String ATTRIBUTE2_VALUE = "Topology Service";

    private final static long poidValue = 123l;
    private final static String namespace = "some model";
    private final static String namespaceVersion = "some version";
    private final static String type = "some mo type";

    private final static String fdn = "MeContext=LTE01ERBS01";

    @Mock
    PersistenceObject persistenceObject;

    @Mock
    ManagedObject managedObject;

    @Before
    public void before() {
        ALL_ATTRIBUTES.put(ATTRIBUTE1_NAME, ATTRIBUTE1_VALUE);
        ALL_ATTRIBUTES.put(ATTRIBUTE2_NAME, ATTRIBUTE2_VALUE);
    }

    @Test
    public void testBuildWithPO() {

        when(persistenceObject.getPoId()).thenReturn(poidValue);
        when(persistenceObject.getType()).thenReturn(type);
        when(persistenceObject.getVersion()).thenReturn(namespaceVersion);
        when(persistenceObject.getNamespace()).thenReturn(namespace);
        when(persistenceObject.getAllAttributes()).thenReturn(ALL_ATTRIBUTES);

        final MoEntity moEntity = new MoEntityBuilder().build(persistenceObject);

        final List<AttributeItem> items = moEntity.getItems();

        assertEquals(String.valueOf(poidValue), moEntity.getFdn());

        for (final AttributeItem item : items) {

            assertTrue(ALL_ATTRIBUTES.containsKey(item.key));
            assertEquals(ALL_ATTRIBUTES.get(item.key), item.value);

        }

    }

    @Test
    public void testBuildWithMO() {

        when(managedObject.getFdn()).thenReturn(fdn);

        final MoEntity moEntity = new MoEntityBuilder().build(managedObject);

        assertEquals(fdn, moEntity.getFdn());

    }
}
