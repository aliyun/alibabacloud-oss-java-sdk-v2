package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketStorageQuota;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteAgenticBucketStorageQuotaRequestTest {

    @Test
    public void testEmptyBuilder() {
        DeleteAgenticBucketStorageQuotaRequest request = DeleteAgenticBucketStorageQuotaRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.bucket()).isNull();
    }

    @Test
    public void testFullBuilder() {
        DeleteAgenticBucketStorageQuotaRequest request = DeleteAgenticBucketStorageQuotaRequest.newBuilder()
                .bucket("example-agentic-bucket")
                .build();

        assertThat(request.bucket()).isEqualTo("example-agentic-bucket");

        DeleteAgenticBucketStorageQuotaRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("example-agentic-bucket");
    }

    @Test
    public void xmlBuilder() {
        DeleteAgenticBucketStorageQuotaRequest request = DeleteAgenticBucketStorageQuotaRequest.newBuilder()
                .bucket("example-agentic-bucket")
                .build();

        OperationInput input = SerdeAgenticBucketStorageQuota.fromDeleteAgenticBucketStorageQuota(request);

        assertThat(input.bucket().get()).isEqualTo("example-agentic-bucket");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("quota")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("DELETE");
    }
}
