package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectTagging;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetObjectTaggingResultTest {

    @Test
    public void testEmptyBuilder() {
        GetObjectTaggingResult result = GetObjectTaggingResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        Tag tag1 = Tag.newBuilder()
                .key("a")
                .value("1")
                .build();

        Tag tag2 = Tag.newBuilder()
                .key("b")
                .value("2")
                .build();

        TagSet tagSet = TagSet.newBuilder()
                .tags(java.util.Arrays.asList(tag1, tag2))
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        GetObjectTaggingResult result = GetObjectTaggingResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .innerBody(tagging)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
        assertThat(result.tagging()).isNotNull();
        assertThat(result.tagging().tagSet()).isNotNull();
        assertThat(result.tagging().tagSet().tags()).hasSize(2);

        Tag resultTag1 = result.tagging().tagSet().tags().get(0);
        assertThat(resultTag1.key()).isEqualTo("a");
        assertThat(resultTag1.value()).isEqualTo("1");

        Tag resultTag2 = result.tagging().tagSet().tags().get(1);
        assertThat(resultTag2.key()).isEqualTo("b");
        assertThat(resultTag2.value()).isEqualTo("2");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        Tag tag1 = Tag.newBuilder()
                .key("a")
                .value("1")
                .build();

        Tag tag2 = Tag.newBuilder()
                .key("b")
                .value("2")
                .build();

        TagSet tagSet = TagSet.newBuilder()
                .tags(java.util.Arrays.asList(tag1, tag2))
                .build();

        Tagging tagging = Tagging.newBuilder()
                .tagSet(tagSet)
                .build();

        GetObjectTaggingResult original = GetObjectTaggingResult.newBuilder()
                .headers(headers)
                .status("Found")
                .statusCode(302)
                .innerBody(tagging)
                .build();

        GetObjectTaggingResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(copy.status()).isEqualTo("Found");
        assertThat(copy.statusCode()).isEqualTo(302);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
        assertThat(copy.tagging()).isNotNull();
        assertThat(copy.tagging().tagSet()).isNotNull();
        assertThat(copy.tagging().tagSet().tags()).hasSize(2);

        Tag resultTag1 = copy.tagging().tagSet().tags().get(0);
        assertThat(resultTag1.key()).isEqualTo("a");
        assertThat(resultTag1.value()).isEqualTo("1");

        Tag resultTag2 = copy.tagging().tagSet().tags().get(1);
        assertThat(resultTag2.key()).isEqualTo("b");
        assertThat(resultTag2.value()).isEqualTo("2");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectTagging.toGetObjectTagging(blankOutput);


        String xml = "<Tagging>\n" +
                "  <TagSet>\n" +
                "    <Tag>\n" +
                "      <Key>a</Key>\n" +
                "      <Value>1</Value>\n" +
                "    </Tag>\n" +
                "    <Tag>\n" +
                "      <Key>b</Key>\n" +
                "      <Value>2</Value>\n" +
                "    </Tag>\n" +
                "  </TagSet>\n" +
                "</Tagging>";

        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-111111111111111111",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .body(BinaryData.fromString(xml))
                .build();

        GetObjectTaggingResult result = SerdeObjectTagging.toGetObjectTagging(output);

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
        assertThat(result.tagging()).isNotNull();
        assertThat(result.tagging().tagSet()).isNotNull();
        assertThat(result.tagging().tagSet().tags()).hasSize(2);

        Tag resultTag1 = result.tagging().tagSet().tags().get(0);
        assertThat(resultTag1.key()).isEqualTo("a");
        assertThat(resultTag1.value()).isEqualTo("1");

        Tag resultTag2 = result.tagging().tagSet().tags().get(1);
        assertThat(resultTag2.key()).isEqualTo("b");
        assertThat(resultTag2.value()).isEqualTo("2");
    }
}
