package com.alethis.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class GenerateUsingTransformerHandler {

    private final static String OUTPUT_FILE = "GenerateUsingTransformerHandler.xml";

    public final static void main(final String[] args) {
        try {

            final File f = new File(OUTPUT_FILE);
            final FileWriter fw = new FileWriter(f);
            final BufferedWriter bw = new BufferedWriter(fw);
            final StreamResult result = new StreamResult(bw);

            final SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            final TransformerHandler handler = tf.newTransformerHandler();
            final Transformer transformer = handler.getTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            handler.setResult(result);

            new GeneratorUsingTransformerHandler(handler).generate();

            System.out.println("DONE");
        }
        catch (final TransformerConfigurationException e) {
            System.out.println("Transformer Configuration Exception: " + e.getMessage());
        }
        catch (final TransformerException e) {
            System.out.println("Transformer Exception: " + e.getMessage());
        }
        catch (final SAXException e) {
            System.out.println("SAX Exception: " + e.getMessage());
        }
        catch (final IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}

class GeneratorUsingTransformerHandler {

    private final static Attributes EMPTY_ATTRS = new AttributesImpl();

    private final static String FAMILY = "family";
    private final static String FATHER = "father";
    private final static String MOTHER = "mother";
    private final static String CHILDREN = "children";
    private final static String CHILD = "child";

    private final static String AGE = "age";

    private final TransformerHandler handler;

    public GeneratorUsingTransformerHandler(final TransformerHandler handler) {
        this.handler = handler;
    }

    public void generate() throws SAXException {
        handler.startDocument();

        handler.startElement("", FAMILY, FAMILY, EMPTY_ATTRS);

        addElement(FATHER, "Michael J.", "49");
        addElement(MOTHER, "Margaret A.", "46");

        handler.startElement("", CHILDREN, CHILDREN, EMPTY_ATTRS);
        addElement(CHILD, "Chloe C.", "12");
        addElement(CHILD, "Adrian K.", "10");
        handler.endElement("", CHILDREN, CHILDREN);

        handler.endElement("", FAMILY, FAMILY);

        handler.endDocument();
    }

    private void addElement(final String element, final String name, final String age) throws SAXException {
        final AttributesImpl ageAttrs = new AttributesImpl();
        ageAttrs.addAttribute("", AGE, AGE, "CDATA", age);
        handler.startElement("", element, element, ageAttrs);
        handler.characters(name.toCharArray(), 0, name.length());
        handler.endElement("", element, element);
    }
}
