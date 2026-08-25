package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PutAgenticBucketStatusRequestTest {

    @Test
    public void testSerde() {
        AgenticBucketStatus statusBody = AgenticBucketStatus.newBuilder()
                .status("enabled")
                .build();

        PutAgenticBucketStatusRequest request = PutAgenticBucketStatusRequest.newBuilder()
                .bucket("test-bucket")
                .agenticBucketStatus(statusBody)
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.agenticBucketStatus()).isNotNull();
        assertThat(request.agenticBucketStatus().status()).isEqualTo("enabled");

        OperationInput input = SerdeAgenticBucketBasic.fromPutAgenticBucketStatus(request);
        assertThat(input.opName()).isEqualTo("PutAgenticBucketStatus");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("status")).isEqualTo("");
        assertThat(input.body()).isPresent();
    }
}
