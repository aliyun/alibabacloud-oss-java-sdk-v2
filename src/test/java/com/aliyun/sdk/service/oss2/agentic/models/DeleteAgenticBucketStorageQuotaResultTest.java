package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketStorageQuota;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteAgenticBucketStorageQuotaResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteAgenticBucketStorageQuotaResult result = DeleteAgenticBucketStorageQuotaResult.newBuilder().build();
        assertThat(result).isNotNull();
    }

    @Test
    public void testBuilderWithValues() {
        DeleteAgenticBucketStorageQuotaResult result = DeleteAgenticBucketStorageQuotaResult.newBuilder()
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

        DeleteAgenticBucketStorageQuotaResult result = SerdeAgenticBucketStorageQuota.toDeleteAgenticBucketStorageQuota(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(204);
        assertThat(result.status()).isEqualTo("No Content");
        assertThat(result.requestId()).isEqualTo("request-id");
    }
}
