package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PutObjectTaggingRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutObjectTaggingRequest request = PutObjectTaggingRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.key()).isNull();
        assertThat(request.versionId()).isNull();
        assertThat(request.tagging()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<Tag> tags = Arrays.asList(
                Tag.newBuilder()
                        .key("a")
                        .value("1")
                        .build(),
                Tag.newBuilder()
                        .key("b")
                        .value("2")
                        .build()
        );

        TagSet tagSet = TagSet.newBuilder()
                .tags(tags)
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        PutObjectTaggingRequest request = PutObjectTaggingRequest.newBuilder()
                .bucket("examplebucket")
                .key("exampleobject")
                .versionId("version-id")
                .tagging(tagging)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.key()).isEqualTo("exampleobject");
        assertThat(request.versionId()).isEqualTo("version-id");
        assertThat(request.tagging()).isEqualTo(tagging);

        assertThat(request.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        List<Tag> tags = Arrays.asList(
                Tag.newBuilder()
                        .key("c")
                        .value("3")
                        .build(),
                Tag.newBuilder()
                        .key("d")
                        .value("4")
                        .build()
        );

        TagSet tagSet = TagSet.newBuilder()
                .tags(tags)
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        PutObjectTaggingRequest original = PutObjectTaggingRequest.newBuilder()
                .bucket("testbucket")
                .key("testobject")
                .versionId("original-version-id")
                .tagging(tagging)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutObjectTaggingRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.key()).isEqualTo("testobject");
        assertThat(copy.versionId()).isEqualTo("original-version-id");
        assertThat(copy.tagging()).isEqualTo(tagging);

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        List<Tag> tags = Arrays.asList(
                Tag.newBuilder()
                        .key("e")
                        .value("5")
                        .build(),
                Tag.newBuilder()
                        .key("f")
                        .value("6")
                        .build()
        );

        TagSet tagSet = TagSet.newBuilder()
                .tags(tags)
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        PutObjectTaggingRequest request = PutObjectTaggingRequest.newBuilder()
                .bucket("anotherbucket")
                .key("anotherobject")
                .versionId("another-version-id")
                .tagging(tagging)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.key()).isEqualTo("anotherobject");
        assertThat(request.versionId()).isEqualTo("another-version-id");
        assertThat(request.tagging()).isEqualTo(tagging);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        List<Tag> tags = Arrays.asList(
                Tag.newBuilder()
                        .key("a")
                        .value("1")
                        .build(),
                Tag.newBuilder()
                        .key("b")
                        .value("2")
                        .build()
        );

        TagSet tagSet = TagSet.newBuilder()
                .tags(tags)
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        String xml = xmlMapper.writeValueAsString(tagging);

        Tagging value = xmlMapper.readValue(xml, Tagging.class);

        assertThat(value.tagSet().tags()).hasSize(2);
        assertThat(value.tagSet().tags().get(0).key()).isEqualTo("a");
        assertThat(value.tagSet().tags().get(0).value()).isEqualTo("1");
        assertThat(value.tagSet().tags().get(1).key()).isEqualTo("b");
        assertThat(value.tagSet().tags().get(1).value()).isEqualTo("2");
    }
}
