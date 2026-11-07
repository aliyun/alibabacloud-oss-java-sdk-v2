package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ListJobsRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListJobsRequest request = ListJobsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.batchJobStatuses()).isNull();
        assertThat(request.maxKeys()).isNull();
        assertThat(request.continuationToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ListJobsRequest request = ListJobsRequest.newBuilder()
                .batchJobStatuses("Complete")
                .maxKeys(10)
                .continuationToken("next-page-token-123")
                .build();

        assertThat(request.batchJobStatuses()).isEqualTo("Complete");
        assertThat(request.maxKeys()).isEqualTo("10");
        assertThat(request.continuationToken()).isEqualTo("next-page-token-123");
    }

    @Test
    public void testSerdeFromListJobs() {
        ListJobsRequest request = ListJobsRequest.newBuilder()
                .batchJobStatuses("Complete")
                .maxKeys(10)
                .continuationToken("token123")
                .build();

        OperationInput input = SerdeBatchOperations.fromListJobs(request);

        assertThat(input.opName()).isEqualTo("ListJobs");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.parameters().get("batchJob")).isEqualTo("");
        assertThat(input.parameters().get("batchJobStatuses")).isEqualTo("Complete");
        assertThat(input.parameters().get("max-keys")).isEqualTo("10");
        assertThat(input.parameters().get("continuation-token")).isEqualTo("token123");
    }

    @Test
    public void testToBuilder() {
        ListJobsRequest request = ListJobsRequest.newBuilder()
                .batchJobStatuses("Active")
                .maxKeys(50)
                .build();

        ListJobsRequest copy = request.toBuilder().build();
        assertThat(copy.batchJobStatuses()).isEqualTo("Active");
        assertThat(copy.maxKeys()).isEqualTo("50");
    }
}
