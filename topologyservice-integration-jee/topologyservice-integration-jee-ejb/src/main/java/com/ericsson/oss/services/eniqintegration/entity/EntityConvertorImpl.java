package com.ericsson.oss.services.eniqintegration.entity;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;

public class EntityConvertorImpl implements EntityConvertor {

    @Inject
    public static Logger logger;

    /**
     * Convert POJO to xml string
     * */
    @Override
    public String convertToXMLString(final MoEntityStore store) {

        String output = "";
        try {

            final JAXBContext jaxbContext = JAXBContext.newInstance(MoEntityStoreImpl.class);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // create empty outputstream
            final OutputStream ops = new ByteArrayOutputStream();
            jaxbMarshaller.marshal(store, ops);
            output = ops.toString();
        } catch (final JAXBException e) {
            logger.error(e.getMessage());
        }
        return output;
    }

}
