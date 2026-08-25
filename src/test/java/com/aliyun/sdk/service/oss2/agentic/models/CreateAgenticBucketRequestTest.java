package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateAgenticBucketRequestTest {

    @Test
    public void testEmptyBuilder() {
        CreateAgenticBucketRequest request = CreateAgenticBucketRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.bucket()).isNull();
        assertThat(request.createAgenticBucketConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        CreateAgenticBucketConfiguration config = CreateAgenticBucketConfiguration.newBuilder()
                .storageClass("Standard")
                .dataRedundancyType("LRS")
                .build();

        CreateAgenticBucketRequest request = CreateAgenticBucketRequest.newBuilder()
                .bucket("test-bucket")
                .createAgenticBucketConfiguration(config)
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.createAgenticBucketConfiguration()).isNotNull();
        assertThat(request.createAgenticBucketConfiguration().storageClass()).isEqualTo("Standard");
        assertThat(request.createAgenticBucketConfiguration().dataRedundancyType()).isEqualTo("LRS");
    }

    @Test
    public void testToBuilder() {
        CreateAgenticBucketRequest original = CreateAgenticBucketRequest.newBuilder()
                .bucket("original-bucket")
                .build();

        CreateAgenticBucketRequest copy = original.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("original-bucket");
    }

    @Test
    public void testSerde() {
        CreateAgenticBucketConfiguration config = CreateAgenticBucketConfiguration.newBuilder()
                .storageClass("Standard")
                .dataRedundancyType("LRS")
                .build();

        CreateAgenticBucketRequest request = CreateAgenticBucketRequest.newBuilder()
                .bucket("test-bucket")
                .createAgenticBucketConfiguration(config)
                .build();

        OperationInput input = SerdeAgenticBucketBasic.fromCreateAgenticBucket(request);
        assertThat(input.opName()).isEqualTo("CreateAgenticBucket");
        assertThat(input.method()).isEqualTo("PUT");
        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.body()).isPresent();
    }
}
