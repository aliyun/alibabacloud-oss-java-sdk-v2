package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketStorageQuota;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAgenticBucketStorageQuotaRequestTest {

    @Test
    public void testEmptyBuilder() {
        GetAgenticBucketStorageQuotaRequest request = GetAgenticBucketStorageQuotaRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        GetAgenticBucketStorageQuotaRequest request = GetAgenticBucketStorageQuotaRequest.newBuilder()
                .bucket("example-agentic-bucket")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("example-agentic-bucket");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );

        GetAgenticBucketStorageQuotaRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("example-agentic-bucket");
    }

    @Test
    public void xmlBuilder() {
        GetAgenticBucketStorageQuotaRequest request = GetAgenticBucketStorageQuotaRequest.newBuilder()
                .bucket("example-agentic-bucket")
                .build();

        OperationInput input = SerdeAgenticBucketStorageQuota.fromGetAgenticBucketStorageQuota(request);

        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.bucket().get()).isEqualTo("example-agentic-bucket");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("quota")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
    }
}
