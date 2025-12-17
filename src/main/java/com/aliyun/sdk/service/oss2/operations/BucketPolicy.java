package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketPolicy;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketPolicy {


    public static PutBucketPolicyResult putBucketPolicy(ClientImpl impl, PutBucketPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketPolicy.fromPutBucketPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketPolicy.toPutBucketPolicy(output);
    }

    public static CompletableFuture<PutBucketPolicyResult> putBucketPolicyAsync(ClientImpl impl, PutBucketPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketPolicy.fromPutBucketPolicy(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketPolicy::toPutBucketPolicy);
    }


    public static GetBucketPolicyResult getBucketPolicy(ClientImpl impl, GetBucketPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketPolicy.fromGetBucketPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketPolicy.toGetBucketPolicy(output);
    }

    public static CompletableFuture<GetBucketPolicyResult> getBucketPolicyAsync(ClientImpl impl, GetBucketPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketPolicy.fromGetBucketPolicy(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketPolicy::toGetBucketPolicy);
    }


    public static DeleteBucketPolicyResult deleteBucketPolicy(ClientImpl impl, DeleteBucketPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketPolicy.fromDeleteBucketPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketPolicy.toDeleteBucketPolicy(output);
    }

    public static CompletableFuture<DeleteBucketPolicyResult> deleteBucketPolicyAsync(ClientImpl impl, DeleteBucketPolicyRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketPolicy.fromDeleteBucketPolicy(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketPolicy::toDeleteBucketPolicy);
    }


    public static GetBucketPolicyStatusResult getBucketPolicyStatus(ClientImpl impl, GetBucketPolicyStatusRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketPolicy.fromGetBucketPolicyStatus(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketPolicy.toGetBucketPolicyStatus(output);
    }

    public static CompletableFuture<GetBucketPolicyStatusResult> getBucketPolicyStatusAsync(ClientImpl impl, GetBucketPolicyStatusRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketPolicy.fromGetBucketPolicyStatus(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketPolicy::toGetBucketPolicyStatus);
    }


}