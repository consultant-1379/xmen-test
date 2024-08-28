/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.oss.services.eniqintegration.arquillianconfig;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

public class Artifact {

    public static final String MODEL_ROOT = "target/deployed-model";
    public static final String MODEL_REPO_XML_PATH = "target/deployed-model/modelRepo.xml";

    //sfw dist
    public static final String ITPF_SERVICE_FRAMEWORK_DIST = "com.ericsson.oss.itpf.sdk:service-framework-dist:jar";
    private static final String COM_ERICSSON_OSS_ITPF_SDK___SERVICE_FRAMEWORK_RAR = "com.ericsson.oss.itpf.sdk:service-framework-rar:rar:2.0.9";

    //manifest
    public static final File MANIFEST_MF_FILE = new File("src/test/resources/META-INF/MANIFEST.MF");

    //deployment structure
    public static final File DEPLOYMENT_STRUCTURE = new File("src/test/resources/META-INF/jboss-deployment-structure.xml");

    public static ResourceAdapterArchive getServiceFrameworkRar() {
        final MavenDependencyResolver resolver = Artifact.getMavenResolver();
        final File[] artifacts = resolver.artifact(COM_ERICSSON_OSS_ITPF_SDK___SERVICE_FRAMEWORK_RAR).exclusion("*").resolveAsFiles();
        if (artifacts.length < 1) {
            throw new IllegalStateException("Can not reslove service-framework-rar dependency from pom.xml");
        }
        final ResourceAdapterArchive rar = ShrinkWrap.createFromZipFile(ResourceAdapterArchive.class, artifacts[0]);
        return rar;
    }

    public static MavenDependencyResolver getMavenResolver() {
        return DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
    }
}
