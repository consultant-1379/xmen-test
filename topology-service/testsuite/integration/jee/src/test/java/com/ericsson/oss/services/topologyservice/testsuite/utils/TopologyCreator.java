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
package com.ericsson.oss.services.topologyservice.testsuite.utils;

import java.util.*;

import javax.inject.Inject;
import javax.transaction.*;

import org.slf4j.Logger;

import com.ericsson.oss.itpf.datalayer.dps.DataBucket;
import com.ericsson.oss.itpf.datalayer.dps.persistence.ManagedObject;
import com.ericsson.oss.itpf.datalayer.dps.persistence.PersistenceObject;
import com.ericsson.oss.itpf.datalayer.dps.query.Query;
import com.ericsson.oss.itpf.datalayer.dps.query.TypeRestrictionBuilder;
import com.ericsson.oss.services.topologyservice.testsuite.DpsFacade;

/**
 * provide methods to populate the versant database, it should be extended by test classes
 */
@SuppressWarnings("PMD")
public class TopologyCreator implements TopologyData {

    @Inject
    DpsFacade dpsFacade;

    @Inject
    protected UserTransaction userTransaction;

    @Inject
    private Logger logger;

    /**
     * This method checks for all the transaction with either rollback or active status and rollback all these transactions. It is used to keep the
     * clean state after every test case run.
     */
    public void rollbackTransactionIfActive() throws Exception {
        if (isActive(userTransaction) || isMarkedForRollback(userTransaction)) {
            logger.info("Rolling back transaction");
            userTransaction.rollback();
        }
    }

    /**
     * This method checks for transaction with rollback status
     * 
     * @param utx
     *            UserTransaction
     */
    private boolean isMarkedForRollback(final UserTransaction utx) {
        try {
            final int status = utx.getStatus();
            return status == Status.STATUS_MARKED_ROLLBACK;
        } catch (final SystemException e) {
            return false;
        }
    }

    /**
     * 
     * This method checks for active transaction
     * 
     * @param utx
     * 
     */
    private boolean isActive(final UserTransaction utx) {
        try {
            final int status = utx.getStatus();
            return status == Status.STATUS_ACTIVE;
        } catch (final SystemException e) {
            return false;
        }
    }

    /**
     * Remove all the data from database
     * 
     */
    protected void emptyData() throws Exception {

        logger.info("Empty the database first");

        userTransaction.begin();
        final Query<TypeRestrictionBuilder> CPP_query = dpsFacade.getQueryBuilder().createTypeQuery(CPP_NAMESPACE, MANAGED_ELEMENT);
        final Iterator<PersistenceObject> iterator = dpsFacade.getLiveBucket().getQueryExecutor().execute(CPP_query);
        while (iterator.hasNext()) {
            final PersistenceObject persistenceObject = iterator.next();
            dpsFacade.getLiveBucket().deletePo(persistenceObject);
        }

        final Query<TypeRestrictionBuilder> OSS_TOP_query = dpsFacade.getQueryBuilder().createTypeQuery(TOP_NAMESPACE, ME_CONTEXT);
        final Iterator<PersistenceObject> iterator1 = dpsFacade.getLiveBucket().getQueryExecutor().execute(OSS_TOP_query);
        while (iterator1.hasNext()) {
            final PersistenceObject persistenceObject = iterator1.next();
            dpsFacade.getLiveBucket().deletePo(persistenceObject);
        }
        userTransaction.commit();

    }

    /**
     * Prepare some topology data for testing
     * 
     * @throws Exception
     */
    protected void prepareData(final int nodeCount) throws Exception {

        logger.info("Prepare " + nodeCount + " nodes");

        emptyData();

        for (int i = 0; i < nodeCount; i++) {
            final String nodeName = NODE_NAME_PREFIX + i;
            createTopology(nodeName, 4);
        }

    }

