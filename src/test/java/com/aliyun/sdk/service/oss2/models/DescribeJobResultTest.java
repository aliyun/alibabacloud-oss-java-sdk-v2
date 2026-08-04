package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DescribeJobResultTest {

    @Test
    public void testEmptyResult() {
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(""))
                .build();

        DescribeJobResult result = SerdeBatchOperations.toDescribeJob(blankOutput);
        assertThat(result).isNotNull();
        assertThat(result.describeJobResult()).isNull();
    }

    @Test
    public void testXmlDeserialization() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<DescribeJobResult>\n" +
                "  <Job>\n" +
                "    <ConfirmationRequired>false</ConfirmationRequired>\n" +
                "    <CreationTime>1749983400</CreationTime>\n" +
                "    <JobId>MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=</JobId>\n" +
                "    <Operation>\n" +
                "      <RestoreObject>\n" +
                "        <Days>7</Days>\n" +
                "        <Tier>Standard</Tier>\n" +
                "      </RestoreObject>\n" +
                "    </Operation>\n" +
                "    <Report>\n" +
                "      <Bucket>report-bucket</Bucket>\n" +
                "      <Enabled>true</Enabled>\n" +
                "      <Prefix>reports/</Prefix>\n" +
                "      <ReportScope>AllTasks</ReportScope>\n" +
                "    </Report>\n" +
                "    <Manifest>\n" +
                "      <Location>\n" +
                "        <ETag>d41d8cd98f00b204e9800998ecf8427e</ETag>\n" +
                "        <Bucket>manifest-bucket</Bucket>\n" +
                "        <Object>manifest.csv</Object>\n" +
                "      </Location>\n" +
                "      <Spec>\n" +
                "        <Fields>Bucket,Key</Fields>\n" +
                "        <Format>OSS_BatchOperations_CSV_20250611</Format>\n" +
                "      </Spec>\n" +
                "    </Manifest>\n" +
                "    <Description>batch restore job</Description>\n" +
                "    <Priority>10</Priority>\n" +
                "    <RoleArn>arn:acs:ram::uid:role/BatchOperationRole</RoleArn>\n" +
                "    <StatusUpdateReason>Task completed successfully</StatusUpdateReason>\n" +
                "    <ProgressSummary>\n" +
                "      <NumberOfTasksFailed>0</NumberOfTasksFailed>\n" +
                "      <NumberOfTasksSucceeded>1000</NumberOfTasksSucceeded>\n" +
                "      <Timers>\n" +
                "        <ElapsedTimeInActiveSeconds>3600</ElapsedTimeInActiveSeconds>\n" +
                "      </Timers>\n" +
                "      <TotalNumberOfTasks>1000</TotalNumberOfTasks>\n" +
                "    </ProgressSummary>\n" +
                "    <Status>Complete</Status>\n" +
                "    <TerminationDate>1749987000</TerminationDate>\n" +
                "  </Job>\n" +
                "</DescribeJobResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .build();

        DescribeJobResult result = SerdeBatchOperations.toDescribeJob(output);
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.describeJobResult()).isNotNull();

        JobDetail job = result.describeJobResult().job();
        assertThat(job).isNotNull();
        assertThat(job.confirmationRequired()).isFalse();
        assertThat(job.creationTime()).isEqualTo(1749983400L);
        assertThat(job.jobId()).isEqualTo("MzRjZGU2NGQ3YTY5NGRhMTkxZmZhYzY5OTM5YTcxYWU=");
        assertThat(job.description()).isEqualTo("batch restore job");
        assertThat(job.priority()).isEqualTo(10L);
        assertThat(job.roleArn()).isEqualTo("arn:acs:ram::uid:role/BatchOperationRole");
        assertThat(job.status()).isEqualTo("Complete");
        assertThat(job.statusUpdateReason()).isEqualTo("Task completed successfully");
        assertThat(job.terminationDate()).isEqualTo(1749987000L);

        // Verify operation
        assertThat(job.operation()).isNotNull();
        assertThat(job.operation().restoreObject()).isNotNull();
        assertThat(job.operation().restoreObject().days()).isEqualTo(7L);
        assertThat(job.operation().restoreObject().tier()).isEqualTo("Standard");

        // Verify report
        assertThat(job.report()).isNotNull();
        assertThat(job.report().bucket()).isEqualTo("report-bucket");
        assertThat(job.report().enabled()).isTrue();
        assertThat(job.report().prefix()).isEqualTo("reports/");
        assertThat(job.report().reportScope()).isEqualTo("AllTasks");

        // Verify manifest
        assertThat(job.manifest()).isNotNull();
        assertThat(job.manifest().location().bucket()).isEqualTo("manifest-bucket");
        assertThat(job.manifest().location().object()).isEqualTo("manifest.csv");
        assertThat(job.manifest().spec().format()).isEqualTo("OSS_BatchOperations_CSV_20250611");

        // Verify progress summary
        assertThat(job.progressSummary()).isNotNull();
        assertThat(job.progressSummary().numberOfTasksFailed()).isEqualTo(0L);
        assertThat(job.progressSummary().numberOfTasksSucceeded()).isEqualTo(1000L);
        assertThat(job.progressSummary().totalNumberOfTasks()).isEqualTo(1000L);
        assertThat(job.progressSummary().timers()).isNotNull();
        assertThat(job.progressSummary().timers().elapsedTimeInActiveSeconds()).isEqualTo(3600L);
    }

    @Test
    public void testXmlWithKeyPrefixManifestGenerator() {
        String xml =
                "<DescribeJobResult>\n" +
                "  <Job>\n" +
                "    <JobId>testJob123</JobId>\n" +
                "    <Status>Active</Status>\n" +
                "    <KeyPrefixManifestGenerator>\n" +
                "      <SourceBucket>source-bucket</SourceBucket>\n" +
                "      <Prefix>data/</Prefix>\n" +
                "    </KeyPrefixManifestGenerator>\n" +
                "  </Job>\n" +
                "</DescribeJobResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .build();

        DescribeJobResult result = SerdeBatchOperations.toDescribeJob(output);
        JobDetail job = result.describeJobResult().job();
        assertThat(job.jobId()).isEqualTo("testJob123");
        assertThat(job.status()).isEqualTo("Active");
        assertThat(job.keyPrefixManifestGenerator()).isNotNull();
        assertThat(job.keyPrefixManifestGenerator().sourceBucket()).isEqualTo("source-bucket");
        assertThat(job.keyPrefixManifestGenerator().prefix()).isEqualTo("data/");
    }

    @Test
    public void testToBuilder() {
        DescribeJobResult result = DescribeJobResult.newBuilder()
                .statusCode(200)
                .status("OK")
                .innerBody(DescribeJobResultBody.newBuilder()
                        .job(JobDetail.newBuilder()
                                .jobId("job123")
                                .status("Complete")
                                .build())
                        .build())
                .build();

        DescribeJobResult copy = result.toBuilder().build();
        assertThat(copy.statusCode()).isEqualTo(200);
        assertThat(copy.describeJobResult().job().jobId()).isEqualTo("job123");
        assertThat(copy.describeJobResult().job().status()).isEqualTo("Complete");
    }
}
