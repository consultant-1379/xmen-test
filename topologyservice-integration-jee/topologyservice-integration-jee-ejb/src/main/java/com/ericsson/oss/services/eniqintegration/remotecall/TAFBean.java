package com.ericsson.oss.services.eniqintegration.remotecall;

import javax.ejb.*;
import javax.inject.Inject;

import com.ericsson.oss.itpf.sdk.recording.*;
import com.ericsson.oss.services.eniqintegration.jcafilemanager.JcaFileManager;
import com.ericsson.oss.services.eniqintegration.property.PropertyManager;

/**
 * Session Bean implementation class TAFBean
 */
@Stateless
@Remote(TAFBeanRemote.class)
@Local(TAFBeanLocal.class)
public class TAFBean implements TAFBeanRemote, TAFBeanLocal {

    private static final String LOG_RESOURCE = "TAFBean.runTest";
    private static final String LOG_SOURCE_RUN_TEST = "Run Test";
    private static final String LOG_EVENT_TYPE = "TOPOLOGY_SERVICE.TAF_TEST";
    private static final int EXCERPT_LENGTH = 50;

    @Inject
    private SystemRecorder systemRecorder;

    public final String TEST_DIRECTORY = System.getProperty("java.io.tmpdir") + "topologyservice-filewriter-taftest";

    @Inject
    JcaFileManager jcaFileManager;

    /**
     * @see TAFBeanRemote#runTest()
     */
    @Override
    public String runTest() {

        // load properties from file
        final PropertyManager propertyManager = new PropertyManager();
        propertyManager.readAllProperties();

        // get file path from properties
        final String filePath = propertyManager.getProperty("FilePath");
        systemRecorder
                .recordEvent(LOG_EVENT_TYPE, EventLevel.COARSE, LOG_SOURCE_RUN_TEST, LOG_RESOURCE, "Read filepath from properties: " + filePath);

        // get file contents from properties
        final String fileContent = propertyManager.getProperty("FileContent");

        String fileContentExcerpt = fileContent;
        if (fileContent.length() > EXCERPT_LENGTH) {
            fileContentExcerpt = fileContent.substring(0, EXCERPT_LENGTH) + "[...]";
        }

        systemRecorder.recordEvent(LOG_EVENT_TYPE, EventLevel.COARSE, LOG_SOURCE_RUN_TEST, LOG_RESOURCE, "Read file content from properties: "
                + fileContentExcerpt);

        jcaFileManager.setFileName(filePath);

        systemRecorder.recordEvent(LOG_EVENT_TYPE, EventLevel.COARSE, LOG_SOURCE_RUN_TEST, LOG_RESOURCE, "About to write to file... "
                + fileContentExcerpt);

        try {
            jcaFileManager.write(fileContent.getBytes(), false);
            systemRecorder.recordEvent(LOG_EVENT_TYPE, EventLevel.COARSE, LOG_SOURCE_RUN_TEST, LOG_RESOURCE, "Successfully wrote content to file.");
            return "File " + filePath + " has been created.";
        } catch (final Exception e) {
            final String errorMessage = "Error writing file " + filePath + ": " + e.getMessage() + ": " + e.getCause().getMessage();
            systemRecorder.recordError("FILE WRITING ERROR", ErrorSeverity.ERROR, LOG_SOURCE_RUN_TEST, LOG_RESOURCE, "Error writing file "
                    + errorMessage);
            return errorMessage;
        }

    }

    // Added For Testing
    public void setJcaFileManager(final JcaFileManager jcaFileManager) {
        this.jcaFileManager = jcaFileManager;
    }

    // Added For Testing
    public void setSystemRecorder(final SystemRecorder systemRecorder) {
        this.systemRecorder = systemRecorder;
    }
}
