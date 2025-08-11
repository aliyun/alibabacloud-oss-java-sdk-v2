package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectTagging;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteObjectTaggingResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteObjectTaggingResult result = DeleteObjectTaggingResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-1234567890abcdefg",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        DeleteObjectTaggingResult result = DeleteObjectTaggingResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(204)
                .build();

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(204);
        assertThat(result.versionId()).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-765432109876543210",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        DeleteObjectTaggingResult original = DeleteObjectTaggingResult.newBuilder()
                .headers(headers)
                .status("No Content")
                .statusCode(204)
                .build();

        DeleteObjectTaggingResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(copy.status()).isEqualTo("No Content");
        assertThat(copy.statusCode()).isEqualTo(204);
        assertThat(copy.versionId()).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void testXmlBuilder() {
        String blankXml = "";

        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectTagging.toDeleteObjectTagging(blankOutput);


        Map<String, String> headers = MapUtils.of(
                "x-request-id", "req-111111111111111111",
                "x-oss-version-id", "CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .headers(headers)
                .status("No Content")
                .statusCode(204)
                .body(null)
                .build();

        DeleteObjectTaggingResult result = SerdeObjectTagging.toDeleteObjectTagging(output);

        assertThat(result.headers().get("x-request-id")).isEqualTo("req-111111111111111111");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.status()).isEqualTo("No Content");
        assertThat(result.statusCode()).isEqualTo(204);
        assertThat(result.versionId()).isEqualTo("CAEQExiBgID98azQwxkiIGQzYmRkZGUxNTgwNzRiMjFiMWQ1NjMyOTRkZGZjMzVl");
        assertThat(result.requestId()).isEqualTo("req-111111111111111111");
    }
}
