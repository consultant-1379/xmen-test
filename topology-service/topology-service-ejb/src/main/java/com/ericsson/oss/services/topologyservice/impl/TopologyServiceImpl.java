package com.ericsson.oss.services.topologyservice.impl;

import java.util.*;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ericsson.oss.itpf.datalayer.dps.DataBucket;
import com.ericsson.oss.itpf.datalayer.dps.DataPersistenceService;
import com.ericsson.oss.itpf.datalayer.dps.persistence.PersistenceObject;
import com.ericsson.oss.itpf.datalayer.dps.query.*;
import com.ericsson.oss.itpf.sdk.core.annotation.EServiceRef;
import com.ericsson.oss.services.topologyservice.api.MoEntity;
import com.ericsson.oss.services.topologyservice.api.TopologyServiceLocal;
import com.ericsson.oss.services.topologyservice.exception.*;

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

/**
 * 
 * implementation of the TopologyService interfaces
 * 
 */
@Stateless
@Local(TopologyServiceLocal.class)
public class TopologyServiceImpl implements TopologyServiceLocal {

    @Inject
    private Logger logger;

    @EServiceRef
    private DataPersistenceService service;

    @Override
    public List<MoEntity> runQuery(final String nameSpace, final String type) throws TopologyServiceException {

        try {
            //retrieve the data bucket that the query will be executed against
            final DataBucket liveBucket = service.getLiveBucket();

            final Query<TypeRestrictionBuilder> query = formQuery(nameSpace, type);

            //retrieve the query executor
            final QueryExecutor queryExecutor = liveBucket.getQueryExecutor();

            //execute the query and receive the result as an iterator of persistence object
            final Iterator<PersistenceObject> iterator = queryExecutor.execute(query);

            final List<MoEntity> moList = transform(iterator);

            if (moList.size() == 0) {
                throw new NoDataAvailable("no data available");
            }

            return moList;

        } catch (final Exception e) {

            logger.warn("Exception thrown from DPS", e);
            throw new ServiceExecutionException("exception from DPS", e);

        }

    }

    @Override
    public List<MoEntity> runQuery(final String nameSpace, final String type, final String baseMo) throws TopologyServiceException {

        try {

            //retrieve the data bucket that the query will be executed against
            final DataBucket liveBucket = service.getLiveBucket();

            final Query<TypeContainmentRestrictionBuilder> query = formQuery(nameSpace, type, baseMo);

            //retrieve the query executor
            final QueryExecutor queryExecutor = liveBucket.getQueryExecutor();

            //execute the query and receive the result as an iterator of persistence object
            final Iterator<PersistenceObject> iterator = queryExecutor.execute(query);

            final List<MoEntity> moList = transform(iterator);

            if (moList.size() == 0) {
                throw new NoDataAvailable("no data available");
            }

            return moList;

        } catch (final Exception e) {

            logger.warn("Exception thrown from DPS", e);
            throw new ServiceExecutionException("exception from DPS", e);

        }
    }

    @Override
    public List<MoEntity> runContainmentQuery(final String baseMo) throws TopologyServiceException {
        try {
            //retrieve the data bucket that the query will be executed against
            final DataBucket liveBucket = service.getLiveBucket();

            final Query<ContainmentRestrictionBuilder> query = formQuery(baseMo);

            //retrieve the query executor
            final QueryExecutor queryExecutor = liveBucket.getQueryExecutor();

            //execute the query and receive the result as an iterator of persistence object
            final Iterator<PersistenceObject> iterator = queryExecutor.execute(query);

            final List<MoEntity> moList = transform(iterator);

            if (moList.size() == 0) {
                throw new NoDataAvailable("no data available");
            }

            return moList;

        } catch (final Exception e) {

            logger.warn("Exception thrown from DPS", e);
            throw new ServiceExecutionException("exception from DPS", e);

        }

    }

    /**
     * 
     * Transform the iterator of {@link PersistenceObject} to @{link MoEntity} list
     * 
     * @param iterator
     * @return
     */
    public List<MoEntity> transform(final Iterator<PersistenceObject> iterator) {

        final List<MoEntity> mos = new ArrayList<>();

        while (iterator.hasNext()) {
            final PersistenceObject pObject = iterator.next();
            final MoEntity moEntity = new MoEntityBuilder().build(pObject);
            mos.add(moEntity);
        }

        return mos;
    }

    private Query<TypeRestrictionBuilder> formQuery(final String nameSpace, final String type) {

        final QueryBuilder queryBuilder = service.getQueryBuilder();
        final Query<TypeRestrictionBuilder> typedQuery = queryBuilder.createTypeQuery(nameSpace, type);

        return typedQuery;
    }

    private Query<TypeContainmentRestrictionBuilder> formQuery(final String nameSpace, final String type, final String baseMo) {

        final QueryBuilder queryBuilder = service.getQueryBuilder();
        final Query<TypeContainmentRestrictionBuilder> typedQuery = queryBuilder.createTypeQuery(nameSpace, type, baseMo);

        return typedQuery;
    }

    private Query<ContainmentRestrictionBuilder> formQuery(final String baseMo) {

        final QueryBuilder queryBuilder = service.getQueryBuilder();
        final Query<ContainmentRestrictionBuilder> typedQuery = queryBuilder.createContainmentQuery(baseMo);

        return typedQuery;
    }

}
