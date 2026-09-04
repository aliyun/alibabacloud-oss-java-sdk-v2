package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateJobPriorityRequestTest {

    @Test
    public void testBasicBuilder() {
        UpdateJobPriorityRequest request = UpdateJobPriorityRequest.newBuilder()
                .batchJobId("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=")
                .targetPriority(20)
                .build();

        assertThat(request).isNotNull();
        assertThat(request.batchJobId()).isEqualTo("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=");
        assertThat(request.targetPriority()).isEqualTo("20");
    }

    @Test
    public void testSerdeFromUpdateJobPriority() {
        UpdateJobPriorityRequest request = UpdateJobPriorityRequest.newBuilder()
                .batchJobId("job123")
                .targetPriority(20)
                .build();

        OperationInput input = SerdeBatchOperations.fromUpdateJobPriority(request);

        assertThat(input.opName()).isEqualTo("UpdateJobPriority");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.parameters().get("batchJobPriority")).isEqualTo("");
        assertThat(input.parameters().get("batchJobId")).isEqualTo("job123");
        assertThat(input.parameters().get("targetPriority")).isEqualTo("20");
    }

    @Test
    public void testToBuilder() {
        UpdateJobPriorityRequest request = UpdateJobPriorityRequest.newBuilder()
                .batchJobId("job123")
                .targetPriority(10)
                .parameter("extra", "val")
                .build();

        UpdateJobPriorityRequest copy = request.toBuilder().build();
        assertThat(copy.batchJobId()).isEqualTo("job123");
        assertThat(copy.targetPriority()).isEqualTo("10");
        assertThat(copy.parameters().get("extra")).isEqualTo("val");
    }

    @Test
    public void testNullFields() {
        UpdateJobPriorityRequest request = UpdateJobPriorityRequest.newBuilder().build();
        assertThat(request.batchJobId()).isNull();
        assertThat(request.targetPriority()).isNull();
    }
}
