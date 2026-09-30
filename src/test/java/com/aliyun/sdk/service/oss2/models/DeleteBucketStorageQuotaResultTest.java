package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketStorageQuota;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBucketStorageQuotaResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteBucketStorageQuotaResult result = DeleteBucketStorageQuotaResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testBuilderWithValues() {
        DeleteBucketStorageQuotaResult result = DeleteBucketStorageQuotaResult.newBuilder()
                .statusCode(204)
                .status("No Content")
                .headers(Collections.singletonMap("x-oss-request-id", "request-id"))
                .build();

        assertThat(result.statusCode()).isEqualTo(204);
        assertThat(result.status()).isEqualTo("No Content");
        assertThat(result.requestId()).isEqualTo("request-id");
    }

    @Test
    public void testXmlBuilder() {
        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(204)
                .status("No Content")
                .headers(Collections.singletonMap("x-oss-request-id", "request-id"))
                .body(BinaryData.fromString(""))
                .build();

        DeleteBucketStorageQuotaResult result = SerdeBucketStorageQuota.toDeleteBucketStorageQuota(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(204);
        assertThat(result.status()).isEqualTo("No Content");
        assertThat(result.requestId()).isEqualTo("request-id");
    }
}
