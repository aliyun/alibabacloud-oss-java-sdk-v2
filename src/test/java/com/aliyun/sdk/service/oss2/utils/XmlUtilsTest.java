package com.aliyun.sdk.service.oss2.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XmlUtilsTest {

    @Test
    public void testGetXmlRootElement() throws Exception {
        String xmlBody =
                "<Error>\n" +
                        "  <Code>NoSuchBucket</Code>\n" +
                        "  <Message>The specified bucket does not exist.</Message>\n" +
                        "  <RequestId>5C3D9175B6FC201293AD****</RequestId>\n" +
                        "  <HostId>test.oss-cn-hangzhou.aliyuncs.com</HostId>\n" +
                        "  <BucketName>test</BucketName>\n" +
                        "  <EC>0015-00000101</EC>\n" +
                        "</Error>";
        for (int i = 0; i < 2000; i++) {
            JsonNode root = XmlUtils.getXmlRootElement(xmlBody.getBytes());
            assertTrue(root.has("Error"));
        }
    }

    @Test
    public void escapeText() {
        assertThat(XmlUtils.escapeText(null)).isEqualTo("");
        assertThat(XmlUtils.escapeText("")).isEqualTo("");
        assertThat(XmlUtils.escapeText("hello world")).isEqualTo("hello world");
        assertThat(XmlUtils.escapeText("<>&\"'")).isEqualTo("&lt;&gt;&amp;&quot;&apos;");
        assertThat(XmlUtils.escapeText("hello<>&\"'world")).isEqualTo("hello&lt;&gt;&amp;&quot;&apos;world");

        byte[] data = new byte[]
                { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
        String encStr = "&#x0;&#x1;&#x2;&#x3;&#x4;&#x5;&#x6;&#x7;&#x8;&#x9;&#xa;&#xb;&#xc;&#xd;&#xe;&#xf;";
        String oriStr = new String(data, StandardCharsets.UTF_8);
        assertThat(XmlUtils.escapeText(oriStr)).isEqualTo(encStr);

        data = new byte[]
                { 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21,
                        (byte) 0xe4, (byte) 0xbd, (byte) 0xa0, (byte) 0xe5, (byte) 0xa5, (byte) 0xbd};
        encStr = "&#x10;&#x11;&#x12;&#x13;&#x14;&#x15;&#x16;&#x17;&#x18;&#x19;&#x1a;&#x1b;&#x1c;&#x1d;&#x1e;&#x1f; !你好";
        oriStr = new String(data, StandardCharsets.UTF_8);
        assertThat(XmlUtils.escapeText(oriStr)).isEqualTo(encStr);
    }
}
