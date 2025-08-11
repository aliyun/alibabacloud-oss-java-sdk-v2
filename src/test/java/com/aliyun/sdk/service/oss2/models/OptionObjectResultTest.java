package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class OptionObjectResultTest {

    @Test
    public void testEmptyBuilder() {
        OptionObjectResult result = OptionObjectResult.newBuilder().build();
        Assert.assertEquals(0, result.headers().size());
        assertNotNull(result);
    }

    @Test
    public void testAccessControlAllowMethods() {
        // Test empty builder initialization
        OptionObjectResult result = OptionObjectResult.newBuilder().build();
        assertNull(result.accessControlAllowMethods());

        // Test builder setting Access-Control-Allow-Methods
        result = OptionObjectResult.newBuilder()
                .headers(MapUtils.of("Access-Control-Allow-Methods", "GET,POST"))
                .build();
        assertThat(result.accessControlAllowMethods()).isEqualTo("GET,POST");

        // Test toBuilder and modification does not affect original object
        OptionObjectResult.Builder builder = result.toBuilder();
        result = builder
                .headers(MapUtils.of("Access-Control-Allow-Methods", "PUT"))
                .build();

        assertThat(result.accessControlAllowMethods()).isEqualTo("PUT");
    }

    @Test
    public void testAccessControlAllowHeaders() {
        // Test empty builder initialization
        OptionObjectResult result = OptionObjectResult.newBuilder().build();
        assertNull(result.accessControlAllowHeaders());

        // Test builder setting Access-Control-Allow-Headers
        result = OptionObjectResult.newBuilder()
                .headers(MapUtils.of("Access-Control-Allow-Headers", "x-header1,x-header2"))
                .build();
        assertThat(result.accessControlAllowHeaders()).isEqualTo("x-header1,x-header2");

        // Test toBuilder and modification does not affect original object
        OptionObjectResult.Builder builder = result.toBuilder();
        result = builder
                .headers(MapUtils.of("Access-Control-Allow-Headers", "x-new-header"))
                .build();

        assertThat(result.accessControlAllowHeaders()).isEqualTo("x-new-header");
    }

    @Test
    public void testAccessControlExposeHeaders() {
        // Test empty builder initialization
        OptionObjectResult result = OptionObjectResult.newBuilder().build();
        assertNull(result.accessControlExposeHeaders());

        // Test builder setting Access-Control-Expose-Headers
        result = OptionObjectResult.newBuilder()
                .headers(MapUtils.of("Access-Control-Expose-Headers", "x-expose-header"))
                .build();
        assertThat(result.accessControlExposeHeaders()).isEqualTo("x-expose-header");

        // Test toBuilder and modification does not affect original object
        OptionObjectResult.Builder builder = result.toBuilder();
        result = builder
                .headers(MapUtils.of("Access-Control-Expose-Headers", "x-new-expose-header"))
                .build();

        assertThat(result.accessControlExposeHeaders()).isEqualTo("x-new-expose-header");
    }

    @Test
    public void testAccessControlMaxAge() {
        // Test empty builder initialization
        OptionObjectResult result = OptionObjectResult.newBuilder().build();
        assertNull(result.accessControlMaxAge());

        // Test builder setting Access-Control-Max-Age with valid value
        result = OptionObjectResult.newBuilder()
                .headers(MapUtils.of("Access-Control-Max-Age", "3600"))
                .build();
        assertThat(result.accessControlMaxAge()).isEqualTo(3600L);

        // Test builder setting Access-Control-Max-Age with null value
//        result = OptionObjectResult.newBuilder()
//                .headers(MapUtils.of("Access-Control-Max-Age", ""))
//                .build();
//        assertNull(result.accessControlMaxAge());

        // Test toBuilder and modification does not affect original object
        OptionObjectResult.Builder builder = result.toBuilder();
        result = builder
                .headers(MapUtils.of("Access-Control-Max-Age", "7200"))
                .build();

        assertThat(result.accessControlMaxAge()).isEqualTo(7200L);
    }

    @Test
    public void testAccessControlAllowOrigin() {
        // Test empty builder initialization
        OptionObjectResult result = OptionObjectResult.newBuilder().build();
        assertNull(result.accessControlAllowOrigin());

        // Test builder setting Access-Control-Allow-Origin
        result = OptionObjectResult.newBuilder()
                .headers(MapUtils.of("Access-Control-Allow-Origin", "http://example.com"))
                .build();
        assertThat(result.accessControlAllowOrigin()).isEqualTo("http://example.com");

        // Test toBuilder and modification does not affect original object
        OptionObjectResult.Builder builder = result.toBuilder();
        result = builder
                .headers(MapUtils.of("Access-Control-Allow-Origin", "http://new-example.com"))
                .build();

        assertThat(result.accessControlAllowOrigin()).isEqualTo("http://new-example.com");
    }

    @Test
    public void testXmlParsing() throws Exception {
        String xml = "";

        OperationOutput output = new OperationOutput.Builder()
                .body(SerdeUtils.serializeXmlBody(xml))
                .build();

        ObjectMapper xmlMapper = new XmlMapper();

        String xmlContent = new String(output.body.toBytes(), StandardCharsets.UTF_8);
        Object innerBody = xmlMapper.readValue(xmlContent, CORSConfiguration.class);
        OptionObjectResult result = OptionObjectResult.newBuilder()
                .innerBody(innerBody)
                .build();

        assertNotNull(result);
    }
}
