package com.aliyun.sdk.service.oss2.agentic.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketVersioning;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class AgenticBucketVersioning {

    public static PutAgenticBucketVersioningResult putAgenticBucketVersioning(ClientImpl impl, PutAgenticBucketVersioningRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketVersioning.fromPutAgenticBucketVersioning(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketVersioning.toPutAgenticBucketVersioning(output);
    }

    public static CompletableFuture<PutAgenticBucketVersioningResult> putAgenticBucketVersioningAsync(ClientImpl impl, PutAgenticBucketVersioningRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketVersioning.fromPutAgenticBucketVersioning(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketVersioning::toPutAgenticBucketVersioning);
    }

    public static GetAgenticBucketVersioningResult getAgenticBucketVersioning(ClientImpl impl, GetAgenticBucketVersioningRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketVersioning.fromGetAgenticBucketVersioning(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketVersioning.toGetAgenticBucketVersioning(output);
    }

    public static CompletableFuture<GetAgenticBucketVersioningResult> getAgenticBucketVersioningAsync(ClientImpl impl, GetAgenticBucketVersioningRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketVersioning.fromGetAgenticBucketVersioning(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketVersioning::toGetAgenticBucketVersioning);
    }
}
