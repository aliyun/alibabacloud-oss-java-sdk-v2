package com.aliyun.sdk.service.oss2.internal.serde;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parses XML with the JDK StAX API, independent of the Jackson version.
 */
public final class XmlErrorParser {

    private XmlErrorParser() {
    }

    /**
     * Returns the local name of the root element.
     */
    public static String rootName(byte[] data) throws IOException {
        try {
            XMLStreamReader reader = newReader(data);
            try {
                reader.nextTag();
                return reader.getLocalName();
            } finally {
                reader.close();
            }
        } catch (XMLStreamException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * Returns the child elements of the root {@code <Error>} element, or null if the root element is not Error.
     * Nested elements are mapped to "", the same as JsonNode.asText() on an object node.
     */
    public static Map<String, String> parseError(byte[] data) throws IOException {
        try {
            XMLStreamReader reader = newReader(data);
            try {
                if (reader.nextTag() != XMLStreamConstants.START_ELEMENT
                        || !"Error".equals(reader.getLocalName())) {
                    return null;
                }
                Map<String, String> fields = new LinkedHashMap<>();
                while (reader.nextTag() == XMLStreamConstants.START_ELEMENT) {
                    String name = reader.getLocalName();
                    fields.put(name, readText(reader));
                }
                return fields;
            } finally {
                reader.close();
            }
        } catch (XMLStreamException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private static XMLStreamReader newReader(byte[] data) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        return factory.createXMLStreamReader(new ByteArrayInputStream(data));
    }

    // reads the content of the current element and stops at its END_ELEMENT
    private static String readText(XMLStreamReader reader) throws XMLStreamException {
        StringBuilder text = new StringBuilder();
        boolean nested = false;
        int depth = 1;
        while (depth > 0) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                depth++;
                nested = true;
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                depth--;
            } else if (depth == 1 && (event == XMLStreamConstants.CHARACTERS
                    || event == XMLStreamConstants.CDATA
                    || event == XMLStreamConstants.SPACE)) {
                text.append(reader.getText());
            }
        }
        return nested ? "" : text.toString();
    }
}
