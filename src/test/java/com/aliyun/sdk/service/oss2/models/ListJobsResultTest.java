package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ListJobsResultTest {

    @Test
    public void testEmptyResult() {
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(""))
                .build();

        ListJobsResult result = SerdeBatchOperations.toListJobs(blankOutput);
        assertThat(result).isNotNull();
        assertThat(result.listJobsResult()).isNull();
    }

    @Test
    public void testXmlDeserialization() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListJobsResult>\n" +
                "  <NextToken>next-page-token-456</NextToken>\n" +
                "  <Jobs>\n" +
                "    <JobListDescriptor>\n" +
                "      <CreationTime>1749983400</CreationTime>\n" +
                "      <Description>batch tagging job</Description>\n" +
                "      <JobId>MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=</JobId>\n" +
                "      <Operation>PutObjectTagging</Operation>\n" +
                "      <Priority>10</Priority>\n" +
                "      <ProgressSummary>\n" +
                "        <NumberOfTasksFailed>0</NumberOfTasksFailed>\n" +
                "        <NumberOfTasksSucceeded>1000</NumberOfTasksSucceeded>\n" +
                "        <Timers>\n" +
                "          <ElapsedTimeInActiveSeconds>3600</ElapsedTimeInActiveSeconds>\n" +
                "        </Timers>\n" +
                "        <TotalNumberOfTasks>1000</TotalNumberOfTasks>\n" +
                "      </ProgressSummary>\n" +
                "      <Status>Complete</Status>\n" +
                "      <TerminationDate>1749987000</TerminationDate>\n" +
                "    </JobListDescriptor>\n" +
                "    <JobListDescriptor>\n" +
                "      <CreationTime>1749990000</CreationTime>\n" +
                "      <Description>batch restore job</Description>\n" +
                "      <JobId>YWJjZGVmZzEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU=</JobId>\n" +
                "      <Operation>RestoreObject</Operation>\n" +
                "      <Priority>5</Priority>\n" +
                "      <Status>Active</Status>\n" +
                "    </JobListDescriptor>\n" +
                "  </Jobs>\n" +
                "</ListJobsResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .build();

        ListJobsResult result = SerdeBatchOperations.toListJobs(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.listJobsResult()).isNotNull();
        assertThat(result.listJobsResult().nextToken()).isEqualTo("next-page-token-456");
        assertThat(result.listJobsResult().jobs()).isNotNull();
        assertThat(result.listJobsResult().jobs()).hasSize(2);

        // First job
        JobListDescriptor job1 = result.listJobsResult().jobs().get(0);
        assertThat(job1.creationTime()).isEqualTo(1749983400L);
        assertThat(job1.description()).isEqualTo("batch tagging job");
        assertThat(job1.jobId()).isEqualTo("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=");
        assertThat(job1.operation()).isEqualTo("PutObjectTagging");
        assertThat(job1.priority()).isEqualTo(10L);
        assertThat(job1.status()).isEqualTo("Complete");
        assertThat(job1.terminationDate()).isEqualTo(1749987000L);
        assertThat(job1.progressSummary()).isNotNull();
        assertThat(job1.progressSummary().numberOfTasksSucceeded()).isEqualTo(1000L);
        assertThat(job1.progressSummary().timers().elapsedTimeInActiveSeconds()).isEqualTo(3600L);

        // Second job
        JobListDescriptor job2 = result.listJobsResult().jobs().get(1);
        assertThat(job2.jobId()).isEqualTo("YWJjZGVmZzEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU=");
        assertThat(job2.operation()).isEqualTo("RestoreObject");
        assertThat(job2.status()).isEqualTo("Active");
        assertThat(job2.priority()).isEqualTo(5L);
    }

    @Test
    public void testToBuilder() {
        ListJobsResult result = ListJobsResult.newBuilder()
                .statusCode(200)
                .status("OK")
                .innerBody(ListJobsResultBody.newBuilder()
                        .nextToken("token123")
                        .build())
                .build();

        ListJobsResult copy = result.toBuilder().build();
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.listJobsResult().nextToken()).isEqualTo("token123");
    }
}
