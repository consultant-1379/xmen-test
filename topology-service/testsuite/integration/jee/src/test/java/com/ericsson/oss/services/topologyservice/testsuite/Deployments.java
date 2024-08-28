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
package com.ericsson.oss.services.topologyservice.testsuite;

import static com.ericsson.oss.services.topologyservice.testsuite.Artifact.*;

import java.io.File;

import org.jboss.arquillian.container.test.api.Testable;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.*;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import com.ericsson.oss.services.topologyservice.api.TopologyService;
import com.ericsson.oss.services.topologyservice.exception.TopologyServiceException;
import com.ericsson.oss.services.topologyservice.impl.TopologyServiceImpl;
import com.ericsson.oss.services.topologyservice.testsuite.utils.TopologyCreator;

/**
 * used to create ejb archive and war for arquillian test
 */
public class Deployments {

    static {
        final String modelRepoXmlPath = System.getProperty("xmlRepoPath", MODEL_REPO_XML_PATH);
        System.setProperty("xmlRepoPath", modelRepoXmlPath);

        final String modelRoot = System.getProperty("ericsson-model-service-root", MODEL_ROOT);
        System.setProperty("ericsson-model-service-root", modelRoot);
    }

    public static EnterpriseArchive createDeployement(final Class clazz) {

        final MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        final File warFile = resolver.artifact("com.ericsson.oss.services.topologyservice:topology-service-war:war").resolveAsFiles()[0];

        final WebArchive webArchive = Testable.archiveToTest(ShrinkWrap.create(ZipImporter.class, "test.war").importFrom(warFile)
                .as(WebArchive.class));

        final EnterpriseArchive archive = ShrinkWrap.create(EnterpriseArchive.class, clazz.toString() + ".ear")
                .addAsModule(createModuleArchive(clazz)).addAsModule(webArchive)
                .addAsLibraries(resolver.artifact(Artifact.ITPF_SERVICE_FRAMEWORK_DIST).resolveAsFiles()).setManifest(Artifact.MANIFEST_MF_FILE)
                .addAsManifestResource(DEPLOYMENT_STRUCTURE);

        return archive;
    }

    private static Archive<?> createModuleArchive(final Class clazz) {
        final JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "topology-service-test-ejb.jar")
                .addAsResource("META-INF/beans.xml", "META-INF/beans.xml").addPackage(TopologyService.class.getPackage())
                .addPackage(TopologyServiceImpl.class.getPackage()).addPackage(TopologyCreator.class.getPackage())
                .addPackage(Artifact.class.getPackage()).addPackage(clazz.getPackage()).addPackage(TopologyServiceException.class.getPackage());

        return archive;

    }

}
