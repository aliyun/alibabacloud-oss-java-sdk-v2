package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketStorageQuota;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBucketStorageQuotaRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteBucketStorageQuotaRequest request = DeleteBucketStorageQuotaRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteBucketStorageQuotaRequest request = DeleteBucketStorageQuotaRequest.newBuilder()
                .bucket("examplebucket")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("str-param", "value")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );

        DeleteBucketStorageQuotaRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
    }

    @Test
    public void xmlBuilder() {
        DeleteBucketStorageQuotaRequest request = DeleteBucketStorageQuotaRequest.newBuilder()
                .bucket("examplebucket")
                .build();

        OperationInput input = SerdeBucketStorageQuota.fromDeleteBucketStorageQuota(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("quota")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("DELETE");
    }
}
