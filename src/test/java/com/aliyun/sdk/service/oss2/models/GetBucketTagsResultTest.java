package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketTags;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketTagsResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketTagsResult result = GetBucketTagsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<Tag> tags = Arrays.asList(
                Tag.newBuilder()
                        .key("testa")
                        .value("value1-test")
                        .build(),
                Tag.newBuilder()
                        .key("testb")
                        .value("value2-test")
                        .build()
        );

        TagSet tagSet = TagSet.newBuilder()
                .tags(tags)
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        GetBucketTagsResult result = GetBucketTagsResult.newBuilder()
                .headers(headers)
                .innerBody(tagging)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        Tagging resultTagging = result.tagging();
        assertThat(resultTagging).isNotNull();
        assertThat(resultTagging.tagSet()).isNotNull();
        assertThat(resultTagging.tagSet().tags()).hasSize(2);

        Tag firstTag = resultTagging.tagSet().tags().get(0);
        assertThat(firstTag.key()).isEqualTo("testa");
        assertThat(firstTag.value()).isEqualTo("value1-test");

        Tag secondTag = resultTagging.tagSet().tags().get(1);
        assertThat(secondTag.key()).isEqualTo("testb");
        assertThat(secondTag.value()).isEqualTo("value2-test");
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

        GetBucketTagsResult original = GetBucketTagsResult.newBuilder()
                .headers(headers)
                .innerBody(tagging)
                .build();

        GetBucketTagsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        Tagging resultTagging = copy.tagging();
        assertThat(resultTagging).isNotNull();
        assertThat(resultTagging.tagSet()).isNotNull();
        assertThat(resultTagging.tagSet().tags()).hasSize(2);

        Tag firstTag = resultTagging.tagSet().tags().get(0);
        assertThat(firstTag.key()).isEqualTo("samplekey1");
        assertThat(firstTag.value()).isEqualTo("samplevalue1");

        Tag secondTag = resultTagging.tagSet().tags().get(1);
        assertThat(secondTag.key()).isEqualTo("samplekey2");
        assertThat(secondTag.value()).isEqualTo("samplevalue2");
    }

    @Test
    public void testXmlBuilder() {
        String xml =
                "<Tagging>\n" +
                        "  <TagSet>\n" +
                        "    <Tag>\n" +
                        "      <Key>testa</Key>\n" +
                        "      <Value>value1-test</Value>\n" +
                        "    </Tag>\n" +
                        "    <Tag>\n" +
                        "      <Key>testb</Key>\n" +
                        "      <Value>value2-test</Value>\n" +
                        "    </Tag>\n" +
                        "  </TagSet>\n" +
                        "</Tagging>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketTagsResult result = SerdeBucketTags.toGetBucketTags(output);

        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();

        Tagging tagging = result.tagging();
        assertThat(tagging).isNotNull();
        assertThat(tagging.tagSet()).isNotNull();
        assertThat(tagging.tagSet().tags()).hasSize(2);

        Tag firstTag = tagging.tagSet().tags().get(0);
        assertThat(firstTag.key()).isEqualTo("testa");
        assertThat(firstTag.value()).isEqualTo("value1-test");

        Tag secondTag = tagging.tagSet().tags().get(1);
        assertThat(secondTag.key()).isEqualTo("testb");
        assertThat(secondTag.value()).isEqualTo("value2-test");
    }
}