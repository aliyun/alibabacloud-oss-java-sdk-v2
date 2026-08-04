package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateJobResultTest {

    @Test
    public void testEmptyResult() {
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(""))
                .build();

        CreateJobResult result = SerdeBatchOperations.toCreateJob(blankOutput);
        assertThat(result).isNotNull();
        assertThat(result.createJobResult()).isNull();
    }

    @Test
    public void testXmlDeserialization() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<CreateJobResult>\n" +
                "  <JobId>MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=</JobId>\n" +
                "</CreateJobResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .build();

        CreateJobResult result = SerdeBatchOperations.toCreateJob(output);
        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.status()).isEqualTo("OK");
        assertThat(result.createJobResult()).isNotNull();
        assertThat(result.createJobResult().jobId()).isEqualTo("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=");
    }

    @Test
    public void testToBuilder() {
        CreateJobResult result = CreateJobResult.newBuilder()
                .statusCode(200)
                .status("OK")
                .innerBody(CreateJobResultBody.newBuilder()
                        .jobId("testJobId123")
                        .build())
                .build();

        CreateJobResult copy = result.toBuilder().build();
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.createJobResult()).isNotNull();
        assertThat(copy.createJobResult().jobId()).isEqualTo("testJobId123");
    }

    @Test
    public void testResultModelFields() {
        String xml =
                "<CreateJobResult>\n" +
                "  <JobId>AAABBBCCC123</JobId>\n" +
                "</CreateJobResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .build();

        CreateJobResult result = SerdeBatchOperations.toCreateJob(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.createJobResult()).isNotNull();
        assertThat(result.createJobResult().jobId()).isEqualTo("AAABBBCCC123");
    }
}
