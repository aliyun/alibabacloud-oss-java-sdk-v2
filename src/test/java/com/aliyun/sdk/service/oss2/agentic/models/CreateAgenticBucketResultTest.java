package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateAgenticBucketResultTest {

    @Test
    public void testEmptyBuilder() {
        CreateAgenticBucketResult result = CreateAgenticBucketResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(0);
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-123456"
        );

        CreateAgenticBucketResult result = CreateAgenticBucketResult.newBuilder()
                .headers(headers)
                .status("OK")
                .statusCode(200)
                .build();

        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-123456");
    }

    @Test
    public void testSerde() {
        OperationOutput output = OperationOutput.newBuilder()
                .headers(MapUtils.of("x-oss-request-id", "req-test"))
                .status("OK")
                .statusCode(200)
                .build();

        CreateAgenticBucketResult result = SerdeAgenticBucketBasic.toCreateAgenticBucket(output);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.requestId()).isEqualTo("req-test");
    }
}
