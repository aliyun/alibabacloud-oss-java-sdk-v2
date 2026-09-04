package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBatchOperations;

import java.util.concurrent.CompletableFuture;

public final class BatchOperations {

    public static CreateJobResult createJob(ClientImpl impl, CreateJobRequest request, OperationOptions options) {

        OperationInput input = SerdeBatchOperations.fromCreateJob(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBatchOperations.toCreateJob(output);
    }

    public static CompletableFuture<CreateJobResult> createJobAsync(ClientImpl impl, CreateJobRequest request, OperationOptions options) {

        OperationInput input = SerdeBatchOperations.fromCreateJob(request);
        return impl.executeAsync(input, options).thenApply(SerdeBatchOperations::toCreateJob);
    }

    public static DescribeJobResult describeJob(ClientImpl impl, DescribeJobRequest request, OperationOptions options) {

        OperationInput input = SerdeBatchOperations.fromDescribeJob(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBatchOperations.toDescribeJob(output);
    }

    public static CompletableFuture<DescribeJobResult> describeJobAsync(ClientImpl impl, DescribeJobRequest request, OperationOptions options) {

        OperationInput input = SerdeBatchOperations.fromDescribeJob(request);
        return impl.executeAsync(input, options).thenApply(SerdeBatchOperations::toDescribeJob);
    }

    public static ListJobsResult listJobs(ClientImpl impl, ListJobsRequest request, OperationOptions options) {

        OperationInput input = SerdeBatchOperations.fromListJobs(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBatchOperations.toListJobs(output);
    }

    public static CompletableFuture<ListJobsResult> listJobsAsync(ClientImpl impl, ListJobsRequest request, OperationOptions options) {

        OperationInput input = SerdeBatchOperations.fromListJobs(request);
        return impl.executeAsync(input, options).thenApply(SerdeBatchOperations::toListJobs);
    }

    public static UpdateJobPriorityResult updateJobPriority(ClientImpl impl, UpdateJobPriorityRequest request, OperationOptions options) {

        OperationInput input = SerdeBatchOperations.fromUpdateJobPriority(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBatchOperations.toUpdateJobPriority(output);
    }

    public static CompletableFuture<UpdateJobPriorityResult> updateJobPriorityAsync(ClientImpl impl, UpdateJobPriorityRequest request, OperationOptions options) {

        OperationInput input = SerdeBatchOperations.fromUpdateJobPriority(request);
        return impl.executeAsync(input, options).thenApply(SerdeBatchOperations::toUpdateJobPriority);
    }

    public static UpdateJobStatusResult updateJobStatus(ClientImpl impl, UpdateJobStatusRequest request, OperationOptions options) {

        OperationInput input = SerdeBatchOperations.fromUpdateJobStatus(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBatchOperations.toUpdateJobStatus(output);
    }

    public static CompletableFuture<UpdateJobStatusResult> updateJobStatusAsync(ClientImpl impl, UpdateJobStatusRequest request, OperationOptions options) {

        OperationInput input = SerdeBatchOperations.fromUpdateJobStatus(request);
        return impl.executeAsync(input, options).thenApply(SerdeBatchOperations::toUpdateJobStatus);
    }
}
