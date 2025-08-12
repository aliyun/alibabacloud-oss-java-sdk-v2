package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.GetBucketAclRequest;
import com.aliyun.sdk.service.oss2.models.GetBucketAclResult;
import com.aliyun.sdk.service.oss2.models.PutBucketAclRequest;
import com.aliyun.sdk.service.oss2.models.PutBucketAclResult;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketAcl;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class BucketAcl {


    public static PutBucketAclResult putBucketAcl(ClientImpl impl, PutBucketAclRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketAcl.fromPutBucketAcl(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketAcl.toPutBucketAcl(output);
    }

    public static CompletableFuture<PutBucketAclResult> putBucketAclAsync(ClientImpl impl, PutBucketAclRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketAcl.fromPutBucketAcl(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketAcl::toPutBucketAcl);
    }


    public static GetBucketAclResult getBucketAcl(ClientImpl impl, GetBucketAclRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketAcl.fromGetBucketAcl(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketAcl.toGetBucketAcl(output);
    }

    public static CompletableFuture<GetBucketAclResult> getBucketAclAsync(ClientImpl impl, GetBucketAclRequest request, OperationOptions options) {
        
        requireNonNull(request.bucket(), "request.bucket is required");
        
        OperationInput input = SerdeBucketAcl.fromGetBucketAcl(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketAcl::toGetBucketAcl);
    }


}