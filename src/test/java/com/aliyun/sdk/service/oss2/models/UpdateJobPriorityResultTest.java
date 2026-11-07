package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateJobPriorityResultTest {

    @Test
    public void testEmptyResult() {
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(""))
                .build();

        UpdateJobPriorityResult result = SerdeBatchOperations.toUpdateJobPriority(blankOutput);
        assertThat(result).isNotNull();
        assertThat(result.updateJobPriorityResult()).isNull();
    }

    @Test
    public void testXmlDeserialization() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<UpdateJobPriorityResult>\n" +
                "  <JobId>MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=</JobId>\n" +
                "  <Priority>20</Priority>\n" +
                "</UpdateJobPriorityResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .build();

        UpdateJobPriorityResult result = SerdeBatchOperations.toUpdateJobPriority(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.updateJobPriorityResult()).isNotNull();
        assertThat(result.updateJobPriorityResult().jobId()).isEqualTo("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=");
        assertThat(result.updateJobPriorityResult().priority()).isEqualTo(20L);
    }

    @Test
    public void testToBuilder() {
        UpdateJobPriorityResult result = UpdateJobPriorityResult.newBuilder()
                .statusCode(200)
                .status("OK")
                .innerBody(UpdateJobPriorityResultBody.newBuilder()
                        .jobId("job123")
                        .priority(15L)
                        .build())
                .build();

        UpdateJobPriorityResult copy = result.toBuilder().build();
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.updateJobPriorityResult()).isNotNull();
        assertThat(copy.updateJobPriorityResult().jobId()).isEqualTo("job123");
        assertThat(copy.updateJobPriorityResult().priority()).isEqualTo(15L);
    }

    @Test
    public void testResultModelFields() {
        String xml =
                "<UpdateJobPriorityResult>\n" +
                "  <JobId>AAABBBCCC123</JobId>\n" +
                "  <Priority>99</Priority>\n" +
                "</UpdateJobPriorityResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .build();

        UpdateJobPriorityResult result = SerdeBatchOperations.toUpdateJobPriority(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.updateJobPriorityResult()).isNotNull();
        assertThat(result.updateJobPriorityResult().jobId()).isEqualTo("AAABBBCCC123");
        assertThat(result.updateJobPriorityResult().priority()).isEqualTo(99L);
    }
}