    /**
     * Create topology with given node name
     * 
     * @param nodeName
     * @param noTopologyLevels
     * @throws Exception
     */
    protected List<ManagedObject> createTopology(final String nodeName, final int noTopologyLevels) throws Exception {

        userTransaction.begin();

        final DataBucket liveBucket = dpsFacade.getLiveBucket();

        final List<ManagedObject> moList = new ArrayList<>();
        ManagedObject parentMo;
        ManagedObject currentMo = null;

        for (int currentLevel = 1; currentLevel <= noTopologyLevels; currentLevel++) {

            parentMo = currentMo;

            switch (currentLevel) {
                case 1: {
                    // level 1 MeContext Mib in OSS_TOP model
                    currentMo = createMeContext(nodeName, liveBucket);
                    moList.add(currentMo);
                    break;
                }
                case 2: {
                    // level 2 create ManagedElement  under CPP model
                    currentMo = createManagedElement(liveBucket, parentMo);
                    moList.add(currentMo);
                    break;
                }
                case 3: {
                    // level 3 create ENodeBFunction  in ERBSNode model
                    currentMo = createENodeBFunction(liveBucket, parentMo, nodeName);
                    moList.add(currentMo);
                    break;
                }
                case 4: {
                    // level 4  create EUtranCellFDD
                    createEUtranCellFDD(liveBucket, parentMo, nodeName);
                    break;
                }
                default: {
                    logger.info("Topology level {} not implemented yet ", currentLevel);
                }

            }
            logger.info("Test Setup, created: " + currentMo.getFdn());
        }

        userTransaction.commit();

        return moList;

    }

    /**
     * Create MeContext ManagedObject
     * 
     * @param nodeName
     * @param liveBucket
     * @return
     */
    private ManagedObject createMeContext(final String nodeName, final DataBucket liveBucket) {
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put(MANDATORY_ME_CONTEXT_ID, nodeName);
        attributes.put(ATTRIBUTE_NE_TYPE, VALUE_NE_TYPE);
        attributes.put(USER_LABEL, "x-men");
        attributes.put(MANDATORY_GENERATION_COUNTER, new Long(2));
        attributes.put(MANDATORY_MIRROR_SYNC_STATUS, "SYNCING");
        final ManagedObject parentMo = liveBucket.getMibRootBuilder().namespace(TOP_NAMESPACE).type(ME_CONTEXT).version(TOP_NAMESPACE_VERSION)
                .name(nodeName).addAttributes(attributes).create();
        logger.info("Created MeContext: " + parentMo.getFdn());
        return parentMo;
    }

    /**
     * create ManagedElement MO
     * 
     * @param liveBucket
     * @param parentMo
     * @return
     */
    private ManagedObject createManagedElement(final DataBucket liveBucket, final ManagedObject parentMo) {

        final HashMap<String, Object> mimStruct = new HashMap<String, Object>();
        mimStruct.put(MIM_NAME, ERBS_NAMESPACE);
        mimStruct.put(MIM_VERSION, C_1_72);
        mimStruct.put(MIM_RELEASE, L_2_29);
        final Map<String, Object> mandatoryManagedElementAttributes = new HashMap<String, Object>();
        mandatoryManagedElementAttributes.put(MANDATORY_MANAGED_ELEMENT_ATTRIBUTE, ID_VALUE);
        mandatoryManagedElementAttributes.put(MIM_INFO, mimStruct);

        final ManagedObject managedElementMo = liveBucket.getMibRootBuilder().namespace(CPP_NAMESPACE).type(MANAGED_ELEMENT)
                .version(CPP_NAMESPACE_VERSION).name("1").addAttributes(mandatoryManagedElementAttributes).parent(parentMo).create();
        logger.info("Created Managedelement: " + managedElementMo.getFdn());
        return managedElementMo;
    }

