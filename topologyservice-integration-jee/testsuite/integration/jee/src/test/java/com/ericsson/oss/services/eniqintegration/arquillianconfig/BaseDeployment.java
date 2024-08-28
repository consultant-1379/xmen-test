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

package com.ericsson.oss.services.eniqintegration.arquillianconfig;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import com.ericsson.oss.services.eniqintegration.entity.*;
import com.ericsson.oss.services.eniqintegration.jcafilemanager.JcaDirectoryOperator;
import com.ericsson.oss.services.eniqintegration.jcafilemanager.JcaFileManager;
import com.ericsson.oss.services.topologyservice.api.AttributeItem;
import com.ericsson.oss.services.topologyservice.api.MoEntity;

public class BaseDeployment {

    // Add Maven package
    public static EnterpriseArchive createDeployment(final Class<?> clazz) {
        final MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        final EnterpriseArchive archive = ShrinkWrap.create(EnterpriseArchive.class, clazz.getSimpleName() + ".ear")
                .addAsModule(createTestModule(clazz))
                .addAsLibraries(resolver.artifact("com.ericsson.oss.itpf.sdk:sdk-resources-api:jar:2.1.0").resolveAsFiles())
                .addAsLibraries(resolver.artifact("com.ericsson.oss.itpf.sdk:sdk-resources:jar:2.0.9").resolveAsFiles())
                .addAsLibraries(resolver.artifact("com.ericsson.oss.itpf.sdk:sdk-recording-api:jar:2.1.0").resolveAsFiles())
                .addAsLibraries(resolver.artifact("com.ericsson.oss.itpf.sdk:sdk-recording:jar:2.0.9").resolveAsFiles())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

        return archive;

    }

    // add java class
    public static Archive<?> createTestModule(final Class<?> clazz) {
        final JavaArchive archive = ShrinkWrap.create(JavaArchive.class, clazz.getSimpleName() + "-test.jar")
                .addAsResource("META-INF/beans.xml", "META-INF/beans.xml").addClass(JcaDirectoryOperator.class).addClass(EntityConvertorImpl.class)
                .addClass(MoEntityStoreImpl.class).addClass(MoEntity.class).addClass(AttributeItem.class).addClass(MoEntityStore.class)
                .addClass(EntityConvertor.class).addClass(JcaFileManager.class).addPackage(clazz.getPackage());
        return archive;
    }
}
