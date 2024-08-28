package com.ericsson.oss.services.topologyservice.api;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.*;

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

@XmlAccessorType(XmlAccessType.NONE)
public class MoEntity implements Serializable {

    private static final long serialVersionUID = -8533297587967662708L;

    @XmlElement
    private String fdn = "";

    @XmlElement
    private List<AttributeItem> items = null;

    public MoEntity() {

    }

    public MoEntity(final String fdn, final List<AttributeItem> items) {

        this.fdn = fdn;
        this.items = items;
    }

    /**
     * @return the fdn
     */
    public String getFdn() {
        return fdn;
    }

    /**
     * @return the items
     */
    public List<AttributeItem> getItems() {
        return items;
    }

}
