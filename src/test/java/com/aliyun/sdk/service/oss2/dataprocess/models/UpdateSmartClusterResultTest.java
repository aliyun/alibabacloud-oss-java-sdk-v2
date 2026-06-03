package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateSmartClusterResultTest {

    @Test
    public void testEmptyBuilder() {
        UpdateSmartClusterResult result = UpdateSmartClusterResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Length", "0"
        );

        UpdateSmartClusterResult result = UpdateSmartClusterResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "Content-Length", "0"
        );

        UpdateSmartClusterResult original = UpdateSmartClusterResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        UpdateSmartClusterResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §15 UpdateSmartCluster - HTTP 200, no body
        OperationOutput output = OperationOutput.newBuilder()
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        UpdateSmartClusterResult result = SerdeDatasetBasic.toUpdateSmartCluster(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
    }
}
