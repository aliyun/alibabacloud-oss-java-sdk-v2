package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectTagging;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutObjectTaggingResultTest {

    @Test
    public void testEmptyBuilder() {
        PutObjectTaggingResult result = PutObjectTaggingResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        PutObjectTaggingResult result = PutObjectTaggingResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.versionId()).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        PutObjectTaggingResult original = PutObjectTaggingResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        PutObjectTaggingResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(copy.status()).isEqualTo("OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.versionId()).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectTagging.toPutObjectTagging(blankOutput);


        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-111111111111111111",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .body(null)
                .build();

        PutObjectTaggingResult result = SerdeObjectTagging.toPutObjectTagging(output);

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.versionId()).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
    }

}
