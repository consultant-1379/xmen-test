/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2014
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.services.eniqintegration.client;

import static org.junit.Assert.*;

import java.util.Hashtable;

import javax.naming.*;

import org.junit.Before;
import org.junit.Test;

import com.ericsson.oss.services.eniqintegration.property.PropertyManager;
import com.ericsson.oss.services.eniqintegration.remotecall.TAFBeanRemote;

public class TestRunnerTest {

    private static final String REMOTE_TESTURL = "remote://testurl";
    private static final String BEAN_USER = "beanUser";
    private static final String BEAN_USER_PWD = "beanUserPwd";

    TestRunner testRunner;
    PropertyManager testProps;

    @Before
    public void setUp() throws NamingException {
        testRunner = new TestRunner();

        testProps = new PropertyManager();
        testProps.addProperty("ProviderUrl", REMOTE_TESTURL);
        testProps.addProperty("SecurityPrincipal", BEAN_USER);
        testProps.addProperty("SecurityCredentials", BEAN_USER_PWD);
    }

    @Test
    public void testBuildJndiProperties() {
        testRunner.setPropertyManager(testProps);
        testRunner.rebuildJndiProperties();
        final Hashtable<String, Object> jndiProps = testRunner.getJndiProperties();

        assertEquals(REMOTE_TESTURL, jndiProps.get(Context.PROVIDER_URL));
        assertEquals(BEAN_USER, jndiProps.get(Context.SECURITY_PRINCIPAL));
        assertEquals(BEAN_USER_PWD, jndiProps.get(Context.SECURITY_CREDENTIALS));
    }

    @Test
    public void testInvokeBeanCallsRemoteBean() throws NamingException {
        final Context context = new MockContext();
        testRunner.setContext(context);
        assertEquals("Test Was Run", testRunner.invokeBean());
    }

}

class MockContext extends InitialContext {

    public MockContext() throws NamingException {
    }

    @Override
    public Object lookup(final String name) throws NamingException {
        return new MockTAFBeanRemote();
    }

}

class MockTAFBeanRemote implements TAFBeanRemote {

    @Override
    public String runTest() {
        return "Test Was Run";
    }

}