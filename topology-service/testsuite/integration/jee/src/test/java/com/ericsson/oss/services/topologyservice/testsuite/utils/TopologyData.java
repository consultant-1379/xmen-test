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

/**
 * Define some common information for topology
 */
public interface TopologyData {

    /**
     * model namespace and version
     */
    String TOP_NAMESPACE = "OSS_TOP";
    String TOP_NAMESPACE_VERSION = "1.1.0";
    String CPP_NAMESPACE = "CPP_NODE_MODEL";
    String CPP_NAMESPACE_VERSION = "3.12.0";
    String ERBS_NAMESPACE = "ERBS_NODE_MODEL";
    String ERBS_NAMESPACE_VERSION = "3.1.72";

    /**
     * Common name, attribute name and values
     */
    String MANAGED_ELEMENT = "ManagedElement";
    String ME_CONTEXT = "MeContext";
    String USER_LABEL = "userLabel";
    String ENODEB_FUNCTION = "ENodeBFunction";
    String ID_VALUE = "1";

    /**
     * MeContext MO attributes
     */
    String MANDATORY_ME_CONTEXT_ID = "MeContextId";
    String VALUE_NE_TYPE = "ENODEB";
    String ATTRIBUTE_NE_TYPE = "neType";
    String MANDATORY_GENERATION_COUNTER = "generationCounter";
    String MANDATORY_MIRROR_SYNC_STATUS = "mirrorSynchronizationStatus";

    /**
     * ManagedElement MO attributes
     */
    String MANDATORY_MANAGED_ELEMENT_ATTRIBUTE = "ManagedElementId";

    String MIM_INFO = "mimInfo";

    /**
     * MIM attributes
     */
    String C_1_72 = "C.1.72";
    String L_2_29 = "L.2.29";
    String MIM_NAME = "mimName";
    String MIM_VERSION = "mimVersion";
    String MIM_RELEASE = "mimRelease";

    /**
     * EnodeBFunction MO attributes
     */
    String MANDATORY_ENODEB_FUNCTION_ATTRIBUTE = "ENodeBFunctionId";

    String ENODEB_FUNCTTION_ATTRIB_eNBId = "eNBId";
    String ENODEB_FUNCTTION_ATTRIB_dnsLookupTimer = "dnsLookupTimer";
    String ENODEB_FUNCTION_ATTRIB_DSCP_LABEL = "dscpLabel";
    String ENODEB_FUNCTION_ATTRIB_OA_M_LINK_SUPER_VISION_ACTIVE = "oaMLinkSuperVisionActive";
    String ENODEB_FUNCTION_ATTRIBX_2_RETRY_TIMER_MAX_AUTO = "x2retryTimerMaxAuto";
    String ENODEB_FUNCTION_ATTRIB_X_2_RETRY_TIMER_START = "x2retryTimerStart";
    String ENODEB_FUNCTION_ATTRIB_S_1_RETRY_TIMER = "s1RetryTimer";
    String ENODEB_FUNCTION_ATTRIB_UL_SCHEDULER_DYNAMIC_BW_ALLOCATION_ENABLED = "ulSchedulerDynamicBWAllocationEnabled";
    String ENODEB_FUNCTION_ATTRIB_NNSF_MODE = "nnsfMode";
    String ENODEB_FUNCTION_ATTRIB_PM_ZTEMPORARY_35 = "pmZtemporary35";
    String ENODEB_FUNCTION_ATTRIB_ZZZ_TEMPORARY_3 = "zzzTemporary3";

    String NODE_NAME_PREFIX = "LTE01ERBS";

}
