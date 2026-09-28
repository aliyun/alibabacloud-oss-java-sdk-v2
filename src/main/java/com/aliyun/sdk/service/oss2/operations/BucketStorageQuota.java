package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.*;

import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class BucketStorageQuota {

    public static PutBucketStorageQuotaResult putBucketStorageQuota(ClientImpl impl, PutBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.bucketStorageQuotaConfiguration(), "request.bucketStorageQuotaConfiguration is required");
        OperationInput input = SerdeBucketStorageQuota.fromPutBucketStorageQuota(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketStorageQuota.toPutBucketStorageQuota(output);
    }

    public static CompletableFuture<PutBucketStorageQuotaResult> putBucketStorageQuotaAsync(ClientImpl impl, PutBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.bucketStorageQuotaConfiguration(), "request.bucketStorageQuotaConfiguration is required");
        OperationInput input = SerdeBucketStorageQuota.fromPutBucketStorageQuota(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketStorageQuota::toPutBucketStorageQuota);
    }

    public static GetBucketStorageQuotaResult getBucketStorageQuota(ClientImpl impl, GetBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeBucketStorageQuota.fromGetBucketStorageQuota(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketStorageQuota.toGetBucketStorageQuota(output);
    }

    public static CompletableFuture<GetBucketStorageQuotaResult> getBucketStorageQuotaAsync(ClientImpl impl, GetBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeBucketStorageQuota.fromGetBucketStorageQuota(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketStorageQuota::toGetBucketStorageQuota);
    }

    public static DeleteBucketStorageQuotaResult deleteBucketStorageQuota(ClientImpl impl, DeleteBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeBucketStorageQuota.fromDeleteBucketStorageQuota(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeBucketStorageQuota.toDeleteBucketStorageQuota(output);
    }

    public static CompletableFuture<DeleteBucketStorageQuotaResult> deleteBucketStorageQuotaAsync(ClientImpl impl, DeleteBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeBucketStorageQuota.fromDeleteBucketStorageQuota(request);
        return impl.executeAsync(input, options).thenApply(SerdeBucketStorageQuota::toDeleteBucketStorageQuota);
    }
}
