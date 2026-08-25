package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ListAgenticBucketsRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListAgenticBucketsRequest request = ListAgenticBucketsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.continuationToken()).isNull();
        assertThat(request.maxKeys()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ListAgenticBucketsRequest request = ListAgenticBucketsRequest.newBuilder()
                .continuationToken("token-123")
                .maxKeys(100L)
                .build();

        assertThat(request.continuationToken()).isEqualTo("token-123");
        assertThat(request.maxKeys()).isEqualTo(100L);
    }

    @Test
    public void testToBuilder() {
        ListAgenticBucketsRequest original = ListAgenticBucketsRequest.newBuilder()
                .continuationToken("token-abc")
                .maxKeys(50L)
                .build();

        ListAgenticBucketsRequest copy = original.toBuilder()
                .continuationToken("token-next")
                .build();

        assertThat(copy.continuationToken()).isEqualTo("token-next");
        assertThat(copy.maxKeys()).isEqualTo(50L);
    }

    @Test
    public void testSerde() {
        ListAgenticBucketsRequest request = ListAgenticBucketsRequest.newBuilder()
                .continuationToken("token-xyz")
                .maxKeys(200L)
                .build();

        OperationInput input = SerdeAgenticBucketBasic.fromListAgenticBuckets(request);
        assertThat(input.opName()).isEqualTo("ListAgenticBuckets");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.bucket().isPresent()).isFalse();
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("continuation-token")).isEqualTo("token-xyz");
        assertThat(input.parameters().get("max-keys")).isEqualTo("200");
    }
}
