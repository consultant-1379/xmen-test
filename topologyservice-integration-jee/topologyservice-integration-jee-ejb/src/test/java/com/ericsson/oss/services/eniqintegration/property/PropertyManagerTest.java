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

import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PropertyManagerTest {
    Map<String, String> propertValueSet;
    PropertyManager propertyManager;
    final String fileLocation = System.getProperty("java.io.tmpdir") + File.separator + "PropertyManagerTest.properties";

    @Before
    public void initiTest() {
        propertyManager = new PropertyManager();
        propertyManager.addProperty("FilePath", "testfile.txt");
    }

    @Test
    public void getPropertyTest() {

        propertyManager.fileLocation = fileLocation;
        propertyManager.writeProperties();
        propertyManager.readAllProperties();
        assertEquals("testfile.txt", propertyManager.getProperty("FilePath"));
    }

}
