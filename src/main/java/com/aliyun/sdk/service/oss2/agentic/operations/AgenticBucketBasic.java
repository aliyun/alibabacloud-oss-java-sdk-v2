package com.aliyun.sdk.service.oss2.agentic.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketBasic;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class AgenticBucketBasic {

    public static CreateAgenticBucketResult createAgenticBucket(ClientImpl impl, CreateAgenticBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketBasic.fromCreateAgenticBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketBasic.toCreateAgenticBucket(output);
    }

    public static CompletableFuture<CreateAgenticBucketResult> createAgenticBucketAsync(ClientImpl impl, CreateAgenticBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketBasic.fromCreateAgenticBucket(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketBasic::toCreateAgenticBucket);
    }

    public static DeleteAgenticBucketResult deleteAgenticBucket(ClientImpl impl, DeleteAgenticBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketBasic.fromDeleteAgenticBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketBasic.toDeleteAgenticBucket(output);
    }

    public static CompletableFuture<DeleteAgenticBucketResult> deleteAgenticBucketAsync(ClientImpl impl, DeleteAgenticBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketBasic.fromDeleteAgenticBucket(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketBasic::toDeleteAgenticBucket);
    }

    public static GetAgenticBucketResult getAgenticBucket(ClientImpl impl, GetAgenticBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketBasic.fromGetAgenticBucket(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketBasic.toGetAgenticBucket(output);
    }

    public static CompletableFuture<GetAgenticBucketResult> getAgenticBucketAsync(ClientImpl impl, GetAgenticBucketRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketBasic.fromGetAgenticBucket(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketBasic::toGetAgenticBucket);
    }

    public static ListAgenticBucketsResult listAgenticBuckets(ClientImpl impl, ListAgenticBucketsRequest request, OperationOptions options) {
        OperationInput input = SerdeAgenticBucketBasic.fromListAgenticBuckets(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketBasic.toListAgenticBuckets(output);
    }

    public static CompletableFuture<ListAgenticBucketsResult> listAgenticBucketsAsync(ClientImpl impl, ListAgenticBucketsRequest request, OperationOptions options) {
        OperationInput input = SerdeAgenticBucketBasic.fromListAgenticBuckets(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketBasic::toListAgenticBuckets);
    }

    public static PutAgenticBucketStatusResult putAgenticBucketStatus(ClientImpl impl, PutAgenticBucketStatusRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.agenticBucketStatus(), "request.agenticBucketStatus is required");
        requireNonNull(request.agenticBucketStatus().status(), "request.agenticBucketStatus.status is required");
        OperationInput input = SerdeAgenticBucketBasic.fromPutAgenticBucketStatus(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketBasic.toPutAgenticBucketStatus(output);
    }

    public static CompletableFuture<PutAgenticBucketStatusResult> putAgenticBucketStatusAsync(ClientImpl impl, PutAgenticBucketStatusRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.agenticBucketStatus(), "request.agenticBucketStatus is required");
        requireNonNull(request.agenticBucketStatus().status(), "request.agenticBucketStatus.status is required");
        OperationInput input = SerdeAgenticBucketBasic.fromPutAgenticBucketStatus(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketBasic::toPutAgenticBucketStatus);
    }

    public static ListBucketSpacesResult listBucketSpaces(ClientImpl impl, ListBucketSpacesRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketBasic.fromListBucketSpaces(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketBasic.toListBucketSpaces(output);
    }

    public static CompletableFuture<ListBucketSpacesResult> listBucketSpacesAsync(ClientImpl impl, ListBucketSpacesRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketBasic.fromListBucketSpaces(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketBasic::toListBucketSpaces);
    }
}
