package com.ericsson.oss.services.eniqintegration.remotecall;

import javax.ejb.Local;

@Local
public interface TAFBeanLocal {
    String runTest();
}
