package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateJobStatusRequestTest {

    @Test
    public void testBasicBuilder() {
        UpdateJobStatusRequest request = UpdateJobStatusRequest.newBuilder()
                .batchJobId("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=")
                .requestedJobStatus("Cancelled")
                .statusUpdateReason("User requested cancellation")
                .build();

        assertThat(request).isNotNull();
        assertThat(request.batchJobId()).isEqualTo("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=");
        assertThat(request.requestedJobStatus()).isEqualTo("Cancelled");
        assertThat(request.statusUpdateReason()).isEqualTo("User requested cancellation");
    }

    @Test
    public void testSerdeFromUpdateJobStatus() {
        UpdateJobStatusRequest request = UpdateJobStatusRequest.newBuilder()
                .batchJobId("job123")
                .requestedJobStatus("Cancelled")
                .statusUpdateReason("test reason")
                .build();

        OperationInput input = SerdeBatchOperations.fromUpdateJobStatus(request);

        assertThat(input.opName()).isEqualTo("UpdateJobStatus");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.parameters().get("batchJobStatus")).isEqualTo("");
        assertThat(input.parameters().get("batchJobId")).isEqualTo("job123");
        assertThat(input.parameters().get("requestedJobStatus")).isEqualTo("Cancelled");
        assertThat(input.parameters().get("statusUpdateReason")).isEqualTo("test reason");
    }

    @Test
    public void testToBuilder() {
        UpdateJobStatusRequest request = UpdateJobStatusRequest.newBuilder()
                .batchJobId("job123")
                .requestedJobStatus("Ready")
                .parameter("extra", "val")
                .build();

        UpdateJobStatusRequest copy = request.toBuilder().build();
        assertThat(copy.batchJobId()).isEqualTo("job123");
        assertThat(copy.requestedJobStatus()).isEqualTo("Ready");
        assertThat(copy.parameters().get("extra")).isEqualTo("val");
    }

    @Test
    public void testNullFields() {
        UpdateJobStatusRequest request = UpdateJobStatusRequest.newBuilder().build();
        assertThat(request.batchJobId()).isNull();
        assertThat(request.requestedJobStatus()).isNull();
        assertThat(request.statusUpdateReason()).isNull();
    }
}
