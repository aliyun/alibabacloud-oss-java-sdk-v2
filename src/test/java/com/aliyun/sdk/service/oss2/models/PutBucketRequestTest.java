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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PutBucketRequestTest {

    @Test
    public void emptyBuilder() {
        PutBucketRequest request = PutBucketRequest.newBuilder().build();
        assertEquals(0, request.headers().size());
        assertEquals(0, request.parameters().size());
        assertNull(request.bucket());
    }

    @Test
    public void fullBuilder() {
        PutBucketRequest request = PutBucketRequest.newBuilder()
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
        PutBucketRequest.Builder builder = request.toBuilder();
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
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        CreateBucketConfiguration config = CreateBucketConfiguration.newBuilder()
                .storageClass("Standard")
                .dataRedundancyType("LRS")
                .build();

        String xml = xmlMapper.writeValueAsString(config);

        CreateBucketConfiguration value = xmlMapper.readValue(xml, CreateBucketConfiguration.class);
        assertEquals(config.storageClass(), value.storageClass());
        assertEquals(config.dataRedundancyType(), value.dataRedundancyType());
    }
}
