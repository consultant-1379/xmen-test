package com.ericsson.oss.services.eniqintegration.remotecall;

import javax.ejb.Remote;

@Remote
public interface TAFBeanRemote {
    String runTest();
}
