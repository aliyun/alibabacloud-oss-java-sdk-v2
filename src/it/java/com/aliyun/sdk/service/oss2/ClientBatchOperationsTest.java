package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

public class ClientBatchOperationsTest extends TestBase {

    @Test
    public void testDescribeJob() {
        OSSClient client = getDefaultClient();

        // First create a job to get a valid job ID
        String jobId = createTestJob(client, "DescribeJob test");

        // Describe the job
        DescribeJobRequest request = DescribeJobRequest.newBuilder()
                .batchJobId(jobId)
                .build();

        DescribeJobResult result = client.describeJob(request);
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.describeJobResult());
        Assert.assertNotNull(result.describeJobResult().job());
        Assert.assertEquals(jobId, result.describeJobResult().job().jobId());
        Assert.assertNotNull(result.describeJobResult().job().status());
        Assert.assertNotNull(result.describeJobResult().job().operation());
    }

    @Test
    public void testListJobs() {
        OSSClient client = getDefaultClient();

        // List all jobs
        ListJobsRequest request = ListJobsRequest.newBuilder()
                .maxKeys(10)
                .build();

        ListJobsResult result = client.listJobs(request);
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.listJobsResult());
    }

    @Test
    public void testListJobsWithStatusFilter() {
        OSSClient client = getDefaultClient();

        // List jobs with status filter
        ListJobsRequest request = ListJobsRequest.newBuilder()
                .batchJobStatuses("Complete")
                .maxKeys(5)
                .build();

        ListJobsResult result = client.listJobs(request);
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.listJobsResult());
    }

    @Test
    public void testUpdateJobPriority() {
        OSSClient client = getDefaultClient();

        // First create a job
        String jobId = createTestJob(client, "UpdateJobPriority test");

        // Update priority
        UpdateJobPriorityRequest request = UpdateJobPriorityRequest.newBuilder()
                .batchJobId(jobId)
                .targetPriority(20)
                .build();

        UpdateJobPriorityResult result = client.updateJobPriority(request);
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.updateJobPriorityResult());
        Assert.assertEquals(jobId, result.updateJobPriorityResult().jobId());
        Assert.assertNotNull(result.updateJobPriorityResult().priority());
    }

    @Test
    public void testUpdateJobStatus() {
        OSSClient client = getDefaultClient();

        // First create a job
        String jobId = createTestJob(client, "UpdateJobStatus test");

        // Cancel the job
        UpdateJobStatusRequest request = UpdateJobStatusRequest.newBuilder()
                .batchJobId(jobId)
                .requestedJobStatus("Cancelled")
                .statusUpdateReason("Integration test cancellation")
                .build();

        UpdateJobStatusResult result = client.updateJobStatus(request);
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.updateJobStatusResult());
        Assert.assertEquals(jobId, result.updateJobStatusResult().jobId());
        Assert.assertNotNull(result.updateJobStatusResult().status());
    }

    private String createTestJob(OSSClient client, String description) {
        JobDeleteObjectTagging deleteTagging = JobDeleteObjectTagging.newBuilder().build();
        JobOperation operation = JobOperation.newBuilder()
                .deleteObjectTagging(deleteTagging)
                .build();

        JobReport report = JobReport.newBuilder()
                .bucket(bucketName)
                .enabled(true)
                .prefix("batch-test-reports/")
                .reportScope("AllTasks")
                .build();

        KeyPrefixManifestGenerator generator = KeyPrefixManifestGenerator.newBuilder()
                .sourceBucket(bucketName)
                .prefix("test/")
                .build();

        CreateJobRequestBody body = CreateJobRequestBody.newBuilder()
                .confirmationRequired(false)
                .operation(operation)
                .report(report)
                .clientRequestToken(UUID.randomUUID().toString())
                .keyPrefixManifestGenerator(generator)
                .description(description)
                .priority(10L)
                .roleArn(ramRoleArn())
                .build();

        CreateJobRequest request = CreateJobRequest.newBuilder()
                .createJobBody(body)
                .build();

        CreateJobResult result = client.createJob(request);
        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.createJobResult());
        Assert.assertNotNull(result.createJobResult().jobId());
        return result.createJobResult().jobId();
    }
}
