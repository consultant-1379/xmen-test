package com.alethis.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;

public class GenerateUsingXMLReader {

    private final static String OUTPUT_FILE = "GenerateUsingXMLReader.xml";

    public final static void main(final String[] args) {
        try {
            final XMLReader generator = new GeneratorUsingXMLReader();
            final SAXSource source = new SAXSource(generator, new InputSource());

            final File f = new File(OUTPUT_FILE);
            final FileWriter fw = new FileWriter(f);
            final BufferedWriter bw = new BufferedWriter(fw);
            final StreamResult result = new StreamResult(bw);

            final TransformerFactory factory = TransformerFactory.newInstance();
            final Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);

            System.out.println("DONE");
        } catch (final TransformerConfigurationException e) {
            System.out.println("Transformer Configuration Exception: " + e.getMessage());
        } catch (final TransformerException e) {
            System.out.println("Transformer Exception: " + e.getMessage());
        } catch (final IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}

abstract class XMLReaderImpl implements XMLReader {

    protected ContentHandler handler;

    protected abstract void generate() throws SAXException;

    @Override
    public void parse(final InputSource input) throws IOException, SAXException {
        generate();
    }

    @Override
    public void parse(final String systemId) throws IOException, SAXException {
    }

    @Override
    public void setContentHandler(final ContentHandler handler) {
        this.handler = handler;
    }

    @Override
    public ContentHandler getContentHandler() {
        return this.handler;
    }

    @Override
    public void setErrorHandler(final ErrorHandler handler) {
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return null;
    }

    @Override
    public void setDTDHandler(final DTDHandler handler) {
    }

    @Override
    public DTDHandler getDTDHandler() {
        return null;
    }

    @Override
    public void setEntityResolver(final EntityResolver resolver) {
    }

    @Override
    public EntityResolver getEntityResolver() {
        return null;
    }

    @Override
    public void setProperty(final java.lang.String name, final java.lang.Object value) {
    }

    @Override
    public Object getProperty(final java.lang.String name) {
        return null;
    }

    @Override
    public void setFeature(final java.lang.String name, final boolean value) {
    }

    @Override
    public boolean getFeature(final java.lang.String name) {
        return false;
    }
}

class GeneratorUsingXMLReader extends XMLReaderImpl {

    private static Attributes EMPTY_ATTRS = new AttributesImpl();

    @Override
    protected void generate() throws SAXException {
        final AttributesImpl attributesImpl = new AttributesImpl();
        handler.startDocument();
        handler.startElement("", "", "model", EMPTY_ATTRS);

        {
            //start mo
            attributesImpl.addAttribute("", "", "fdn", "CDATA", "SubNetwork=ONRM_ROOT_MO_R,MeContext=TEST-ERBSNEW");
            attributesImpl.addAttribute("", "", "mimName", "CDATA", "ERBS_NODE_MODEL");
            attributesImpl.addAttribute("", "", "lastModified", "CDATA", "1323853821481");
            handler.startElement("", "", "mo", attributesImpl);
            addElement("attr", "name", "MeContextId", "TEST-ERBSNEW");
            addElement("attr", "name", "MeContextId", "TEST-ERBSNEW");
            addElement("attr", "name", "MeContextId", "TEST-ERBSNEW");
            addElement("attr", "name", "MeContextId", "TEST-ERBSNEW");
            addElement("attr", "name", "MeContextId", "TEST-ERBSNEW");
            addElement("attr", "name", "MeContextId", "TEST-ERBSNEW");
            handler.endElement("", "", "mo");
            //end mo
        }

        {
            //start mo
            attributesImpl.addAttribute("", "", "fdn", "CDATA", "SubNetwork=ONRM_ROOT_MO_R,MeContext=TEST-ERBSNEW,ManagedElement=1,ENodeBFunction=1");
            attributesImpl.addAttribute("", "", "mimName", "CDATA", "ERBS_NODE_MODEL");
            attributesImpl.addAttribute("", "", "lastModified", "CDATA", "1322087635197");
            handler.startElement("", "", "mo", attributesImpl);
            addElement("attr", "name", "eNBId", "5");

            {
                handler.startElement("", "", "struct", EMPTY_ATTRS);
                addElement("attr", "name", "mcc", "353");
                addElement("attr", "name", "mcc", "353");
                addElement("attr", "name", "mcc", "353");
                handler.endElement("", "", "struct");
                handler.endElement("", "", "mo");
            }
            //end mo
        }

        handler.endElement("", "", "model");
        handler.endDocument();
    }

    private void addElement(final String element, final String attributeName, final String attributeValue, final String content) throws SAXException {
        final AttributesImpl ageAttrs = new AttributesImpl();
        ageAttrs.addAttribute("", attributeName, attributeName, "CDATA", attributeValue);
        handler.startElement("", element, element, ageAttrs);
        handler.characters(content.toCharArray(), 0, content.length());
        handler.endElement("", element, element);
    }
}
