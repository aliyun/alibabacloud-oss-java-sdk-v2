package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ClientBatchOperationsAsyncTest extends TestBase {

    @Test
    public void testDescribeJobAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();

        // First create a job using sync client
        String jobId = createTestJob(getDefaultClient(), "Async DescribeJob test");

        // Describe the job asynchronously
        DescribeJobRequest request = DescribeJobRequest.newBuilder()
                .batchJobId(jobId)
                .build();

        CompletableFuture<DescribeJobResult> future = client.describeJobAsync(request);
        DescribeJobResult result = future.get();

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.describeJobResult());
        Assert.assertNotNull(result.describeJobResult().job());
        Assert.assertEquals(jobId, result.describeJobResult().job().jobId());
    }

    @Test
    public void testListJobsAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();

        ListJobsRequest request = ListJobsRequest.newBuilder()
                .maxKeys(10)
                .build();

        CompletableFuture<ListJobsResult> future = client.listJobsAsync(request);
        ListJobsResult result = future.get();

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.listJobsResult());
    }

    @Test
    public void testUpdateJobPriorityAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();

        // First create a job using sync client
        String jobId = createTestJob(getDefaultClient(), "Async UpdateJobPriority test");

        // Update priority asynchronously
        UpdateJobPriorityRequest request = UpdateJobPriorityRequest.newBuilder()
                .batchJobId(jobId)
                .targetPriority(25)
                .build();

        CompletableFuture<UpdateJobPriorityResult> future = client.updateJobPriorityAsync(request);
        UpdateJobPriorityResult result = future.get();

        Assert.assertNotNull(result);
        Assert.assertEquals(200, result.statusCode());
        Assert.assertNotNull(result.updateJobPriorityResult());
        Assert.assertEquals(jobId, result.updateJobPriorityResult().jobId());
    }

    @Test
    public void testUpdateJobStatusAsync() throws Exception {
        OSSAsyncClient client = getDefaultAsyncClient();

        // First create a job using sync client
        String jobId = createTestJob(getDefaultClient(), "Async UpdateJobStatus test");

        // Cancel the job asynchronously
        UpdateJobStatusRequest request = UpdateJobStatusRequest.newBuilder()
                .batchJobId(jobId)
                .requestedJobStatus("Cancelled")
                .statusUpdateReason("Async integration test cancellation")
                .build();

        CompletableFuture<UpdateJobStatusResult> future = client.updateJobStatusAsync(request);
        UpdateJobStatusResult result = future.get();

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
                .prefix("async-batch-test-reports/")
                .reportScope("AllTasks")
                .build();

        KeyPrefixManifestGenerator generator = KeyPrefixManifestGenerator.newBuilder()
                .sourceBucket(bucketName)
                .prefix("async-test/")
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
