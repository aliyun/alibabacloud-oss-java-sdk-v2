package com.aliyun.sdk.service.oss2.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;

import java.io.IOException;

public class XmlUtils {

    public static JsonNode getXmlRootElement(byte[] data) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new SimpleModule().addDeserializer(JsonNode.class,
                new JsonNodeDeserializer() {
                    @Override
                    public JsonNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                        String rootName = ((FromXmlParser) p).getStaxReader().getLocalName();
                        return ctxt.getNodeFactory()
                                .objectNode().set(rootName, super.deserialize(p, ctxt));
                    }
                }));
        return xmlMapper.readTree(data);
    }

    public static String escapeText(String key) {
        if (key == null) {
            return "";
        }

        int pos;
        int len = key.length();
        StringBuilder builder = new StringBuilder();
        for (pos = 0; pos < len; pos++) {
            char ch = key.charAt(pos);
            String escapedStr;
            switch (ch) {
                /*
                case '\t':
                    escapedStr = "&#x09;";
                    break;
                case '\n':
                    escapedStr = "&#x00A;";
                    break;
                case '\r':
                    escapedStr = "&#x0D;";
                    break;
                 */
                case '&':
                    escapedStr = "&amp;";
                    break;
                case '\'':
                    escapedStr = "&apos;";
                    break;
                case '"':
                    escapedStr = "&quot;";
                    break;
                case '<':
                    escapedStr = "&lt;";
                    break;
                case '>':
                    escapedStr = "&gt;";
                    break;
                default:
                    if (ch < 0x20) {
                        escapedStr = "&#x" + Integer.toHexString(ch) + ";";
                    } else {
                        escapedStr = null;
                    }
                    break;
            }

            if (escapedStr != null) {
                builder.append(escapedStr);
            } else {
                builder.append(ch);
            }
        }

        return builder.toString();
    }

}
