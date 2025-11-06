package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;
import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketResourceGroup {


    public static GetBucketResourceGroupResult getBucketResourceGroup(ClientImpl impl, GetBucketResourceGroupRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketResourceGroup.fromGetBucketResourceGroup(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketResourceGroup.toGetBucketResourceGroup(output);
    }

    public static CompletableFuture<GetBucketResourceGroupResult> getBucketResourceGroupAsync(ClientImpl impl, GetBucketResourceGroupRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketResourceGroup.fromGetBucketResourceGroup(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketResourceGroup::toGetBucketResourceGroup);
    }


    public static PutBucketResourceGroupResult putBucketResourceGroup(ClientImpl impl, PutBucketResourceGroupRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketResourceGroup.fromPutBucketResourceGroup(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketResourceGroup.toPutBucketResourceGroup(output);
    }

    public static CompletableFuture<PutBucketResourceGroupResult> putBucketResourceGroupAsync(ClientImpl impl, PutBucketResourceGroupRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeBucketResourceGroup.fromPutBucketResourceGroup(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketResourceGroup::toPutBucketResourceGroup);
    }

}