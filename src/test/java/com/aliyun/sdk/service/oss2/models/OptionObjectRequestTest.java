package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class OptionObjectRequestTest {

    @Test
    public void emptyBuilder() {
        OptionObjectRequest request = OptionObjectRequest.newBuilder().build();
        assertEquals(0, request.headers().size());
        assertEquals(0, request.parameters().size());
        assertNull(request.bucket());
    }

    @Test
    public void fullBuilder() {
        OptionObjectRequest request = OptionObjectRequest.newBuilder()
                .bucket("bucket")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("bucket");
        assertEquals(1, request.headers().size());
        Assertions.assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertEquals(1, request.headers().size());
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertNull(request.parameters().get("null-param"));

        //to builder
        OptionObjectRequest.Builder builder = request.toBuilder();
        request = builder
                .header("x-header-value", "value1")
                .parameters(new HashMap<>())
                .build();

        assertThat(request.bucket()).isEqualTo("bucket");
        assertEquals(1, request.headers().size());
        Assertions.assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value1"));
        assertEquals(3, request.parameters().size());
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertNull(request.parameters().get("null-param"));
    }

    @Test
    public void testOrigin() {
        // Test empty builder initialization
        OptionObjectRequest request = OptionObjectRequest.newBuilder().build();
        assertNull(request.origin());

        // Test builder setting Origin
        request = OptionObjectRequest.newBuilder()
                .origin("http://example.com")
                .build();
        assertThat(request.origin()).isEqualTo("http://example.com");

        // Test toBuilder and modification does not affect original object
        OptionObjectRequest.Builder builder = request.toBuilder();
        request = builder
                .origin("http://new-example.com")
                .build();

        assertThat(request.origin()).isEqualTo("http://new-example.com");

    }

    @Test
    public void testAccessControlRequestMethod() {
        // Test empty builder initialization
        OptionObjectRequest request = OptionObjectRequest.newBuilder().build();
        assertNull(request.accessControlRequestMethod());

        // Test builder setting Access-Control-Request-Method
        request = OptionObjectRequest.newBuilder()
                .accessControlRequestMethod("GET")
                .build();
        assertThat(request.accessControlRequestMethod()).isEqualTo("GET");

        // Test toBuilder and modification does not affect original object
        OptionObjectRequest.Builder builder = request.toBuilder();
        request = builder
                .accessControlRequestMethod("POST")
                .build();

        assertThat(request.accessControlRequestMethod()).isEqualTo("POST");
    }

    @Test
    public void testAccessControlRequestHeaders() {
        // Test empty builder initialization
        OptionObjectRequest request = OptionObjectRequest.newBuilder().build();
        assertNull(request.accessControlRequestHeaders());

        // Test builder setting Access-Control-Request-Headers
        request = OptionObjectRequest.newBuilder()
                .accessControlRequestHeaders("x-header1,x-header2")
                .build();
        assertThat(request.accessControlRequestHeaders()).isEqualTo("x-header1,x-header2");

        // Test toBuilder and modification does not affect original object
        OptionObjectRequest.Builder builder = request.toBuilder();
        request = builder
                .accessControlRequestHeaders("x-new-header")
                .build();

        assertThat(request.accessControlRequestHeaders()).isEqualTo("x-new-header");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        CORSConfiguration config = CORSConfiguration.newBuilder()
                .build();

        String xml = xmlMapper.writeValueAsString(config);

        CORSConfiguration value = xmlMapper.readValue(xml, CORSConfiguration.class);

        assertNotNull(value);
    }
}