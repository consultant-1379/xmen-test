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

import java.util.*;

import com.ericsson.oss.itpf.datalayer.dps.persistence.ManagedObject;
import com.ericsson.oss.itpf.datalayer.dps.persistence.PersistenceObject;
import com.ericsson.oss.services.topologyservice.api.AttributeItem;
import com.ericsson.oss.services.topologyservice.api.MoEntity;

/**
 * This class is intended to create {@link MoEntity} object
 */
public class MoEntityBuilder {

    /**
     * build the {@link MoEntity} from {@link PersistenceObject}
     * 
     * @param pObject
     * @return
     */
    public MoEntity build(final PersistenceObject pObject) {

        String fdn;

        if (pObject instanceof ManagedObject) {
            final ManagedObject managedObject = (ManagedObject) pObject;
            fdn = managedObject.getFdn();
        } else {
            fdn = String.valueOf(pObject.getPoId());
        }
        final List<AttributeItem> items = getFormattedAttributes(pObject.getAllAttributes());

        return new MoEntity(fdn, items);

    }

    /**
     * @param allAttributes
     * @return
     */
    private List<AttributeItem> getFormattedAttributes(final Map<String, Object> allAttributes) {
        if (allAttributes == null) {
            return Collections.emptyList();
        }

        final List<AttributeItem> attributeItems = new ArrayList<>();

        for (final Map.Entry<String, Object> entry : allAttributes.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            attributeItems.add(new AttributeItem(key, value));
        }
        return attributeItems;
    }

}
