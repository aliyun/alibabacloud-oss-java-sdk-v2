package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DescribeJobRequestTest {

    @Test
    public void testBasicBuilder() {
        DescribeJobRequest request = DescribeJobRequest.newBuilder()
                .batchJobId("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=")
                .build();

        assertThat(request).isNotNull();
        assertThat(request.batchJobId()).isEqualTo("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=");
    }

    @Test
    public void testToBuilder() {
        DescribeJobRequest request = DescribeJobRequest.newBuilder()
                .batchJobId("job123")
                .parameter("param1", "value1")
                .build();

        DescribeJobRequest copy = request.toBuilder().build();
        assertThat(copy.batchJobId()).isEqualTo("job123");
        assertThat(copy.parameters().get("param1")).isEqualTo("value1");
    }

    @Test
    public void testSerdeFromDescribeJob() {
        DescribeJobRequest request = DescribeJobRequest.newBuilder()
                .batchJobId("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=")
                .build();

        OperationInput input = SerdeBatchOperations.fromDescribeJob(request);

        assertThat(input.opName()).isEqualTo("DescribeJob");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.parameters().get("batchJob")).isEqualTo("");
        assertThat(input.parameters().get("batchJobId")).isEqualTo("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
    }

    @Test
    public void testNullBatchJobId() {
        DescribeJobRequest request = DescribeJobRequest.newBuilder().build();
        assertThat(request.batchJobId()).isNull();
    }
}
