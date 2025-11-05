package com.aliyun.sdk.service.oss2.vectors.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.transform.SerdeVectorIndexBasic;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteVectorIndexResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteVectorIndexResult result = DeleteVectorIndexResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "x-oss-hash-crc64ecma", "316181249502703****",
                "x-oss-version-id", "CAEQNhiBgMDJgZCA0BYiIDc4MGZjZGI2OTBjOTRmNTE5NmU5NmFhZjhjYmY0****"
        );

        DeleteVectorIndexResult result = DeleteVectorIndexResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("x-oss-hash-crc64ecma")).isEqualTo("316181249502703****");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("CAEQNhiBgMDJgZCA0BYiIDc4MGZjZGI2OTBjOTRmNTE5NmU5NmFhZjhjYmY0****");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-1234567890abcdefg");
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "x-oss-hash-crc64ecma", "316181249502703****",
                "x-oss-version-id", "CAEQNhiBgMDJgZCA0BYiIDc4MGZjZGI2OTBjOTRmNTE5NmU5NmFhZjhjYmY0****"
        );

        DeleteVectorIndexResult original = DeleteVectorIndexResult.newBuilder()
                .headers(headers)
                .status("Created")
                .statusCode(201)
                .build();

        DeleteVectorIndexResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("x-oss-hash-crc64ecma")).isEqualTo("316181249502703****");
        assertThat(copy.headers().get("x-oss-version-id")).isEqualTo("CAEQNhiBgMDJgZCA0BYiIDc4MGZjZGI2OTBjOTRmNTE5NmU5NmFhZjhjYmY0****");
        assertThat(copy.status()).isEqualTo("Created");
        assertThat(copy.statusCode()).isEqualTo(201);
        assertThat(copy.requestId()).isEqualTo("req-765432109876543210");
    }

    @Test
    public void xmlBuilder() {
        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(""))
                .headers(MapUtils.of(
                        "x-oss-request-id", "req-xml-builder-test",
                        "x-oss-hash-crc64ecma", "316181249502703****",
                        "x-oss-version-id", "CAEQNhiBgMDJgZCA0BYiIDc4MGZjZGI2OTBjOTRmNTE5NmU5NmFhZjhjYmY0****"
                ))
                .status("OK")
                .statusCode(200)
                .build();

        DeleteVectorIndexResult result = SerdeVectorIndexBasic.toDeleteVectorIndex(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-xml-builder-test");
        assertThat(result.headers().get("x-oss-hash-crc64ecma")).isEqualTo("316181249502703****");
        assertThat(result.headers().get("x-oss-version-id")).isEqualTo("CAEQNhiBgMDJgZCA0BYiIDc4MGZjZGI2OTBjOTRmNTE5NmU5NmFhZjhjYmY0****");
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-xml-builder-test");
    }
}
