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

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
public class AttributeItem {
    @XmlElement
    public String key;
    @XmlElement
    public Object value;

    public AttributeItem(final String key, final Object value) {
        this.key = key;
        this.value = value;
    }
}
