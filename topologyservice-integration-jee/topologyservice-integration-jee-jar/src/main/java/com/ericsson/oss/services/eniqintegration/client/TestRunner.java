package com.ericsson.oss.services.eniqintegration.client;

import java.util.Hashtable;

import javax.naming.*;

import com.ericsson.oss.services.eniqintegration.property.PropertyManager;
import com.ericsson.oss.services.eniqintegration.remotecall.TAFBeanRemote;

public class TestRunner {

    private PropertyManager propertyManager;
    private Hashtable<String, Object> jndiProperties;
    private Context context;

    /**
     * Client program for running TAF test
     * 
     * @param argv
     *        The command line arguments which are ignored.
     * @throws NamingException
     */
    public static void main(final String[] args) throws NamingException {
        final TestRunner testRunner = new TestRunner();
        System.out.println(testRunner.invokeBean());
    }

    public TestRunner() throws NamingException {
        getProperties();
        buildJndiProperties();
        buildContext();
    }

    private void getProperties() {
        propertyManager = new PropertyManager();
        propertyManager.readAllProperties();
    }

    private void buildJndiProperties() {
        // The "standard" JNDI lookup
        jndiProperties = new Hashtable<String, Object>();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false"); // needed for a login module that requires the
                                                                                                                     // password in plaintext
        jndiProperties.put("jboss.naming.client.ejb.context", true);

        jndiProperties.put(Context.PROVIDER_URL, propertyManager.getProperty("ProviderUrl"));
        jndiProperties.put(Context.SECURITY_PRINCIPAL, propertyManager.getProperty("SecurityPrincipal"));
        jndiProperties.put(Context.SECURITY_CREDENTIALS, propertyManager.getProperty("SecurityCredentials"));
    }

    private void buildContext() throws NamingException {
        context = new InitialContext(jndiProperties);
    }

    public String invokeBean() throws NamingException {
        final TAFBeanRemote bean = (TAFBeanRemote) context
                .lookup("topologyservice-integration-jee-ear-1.0.1-SNAPSHOT/topologyservice-integration-jee-ejb-1.0.1-SNAPSHOT/TAFBean!com.ericsson.oss.services.eniqintegration.remotecall.TAFBeanRemote");
        String result = null;
        try {
            result = bean.runTest();
        } catch (final Exception e) {
            result = "File writing failed: " + e.getMessage();
        }
        return result;

    }

    // Added for testing
    public void setPropertyManager(final PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    // Added for testing
    public Hashtable<String, Object> getJndiProperties() {
        return jndiProperties;
    }

    // Added for testing
    public void setContext(final Context context) {
        this.context = context;
    }

    public void rebuildJndiProperties() {
        buildJndiProperties();
    }
}
