package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketTransferAcceleration;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketTransferAccelerationResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketTransferAccelerationResult result = GetBucketTransferAccelerationResult.newBuilder().build();
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

        TransferAccelerationConfiguration transferAccelerationConfiguration = TransferAccelerationConfiguration.newBuilder()
                .enabled(true)
                .build();

        GetBucketTransferAccelerationResult result = GetBucketTransferAccelerationResult.newBuilder()
                .headers(headers)
                .innerBody(transferAccelerationConfiguration)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.transferAccelerationConfiguration()).isEqualTo(transferAccelerationConfiguration);
        assertThat(result.transferAccelerationConfiguration().enabled()).isEqualTo(true);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        TransferAccelerationConfiguration transferAccelerationConfiguration = TransferAccelerationConfiguration.newBuilder()
                .enabled(false)
                .build();

        GetBucketTransferAccelerationResult original = GetBucketTransferAccelerationResult.newBuilder()
                .headers(headers)
                .innerBody(transferAccelerationConfiguration)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetBucketTransferAccelerationResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.transferAccelerationConfiguration()).isEqualTo(transferAccelerationConfiguration);
        assertThat(copy.transferAccelerationConfiguration().enabled()).isEqualTo(false);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<TransferAccelerationConfiguration>\n" +
                " <Enabled>true</Enabled>\n" +
                "</TransferAccelerationConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketTransferAccelerationResult result = SerdeBucketTransferAcceleration.toGetBucketTransferAcceleration(output);

        assertThat(result.transferAccelerationConfiguration()).isNotNull();
        assertThat(result.transferAccelerationConfiguration().enabled()).isEqualTo(true);
    }
}