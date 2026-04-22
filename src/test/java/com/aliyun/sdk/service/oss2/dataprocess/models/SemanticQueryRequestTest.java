package com.aliyun.sdk.service.oss2.dataprocess.models;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class SemanticQueryRequestTest {

    @Test
    public void testEmptyBuilder() {
        SemanticQueryRequest request = SemanticQueryRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.datasetName()).isNull();
        assertThat(request.nextToken()).isNull();
        assertThat(request.maxResults()).isNull();
        assertThat(request.query()).isNull();
        assertThat(request.withFields()).isNull();
        assertThat(request.mediaTypes()).isNull();
        assertThat(request.sourceUri()).isNull();
    }

    @Test
    public void testFullBuilder() {
        SemanticQueryRequest request = SemanticQueryRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("test-dataset")
                .nextToken("next-token-value")
                .maxResults(10)
                .query("blue shirt man walking to table")
                .withFields(Arrays.asList("Filename", "Size", "MediaType"))
                .mediaTypes(Arrays.asList("video", "image"))
                .sourceUri("oss://bucket/prefix/")
                .header("x-custom-header", "value1")
                .parameter("extra-param", "extra-value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("test-dataset");
        assertThat(request.nextToken()).isEqualTo("next-token-value");
        assertThat(request.maxResults()).isEqualTo(10);
        assertThat(request.query()).isEqualTo("blue shirt man walking to table");
        assertThat(request.withFields()).isEqualTo("[\"Filename\",\"Size\",\"MediaType\"]");
        assertThat(request.mediaTypes()).isEqualTo("[\"video\",\"image\"]");
        assertThat(request.sourceUri()).isEqualTo("oss://bucket/prefix/");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("extra-param", "extra-value"));

        // Test toBuilder
        SemanticQueryRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.nextToken()).isEqualTo("next-token-value");
        assertThat(copy.maxResults()).isEqualTo(10);
        assertThat(copy.query()).isEqualTo("blue shirt man walking to table");
    }

    @Test
    public void testToBuilderPreserveState() {
        SemanticQueryRequest original = SemanticQueryRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .query("test query")
                .build();

        SemanticQueryRequest copy = original.toBuilder()
                .maxResults(20)
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.query()).isEqualTo("test query");
        assertThat(copy.maxResults()).isEqualTo(20);
    }

    @Test
    public void testWithStringParameters() {
        SemanticQueryRequest request = SemanticQueryRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .nextToken("test-token")
                .maxResults(50)
                .query("俯瞰白雪覆盖的森林")
                .withFields(Arrays.asList("Filename", "Size"))
                .mediaTypes("video")
                .sourceUri("oss://bucket/video/")
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.datasetName()).isEqualTo("test-dataset");
        assertThat(request.nextToken()).isEqualTo("test-token");
        assertThat(request.maxResults()).isEqualTo(50);
        assertThat(request.query()).isEqualTo("俯瞰白雪覆盖的森林");
        assertThat(request.withFields()).isEqualTo("[\"Filename\",\"Size\"]");
        assertThat(request.mediaTypes()).isEqualTo("video");
        assertThat(request.sourceUri()).isEqualTo("oss://bucket/video/");
    }
}
