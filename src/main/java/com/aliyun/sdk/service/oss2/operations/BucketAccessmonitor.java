package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.GetBucketAccessMonitorRequest;
import com.aliyun.sdk.service.oss2.models.GetBucketAccessMonitorResult;
import com.aliyun.sdk.service.oss2.models.PutBucketAccessMonitorRequest;
import com.aliyun.sdk.service.oss2.models.PutBucketAccessMonitorResult;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketAccessmonitor;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class BucketAccessmonitor {


    public static PutBucketAccessMonitorResult putBucketAccessMonitor(ClientImpl impl, PutBucketAccessMonitorRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketAccessmonitor.fromPutBucketAccessMonitor(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketAccessmonitor.toPutBucketAccessMonitor(output);
    }

    public static CompletableFuture<PutBucketAccessMonitorResult> putBucketAccessMonitorAsync(ClientImpl impl, PutBucketAccessMonitorRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketAccessmonitor.fromPutBucketAccessMonitor(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketAccessmonitor::toPutBucketAccessMonitor);
    }


    public static GetBucketAccessMonitorResult getBucketAccessMonitor(ClientImpl impl, GetBucketAccessMonitorRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketAccessmonitor.fromGetBucketAccessMonitor(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketAccessmonitor.toGetBucketAccessMonitor(output);
    }

    public static CompletableFuture<GetBucketAccessMonitorResult> getBucketAccessMonitorAsync(ClientImpl impl, GetBucketAccessMonitorRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketAccessmonitor.fromGetBucketAccessMonitor(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketAccessmonitor::toGetBucketAccessMonitor);
    }


}