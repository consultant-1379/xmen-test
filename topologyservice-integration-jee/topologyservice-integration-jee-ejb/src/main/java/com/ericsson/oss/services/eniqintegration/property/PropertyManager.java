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
package com.ericsson.oss.services.eniqintegration.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.ericsson.oss.services.eniqintegration.jcafilemanager.JcaFileManager;

public class PropertyManager {

    JcaFileManager jcaFileManager;
    Map<String, String> propertiesValueSet;
    String fileLocation = System.getProperty("user.home") + File.separator + "topologyservicefilewriter" + File.separator + "config.properties";

    public PropertyManager(){
        propertiesValueSet = new Hashtable<String, String>();
    }

    /**
     * Add Key&Value for property file
     * */
    public void addProperty(final String name, final String value) {
        propertiesValueSet.put(name, value);
        jcaFileManager = new JcaFileManager();
    }

    /**
     * Write Hashtable into properties
     * */
    public void writeProperties() {
        final Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream(fileLocation);

            // set the properties value
            final Iterator<Map.Entry<String, String>> iter = propertiesValueSet.entrySet().iterator();

            while (iter.hasNext()) {
                final Map.Entry<String, String> entry = iter.next();
                prop.setProperty(entry.getKey(), entry.getValue());
            }

            // save properties to project root folder
            prop.store(output, null);

        } catch (final IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Get property by name
     * */
    public String getProperty(final String name) {
        if (propertiesValueSet.containsKey(name)) {
            return propertiesValueSet.get(name);
        }

        return "NULL";
    }

    /**
     * Print all properties
     * */
    public void readAllProperties() {
        final Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(fileLocation);

            // load a properties file
            prop.load(input);

            // convert data into Hashtable
            final Set<Entry<Object, Object>> set = prop.entrySet();

            final Iterator<Entry<Object, Object>> iter = set.iterator();

            while (iter.hasNext()) {
                final Entry<Object, Object> entry = iter.next();
                this.addProperty(entry.getKey().toString(), entry.getValue().toString());
            }

        } catch (final IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
