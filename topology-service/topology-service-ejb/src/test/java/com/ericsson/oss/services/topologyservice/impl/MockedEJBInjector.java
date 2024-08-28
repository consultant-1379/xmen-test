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
package com.ericsson.oss.services.topologyservice.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.ericsson.oss.itpf.sdk.core.annotation.EServiceRef;

public class MockedEJBInjector {
    private static final List<Class<? extends Annotation>> EJB_ANNOTATIONS;
    static {
        EJB_ANNOTATIONS = new ArrayList<Class<? extends Annotation>>();
        EJB_ANNOTATIONS.add(EJB.class);
        EJB_ANNOTATIONS.add(Resource.class);
        EJB_ANNOTATIONS.add(Inject.class);
        EJB_ANNOTATIONS.add(EServiceRef.class);
    }

    final Map<Class<?>, Object> mappings = new HashMap<Class<?>, Object>();

    public void inject(final Object bean) throws IllegalAccessException {
        for (final Field field : getEJBAnnotatedFields(bean)) {
            injectField(field, bean);
        }
    }

    public void assign(final Class<?> type, final Object instance) {
        mappings.put(type, instance);
    }

    private void injectField(final Field field, final Object bean) throws IllegalAccessException {
        final Object instanceToInject = mappings.get(field.getType());
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        field.set(bean, instanceToInject);
    }

    private List<Field> getEJBAnnotatedFields(final Object bean) {
        final Class<? extends Object> beanClass = bean.getClass();
        final List<Field> annotatedFields = new ArrayList<Field>();
        for (final Field field : beanClass.getDeclaredFields()) {
            if (hasEJBAnnotation(field)) {
                annotatedFields.add(field);
            }
        }
        return annotatedFields;
    }

    private static boolean hasEJBAnnotation(final Field field) {
        for (final Class<? extends Annotation> annotation : EJB_ANNOTATIONS) {
            if (field.isAnnotationPresent(annotation)) {
                return true;
            }
        }
        return false;
    }
}
