package com.aliyun.sdk.service.oss2.agentic.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ListBucketSpacesRequestTest {

    @Test
    public void testFullBuilder() {
        ListBucketSpacesRequest request = ListBucketSpacesRequest.newBuilder()
                .bucket("test-bucket")
                .prefix("space-")
                .continuationToken("token-123")
                .startAfter("space-000")
                .maxKeys(100L)
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.prefix()).isEqualTo("space-");
        assertThat(request.continuationToken()).isEqualTo("token-123");
        assertThat(request.startAfter()).isEqualTo("space-000");
        assertThat(request.maxKeys()).isEqualTo(100L);
    }

    @Test
    public void testToBuilder() {
        ListBucketSpacesRequest original = ListBucketSpacesRequest.newBuilder()
                .bucket("test-bucket")
                .continuationToken("token-abc")
                .build();

        ListBucketSpacesRequest copy = original.toBuilder()
                .continuationToken("token-next")
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.continuationToken()).isEqualTo("token-next");
    }

    @Test
    public void testSerde() {
        ListBucketSpacesRequest request = ListBucketSpacesRequest.newBuilder()
                .bucket("test-bucket")
                .continuationToken("token-xyz")
                .startAfter("space-000")
                .maxKeys(200L)
                .build();

        OperationInput input = SerdeAgenticBucketBasic.fromListBucketSpaces(request);
        assertThat(input.opName()).isEqualTo("ListBucketSpaces");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.bucket().isPresent()).isTrue();
        assertThat(input.bucket().get()).isEqualTo("test-bucket");
        assertThat(input.parameters().get("agenticBucket")).isEqualTo("");
        assertThat(input.parameters().get("bucketSpace")).isEqualTo("");
        assertThat(input.parameters().get("continuation-token")).isEqualTo("token-xyz");
        assertThat(input.parameters().get("start-after")).isEqualTo("space-000");
        assertThat(input.parameters().get("max-keys")).isEqualTo("200");
    }
}
