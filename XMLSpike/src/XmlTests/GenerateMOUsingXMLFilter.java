package XmlTests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class GenerateMOUsingXMLFilter {

    private final static String OUTPUT_FILE = "erbs-test.xml";

    public final static void main(final String[] args) {
        try {
            final File f = new File(OUTPUT_FILE);
            final FileWriter fw = new FileWriter(f);
            final BufferedWriter bw = new BufferedWriter(fw);
            final StreamResult result = new StreamResult(bw);

            final XMLReader generator = new GeneratorUsingXMLFilter();
            final SAXSource source = new SAXSource(generator, new InputSource());

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

class SimpleAttributes extends AttributesImpl {
    private static final String CDATA = "CDATA";

    SimpleAttributes() {
        super();
    }

    SimpleAttributes(final String name, final String value) {
        super();
        addAttribute(name, value);
    }

    public void addAttribute(final String name, final String value) {
        addAttribute("", name, name, CDATA, value);
    }
}

class NameValue {
    String name;
    String value;

    NameValue(final String name, final String value) {
        this.name = name;
        this.value = value;
    }
}

class GeneratorUsingXMLFilter extends XMLFilterImpl {

    private final static Attributes EMPTY_ATTRS = new AttributesImpl();

    private static final String MO_TAG = "mo";
    private static final String ATTR_TAG = "attr";
    private static final String FDN = "fdn";
    private static final String MIM_NAME = "mimName";
    private static final String LAST_MOD = "lastModified";
    private static final String MO_ATTR_NAME = "name";

    @Override
    public void parse(final InputSource is) throws SAXException {
        generate();
    }

    @Override
    public void parse(final String systemId) throws SAXException {
        generate();
    }

    private void buildSampleElementList(final List<NameValue> elements) {
        elements.add(new NameValue("MeContextId", "TEST-ERBSNEW"));
        elements.add(new NameValue("userLabel", "TEST-ERBSNEW"));
        elements.add(new NameValue("neMIMversion", "vC.1.110"));
        elements.add(new NameValue("timestampOfChange", "2011-12-14 09:10:21.481"));
        elements.add(new NameValue("swVersion", "_"));
        elements.add(new NameValue("siteRef", "SubNetwork=ONRM_ROOT_MO_R,Site=Site_LMI"));
        elements.add(new NameValue("nodeVersion", "L12.1"));
    }

    /**
     * THE ACTION STARTS HERE
     */
    private void generate() throws SAXException {

        final List<NameValue> sampleElements = new ArrayList<NameValue>();
        buildSampleElementList(sampleElements);

        startDocument();
        addMo("SubNetwork=ONRM_ROOT_MO_R,MeContext=TEST-ERBSNEW", "ERBS_NODE_MODEL", "1323853821481", sampleElements);
        endDocument();
    }

    private void startMo(final String fdn, final String mimName, final String lastModified) throws SAXException {
        final SimpleAttributes moAttrs = new SimpleAttributes();
        moAttrs.addAttribute(FDN, fdn);
        moAttrs.addAttribute(MIM_NAME, mimName);
        moAttrs.addAttribute(LAST_MOD, lastModified);
        startElement(MO_TAG, moAttrs);
    }

    private void addMo(final String fdn, final String mimName, final String lastModified, final List<NameValue> elements) throws SAXException {
        startMo(fdn, mimName, lastModified);
        addElements(elements);
        endMo();
    }

    private void addElements(final List<NameValue> elements) throws SAXException {
        for (final NameValue attr : elements) {
            addAttrElement(attr);
        }
    }

    private void addAttrElement(final NameValue attr) throws SAXException {
        startElement(ATTR_TAG, new SimpleAttributes(MO_ATTR_NAME, attr.name));
        characters(attr.value.toCharArray(), 0, attr.value.length());
        endElement(ATTR_TAG);
    }

    private void startElement(final String name, final Attributes attr) throws SAXException {
        startElement("", name, name, attr);
    }

    private void endElement(final String name) throws SAXException {
        endElement("", name, name);
    }

    private void endMo() throws SAXException {
        endElement(MO_TAG);
    }
}
