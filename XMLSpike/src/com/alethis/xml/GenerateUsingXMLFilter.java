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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLFilterImpl;

public class GenerateUsingXMLFilter {

    private final static String OUTPUT_FILE = "GenerateUsingXMLFilter.xml";

    public final static void main(final String[] args) {
        try {
            final XMLReader generator = new GeneratorUsingXMLFilter();
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
        }
        catch (final TransformerConfigurationException e) {
            System.out.println("Transformer Configuration Exception: " + e.getMessage());
        }
        catch (final TransformerException e) {
            System.out.println("Transformer Exception: " + e.getMessage());
        }
        catch (final IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}

class GeneratorUsingXMLFilter extends XMLFilterImpl {

    private final static Attributes EMPTY_ATTRS = new AttributesImpl();

    private final static String FAMILY = "family";
    private final static String FATHER = "father";
    private final static String MOTHER = "mother";
    private final static String CHILDREN = "children";
    private final static String CHILD = "child";

    private final static String AGE = "age";

    @Override
    public void parse(final InputSource is) throws SAXException {
        generate();
    }

    @Override
    public void parse(final String systemId) throws SAXException {
        generate();
    }

    private void generate() throws SAXException {
        startDocument();

        startElement("", FAMILY, FAMILY, EMPTY_ATTRS);

        addElement(FATHER, "Michael", "49");
        addElement(MOTHER, "Margaret", "46");

        startElement("", CHILDREN, CHILDREN, EMPTY_ATTRS);
        addElement(CHILD, "Chloe", "12");
        addElement(CHILD, "Adrian", "10");
        endElement("", CHILDREN, CHILDREN);

        endElement("", FAMILY, FAMILY);

        endDocument();
    }

    private void addElement(final String element, final String name, final String age) throws SAXException {
        final AttributesImpl ageAttrs = new AttributesImpl();
        ageAttrs.addAttribute("", AGE, AGE, "CDATA", age);
        startElement("", element, element, ageAttrs);
        characters(name.toCharArray(), 0, name.length());
        endElement("", element, element);
    }
}
