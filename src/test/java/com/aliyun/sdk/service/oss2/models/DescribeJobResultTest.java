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
    public void testXmlWithFailureReasons() {
        String xml =
                "<DescribeJobResult>\n" +
                "  <Job>\n" +
                "    <ConfirmationRequired>false</ConfirmationRequired>\n" +
                "    <CreationTime>1785831911</CreationTime>\n" +
                "    <FailureReasons>\n" +
                "      <JobFailure>\n" +
                "        <FailureCode>TooManyFailures</FailureCode>\n" +
                "        <FailureReason>Too many failures.</FailureReason>\n" +
                "      </JobFailure>\n" +
                "    </FailureReasons>\n" +
                "    <JobId>ZDc3YmY1ODUyNjg2NDM3OGExM2Y2YmQ5NDk0NWI2NTU=</JobId>\n" +
                "    <Operation>\n" +
                "      <PutObjectAcl>\n" +
                "        <ObjectAcl>public-read</ObjectAcl>\n" +
                "      </PutObjectAcl>\n" +
                "    </Operation>\n" +
                "    <Report>\n" +
                "      <Enabled>true</Enabled>\n" +
                "      <Bucket>sdk-oss-test-hz-zxl</Bucket>\n" +
                "      <Prefix>batch-reports/</Prefix>\n" +
                "      <ReportScope>AllTasks</ReportScope>\n" +
                "    </Report>\n" +
                "    <Description>Batch put object acl job</Description>\n" +
                "    <Priority>10</Priority>\n" +
                "    <RoleArn>acs:ram::1303778382245978:role/oss-sdk-batch-test</RoleArn>\n" +
                "    <KeyPrefixManifestGenerator>\n" +
                "      <SourceBucket>sdk-oss-test-hz-zxl</SourceBucket>\n" +
                "      <Prefix>test/</Prefix>\n" +
                "    </KeyPrefixManifestGenerator>\n" +
                "    <ProgressSummary>\n" +
                "      <NumberOfTasksFailed>3</NumberOfTasksFailed>\n" +
                "      <NumberOfTasksSucceeded>0</NumberOfTasksSucceeded>\n" +
                "      <TotalNumberOfTasks>3</TotalNumberOfTasks>\n" +
                "      <Timers>\n" +
                "        <ElapsedTimeInActiveSeconds>8</ElapsedTimeInActiveSeconds>\n" +
                "      </Timers>\n" +
                "    </ProgressSummary>\n" +
                "    <Status>Failed</Status>\n" +
                "    <TerminationDate>1785831929</TerminationDate>\n" +
                "  </Job>\n" +
                "</DescribeJobResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .statusCode(200)
                .status("OK")
                .build();

        DescribeJobResult result = SerdeBatchOperations.toDescribeJob(output);
        assertThat(result.describeJobResult()).isNotNull();

        JobDetail job = result.describeJobResult().job();
        assertThat(job.confirmationRequired()).isFalse();
        assertThat(job.creationTime()).isEqualTo(1785831911L);
        assertThat(job.jobId()).isEqualTo("ZDc3YmY1ODUyNjg2NDM3OGExM2Y2YmQ5NDk0NWI2NTU=");
        assertThat(job.description()).isEqualTo("Batch put object acl job");
        assertThat(job.priority()).isEqualTo(10L);
        assertThat(job.roleArn()).isEqualTo("acs:ram::1303778382245978:role/oss-sdk-batch-test");
        assertThat(job.status()).isEqualTo("Failed");
        assertThat(job.terminationDate()).isEqualTo(1785831929L);

        // Verify FailureReasons - single JobFailure object
        assertThat(job.failureReasons()).isNotNull();
        assertThat(job.failureReasons().jobFailure()).isNotNull();
        assertThat(job.failureReasons().jobFailure().failureCode()).isEqualTo("TooManyFailures");
        assertThat(job.failureReasons().jobFailure().failureReason()).isEqualTo("Too many failures.");

        // Verify operation
        assertThat(job.operation()).isNotNull();
        assertThat(job.operation().putObjectAcl()).isNotNull();
        assertThat(job.operation().putObjectAcl().objectAcl()).isEqualTo("public-read");

        // Verify report
        assertThat(job.report()).isNotNull();
        assertThat(job.report().enabled()).isTrue();
        assertThat(job.report().bucket()).isEqualTo("sdk-oss-test-hz-zxl");

        // Verify progress summary
        assertThat(job.progressSummary()).isNotNull();
        assertThat(job.progressSummary().numberOfTasksFailed()).isEqualTo(3L);
        assertThat(job.progressSummary().numberOfTasksSucceeded()).isEqualTo(0L);
        assertThat(job.progressSummary().totalNumberOfTasks()).isEqualTo(3L);
    }

    @Test
    public void testBuilderWithFailureReasons() {
        JobDetail job = JobDetail.newBuilder()
                .jobId("job456")
                .status("Failed")
                .failureReasons(FailureReasons.newBuilder()
                        .jobFailure(JobFailure.newBuilder()
                                .failureCode("TooManyFailures")
                                .failureReason("Too many failures.")
                                .build())
                        .build())
                .build();

        assertThat(job.failureReasons()).isNotNull();
        assertThat(job.failureReasons().jobFailure()).isNotNull();
        assertThat(job.failureReasons().jobFailure().failureCode()).isEqualTo("TooManyFailures");
        assertThat(job.failureReasons().jobFailure().failureReason()).isEqualTo("Too many failures.");

        // Verify toBuilder round-trip
        JobDetail copy = job.toBuilder().build();
        assertThat(copy.failureReasons()).isNotNull();
        assertThat(copy.failureReasons().jobFailure()).isNotNull();
        assertThat(copy.failureReasons().jobFailure().failureCode()).isEqualTo("TooManyFailures");
        assertThat(copy.failureReasons().jobFailure().failureReason()).isEqualTo("Too many failures.");
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
