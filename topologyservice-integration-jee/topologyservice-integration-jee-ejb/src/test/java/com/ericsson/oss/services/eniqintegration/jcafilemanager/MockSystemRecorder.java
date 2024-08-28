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
package com.ericsson.oss.services.eniqintegration.jcafilemanager;

import com.ericsson.oss.itpf.sdk.recording.*;

public class MockSystemRecorder implements SystemRecorder {

    public String eventType;
    public EventLevel eventLevel;
    public String source;
    public String resource;
    public String additionalInformation;
    public String errorId;
    public ErrorSeverity severity;
    public String commandName;
    public CommandPhase commandPhase;
    public String originalState;
    public String currentState;
    public String distinguishedName;
    public String eventStatus;
    public String target;

    @Override
    public void recordEvent(final String eventType, final EventLevel eventLevel, final String source, final String resource,
                            final String additionalInformation) {
        this.eventType = eventType;
        this.eventLevel = eventLevel;
        this.source = source;
        this.resource = resource;
        this.additionalInformation = additionalInformation;
    }

    @Override
    public void recordError(final String errorId, final ErrorSeverity severity, final String source, final String resource,
                            final String additionalInformation) {
        this.errorId = errorId;
        this.severity = severity;
        this.source = source;
        this.resource = resource;
        this.additionalInformation = additionalInformation;
    }

    @Override
    public void recordCommand(final String commandName, final CommandPhase commandPhase, final String source, final String resource,
                              final String additionalInfo) {
        this.commandName = commandName;
        this.commandPhase = commandPhase;
        this.source = source;
        this.resource = resource;
        this.additionalInformation = additionalInfo;
    }

    @Override
    public void recordNetworkStatus(final String commandName, final String source, final String resource, final String originalState,
                                    final String currentState, final String additionalInfo) {
        this.commandName = commandName;
        this.originalState = originalState;
        this.currentState = currentState;
        this.source = source;
        this.resource = resource;
        this.additionalInformation = additionalInfo;
    }

    @Override
    public void recordSecurityEvent(final String target, final String distinguishedName, final String additionalInfo, final String eventType,
                                    final ErrorSeverity severity, final String eventStatus) {
        this.target = target;
        this.distinguishedName = distinguishedName;
        this.eventType = eventType;
        this.severity = severity;
        this.eventStatus = eventStatus;
        this.additionalInformation = additionalInfo;
    }
}
