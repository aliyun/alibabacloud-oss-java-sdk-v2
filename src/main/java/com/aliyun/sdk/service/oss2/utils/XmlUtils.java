package com.aliyun.sdk.service.oss2.utils;

import com.aliyun.sdk.service.oss2.internal.serde.XmlErrorParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class XmlUtils {

    public static JsonNode getXmlRootElement(byte[] data) throws IOException {
        // read the root name with StAX, FromXmlParser.getStaxReader() is not reliable across versions
        String rootName = XmlErrorParser.rootName(data);
        JsonNode content = new XmlMapper().readTree(data);
        return JsonNodeFactory.instance.objectNode().set(rootName, content);
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
