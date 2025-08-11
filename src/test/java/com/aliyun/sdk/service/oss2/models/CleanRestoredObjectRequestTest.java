package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class CleanRestoredObjectRequestTest {
    @Test
    public void testEmptyBuilder() {
        CleanRestoredObjectRequest request = CleanRestoredObjectRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
    }

    @Test
    public void testFullBuilder() {
        CleanRestoredObjectRequest request = CleanRestoredObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject.txt");

        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        CleanRestoredObjectRequest original = CleanRestoredObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .parameter("param1", "value1")
                .build();

        CleanRestoredObjectRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.key()).isEqualTo("exampleobject.txt");
        assertThat(copy.parameters()).contains(new AbstractMap.SimpleEntry<>("param1", "value1"));
    }

    @Test
    public void testHeaderProperties() {
        CleanRestoredObjectRequest request = CleanRestoredObjectRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject.txt")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject.txt");
    }
}