    /**
     * create EnodeBFunction MO
     * 
     * @param liveBucket
     * @param managedElementMo
     * @param nodeName
     * @return
     */
    private ManagedObject createENodeBFunction(final DataBucket liveBucket, final ManagedObject managedElementMo, final String nodeName) {
        final Map<String, Object> eNodeBFunctionAttributes = new HashMap<String, Object>();
        eNodeBFunctionAttributes.put(MANDATORY_ENODEB_FUNCTION_ATTRIBUTE, ID_VALUE);
        eNodeBFunctionAttributes.put(USER_LABEL, nodeName);
        try {
            logger.info("node name is even/odd: {}", nodeName.substring(nodeName.length() - 1, nodeName.length()));
            final String lastChar = nodeName.substring(nodeName.length() - 1, nodeName.length());
            if (lastChar.matches("[0-9]") && Integer.valueOf(lastChar) % 2 == 0) {
                eNodeBFunctionAttributes.put(ENODEB_FUNCTTION_ATTRIB_eNBId, new Long(8));
                eNodeBFunctionAttributes.put(ENODEB_FUNCTTION_ATTRIB_dnsLookupTimer, new Long(108));
                eNodeBFunctionAttributes.put(USER_LABEL, "x-men");
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_DSCP_LABEL, 25L);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_OA_M_LINK_SUPER_VISION_ACTIVE, false);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIBX_2_RETRY_TIMER_MAX_AUTO, 1440L);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_X_2_RETRY_TIMER_START, 1000L);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_S_1_RETRY_TIMER, 30L);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_UL_SCHEDULER_DYNAMIC_BW_ALLOCATION_ENABLED, true);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_NNSF_MODE, "RPLMN_IF_SAME_AS_SPLMN");
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_PM_ZTEMPORARY_35, 15L);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_ZZZ_TEMPORARY_3, "aa");
            } else {
                eNodeBFunctionAttributes.put(ENODEB_FUNCTTION_ATTRIB_eNBId, new Long(7));
                eNodeBFunctionAttributes.put(ENODEB_FUNCTTION_ATTRIB_dnsLookupTimer, new Long(107));
                eNodeBFunctionAttributes.put(USER_LABEL, "x-men");
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_DSCP_LABEL, 25L);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_OA_M_LINK_SUPER_VISION_ACTIVE, true);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIBX_2_RETRY_TIMER_MAX_AUTO, 1440L);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_X_2_RETRY_TIMER_START, 1000L);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_S_1_RETRY_TIMER, 30L);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_UL_SCHEDULER_DYNAMIC_BW_ALLOCATION_ENABLED, true);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_NNSF_MODE, "RPLMN_IF_SAME_AS_SPLMN");
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_PM_ZTEMPORARY_35, 15L);
                eNodeBFunctionAttributes.put(ENODEB_FUNCTION_ATTRIB_ZZZ_TEMPORARY_3, "aa");
            }
        } catch (final Exception e) {
            logger.error(e.getMessage());
        }

        final ManagedObject eNodeBFunctionMo = liveBucket.getMibRootBuilder().namespace(ERBS_NAMESPACE).type("ENodeBFunction")
                .version(ERBS_NAMESPACE_VERSION).name("1").addAttributes(eNodeBFunctionAttributes).parent(managedElementMo).create();
        logger.info("Created ENodeBFunction Fdn={}, attributes={} ", eNodeBFunctionMo.getFdn(), eNodeBFunctionAttributes);
        return eNodeBFunctionMo;
    }

    private void createEUtranCellFDD(final DataBucket liveBucket, final ManagedObject parentMo, final String nodeName) {
        final Map<String, Object> cell1Attributes = new HashMap<String, Object>();
        cell1Attributes.put("cellId", new Long(1));
        cell1Attributes.put("physicalLayerCellIdGroup", new Long(0));
        cell1Attributes.put("physicalLayerSubCellId", new Long(0));
        cell1Attributes.put("EUtranCellFDDId", "Cell1");
        cell1Attributes.put("earfcndl", new Long(1700));
        cell1Attributes.put("earfcnul", new Long(18000));
        cell1Attributes.put("sectorFunctionRef", parentMo.getFdn());
        cell1Attributes.put("tac", new Long(0));

        final ManagedObject fddCell1 = liveBucket.getMibRootBuilder().namespace(ERBS_NAMESPACE).type("EUtranCellFDD").version(ERBS_NAMESPACE_VERSION)
                .name("Cell1").addAttributes(cell1Attributes).parent(parentMo).create();
        logger.debug("Created EUtranCellFDD 1: " + fddCell1.getFdn());

        final Map<String, Object> cell2Attributes = new HashMap<String, Object>();
        cell2Attributes.put("cellId", new Long(2));
        cell2Attributes.put("physicalLayerCellIdGroup", new Long(1));
        cell2Attributes.put("physicalLayerSubCellId", new Long(1));
        cell2Attributes.put("EUtranCellFDDId", "Cell2");
        cell2Attributes.put("earfcndl", new Long(1800));
        cell2Attributes.put("earfcnul", new Long(18001));
        cell2Attributes.put("sectorFunctionRef", parentMo.getFdn());
        cell2Attributes.put("tac", new Long(0));

        final ManagedObject fddCell2 = liveBucket.getMibRootBuilder().namespace(ERBS_NAMESPACE).type("EUtranCellFDD").version(ERBS_NAMESPACE_VERSION)
                .name("Cell2").addAttributes(cell2Attributes).parent(parentMo).create();

        logger.debug("Created EUtranCellFDD 2 :  " + fddCell2.getFdn());

    }

}
