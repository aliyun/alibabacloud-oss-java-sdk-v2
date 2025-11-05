package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketTags;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketTagsRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutBucketTagsRequest request = PutBucketTagsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.tagging()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<Tag> tags = Arrays.asList(
                Tag.newBuilder()
                        .key("key1")
                        .value("value1")
                        .build(),
                Tag.newBuilder()
                        .key("key2")
                        .value("value2")
                        .build()
        );

        TagSet tagSet = TagSet.newBuilder()
                .tags(tags)
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        PutBucketTagsRequest request = PutBucketTagsRequest.newBuilder()
                .bucket("examplebucket")
                .tagging(tagging)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.tagging()).isEqualTo(tagging);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        List<Tag> tags = Arrays.asList(
                Tag.newBuilder()
                        .key("samplekey1")
                        .value("samplevalue1")
                        .build(),
                Tag.newBuilder()
                        .key("samplekey2")
                        .value("samplevalue2")
                        .build()
        );

        TagSet tagSet = TagSet.newBuilder()
                .tags(tags)
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        PutBucketTagsRequest original = PutBucketTagsRequest.newBuilder()
                .bucket("testbucket")
                .tagging(tagging)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutBucketTagsRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.tagging()).isEqualTo(tagging);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
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
                        .key("object1")
                        .value("value1")
                        .build(),
                Tag.newBuilder()
                        .key("object2")
                        .value("value2")
                        .build()
        );

        TagSet tagSet = TagSet.newBuilder()
                .tags(tags)
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        PutBucketTagsRequest request = PutBucketTagsRequest.newBuilder()
                .bucket("anotherbucket")
                .tagging(tagging)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.tagging()).isEqualTo(tagging);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        List<Tag> tags = Arrays.asList(
                Tag.newBuilder()
                        .key("key1")
                        .value("value1")
                        .build(),
                Tag.newBuilder()
                        .key("key2")
                        .value("value2")
                        .build()
        );

        TagSet tagSet = TagSet.newBuilder()
                .tags(tags)
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        PutBucketTagsRequest request = PutBucketTagsRequest.newBuilder()
                .bucket("examplebucket")
                .tagging(tagging)
                .build();

        OperationInput input = SerdeBucketTags.fromPutBucketTags(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("tagging")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<Tagging>");
        assertThat(xmlContent).contains("<TagSet>");
        assertThat(xmlContent).contains("<Tag>");
        assertThat(xmlContent).contains("<Key>key1</Key>");
        assertThat(xmlContent).contains("<Value>value1</Value>");
        assertThat(xmlContent).contains("<Key>key2</Key>");
        assertThat(xmlContent).contains("<Value>value2</Value>");
        assertThat(xmlContent).contains("</Tag>");
        assertThat(xmlContent).contains("</TagSet>");
        assertThat(xmlContent).contains("</Tagging>");
    }
}