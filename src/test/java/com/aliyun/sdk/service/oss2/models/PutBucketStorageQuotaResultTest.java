package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketStorageQuota;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketStorageQuotaResultTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketStorageQuotaResult result = PutBucketStorageQuotaResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testBuilderWithValues() {
        PutBucketStorageQuotaResult result = PutBucketStorageQuotaResult.newBuilder()
                .statusCode(200)
                .status("OK")
                .headers(Collections.singletonMap("x-oss-request-id", "request-id"))
                .build();

        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.requestId()).isEqualTo("request-id");
    }

    @Test
    public void testXmlBuilder() {
        OperationOutput output = OperationOutput.newBuilder()
                .statusCode(200)
                .status("OK")
                .headers(Collections.singletonMap("x-oss-request-id", "request-id"))
                .body(BinaryData.fromString(""))
                .build();

        PutBucketStorageQuotaResult result = SerdeBucketStorageQuota.toPutBucketStorageQuota(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.requestId()).isEqualTo("request-id");
    }
}
