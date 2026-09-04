package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateJobStatusResultTest {

    @Test
    public void testEmptyResult() {
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(""))
                .build();

        UpdateJobStatusResult result = SerdeBatchOperations.toUpdateJobStatus(blankOutput);
        assertThat(result).isNotNull();
        assertThat(result.updateJobStatusResult()).isNull();
    }

    @Test
    public void testXmlDeserialization() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<UpdateJobStatusResult>\n" +
                "  <JobId>MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=</JobId>\n" +
                "  <Status>Cancelling</Status>\n" +
                "  <StatusUpdateReason>User requested cancellation</StatusUpdateReason>\n" +
                "</UpdateJobStatusResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .build();

        UpdateJobStatusResult result = SerdeBatchOperations.toUpdateJobStatus(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.updateJobStatusResult()).isNotNull();
        assertThat(result.updateJobStatusResult().jobId()).isEqualTo("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=");
        assertThat(result.updateJobStatusResult().status()).isEqualTo("Cancelling");
        assertThat(result.updateJobStatusResult().statusUpdateReason()).isEqualTo("User requested cancellation");
    }

    @Test
    public void testToBuilder() {
        UpdateJobStatusResult result = UpdateJobStatusResult.newBuilder()
                .statusCode(200)
                .status("OK")
                .innerBody(UpdateJobStatusResultBody.newBuilder()
                        .jobId("job123")
                        .status("Ready")
                        .statusUpdateReason("confirmed")
                        .build())
                .build();

        UpdateJobStatusResult copy = result.toBuilder().build();
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.updateJobStatusResult()).isNotNull();
        assertThat(copy.updateJobStatusResult().jobId()).isEqualTo("job123");
        assertThat(copy.updateJobStatusResult().status()).isEqualTo("Ready");
        assertThat(copy.updateJobStatusResult().statusUpdateReason()).isEqualTo("confirmed");
    }

    @Test
    public void testResultModelFields() {
        String xml =
                "<UpdateJobStatusResult>\n" +
                "  <JobId>AAABBBCCC123</JobId>\n" +
                "  <Status>Cancelled</Status>\n" +
                "  <StatusUpdateReason>manual cancel</StatusUpdateReason>\n" +
                "</UpdateJobStatusResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .build();

        UpdateJobStatusResult result = SerdeBatchOperations.toUpdateJobStatus(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.updateJobStatusResult()).isNotNull();
        assertThat(result.updateJobStatusResult().jobId()).isEqualTo("AAABBBCCC123");
        assertThat(result.updateJobStatusResult().status()).isEqualTo("Cancelled");
        assertThat(result.updateJobStatusResult().statusUpdateReason()).isEqualTo("manual cancel");
    }
}
