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
package com.ericsson.oss.services.topologyservice.testsuite;

import java.util.Collection;

import com.ericsson.oss.itpf.datalayer.dps.DataBucket;
import com.ericsson.oss.itpf.datalayer.dps.DataPersistenceService;
import com.ericsson.oss.itpf.datalayer.dps.query.QueryBuilder;
import com.ericsson.oss.itpf.sdk.core.annotation.EServiceRef;

public class DpsFacade implements DataPersistenceService {

    @EServiceRef
    private DataPersistenceService dps;

    @Override
    public DataBucket getLiveBucket() {
        return dps.getLiveBucket();
    }

    @Override
    public DataBucket getDataBucket(final String bucketName, final String... optionalParameters) {
        return dps.getDataBucket(bucketName, optionalParameters);
    }

    @Override
    public QueryBuilder getQueryBuilder() {
        return dps.getQueryBuilder();
    }

    @Override
    public DataBucket createDataBucket(final String arg0, final String arg1, final String... arg2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long deleteDataBucket(final String arg0, final boolean arg1) {
        return 0; //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<String> getAllDataBucketNames() {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }
}
