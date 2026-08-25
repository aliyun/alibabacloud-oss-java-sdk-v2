package com.aliyun.sdk.service.oss2.agentic.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketPublicAccessBlock;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class AgenticBucketPublicAccessBlock {

    public static PutAgenticBucketPublicAccessBlockResult putAgenticBucketPublicAccessBlock(ClientImpl impl, PutAgenticBucketPublicAccessBlockRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPublicAccessBlock.fromPutAgenticBucketPublicAccessBlock(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketPublicAccessBlock.toPutAgenticBucketPublicAccessBlock(output);
    }

    public static CompletableFuture<PutAgenticBucketPublicAccessBlockResult> putAgenticBucketPublicAccessBlockAsync(ClientImpl impl, PutAgenticBucketPublicAccessBlockRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPublicAccessBlock.fromPutAgenticBucketPublicAccessBlock(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketPublicAccessBlock::toPutAgenticBucketPublicAccessBlock);
    }

    public static GetAgenticBucketPublicAccessBlockResult getAgenticBucketPublicAccessBlock(ClientImpl impl, GetAgenticBucketPublicAccessBlockRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPublicAccessBlock.fromGetAgenticBucketPublicAccessBlock(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketPublicAccessBlock.toGetAgenticBucketPublicAccessBlock(output);
    }

    public static CompletableFuture<GetAgenticBucketPublicAccessBlockResult> getAgenticBucketPublicAccessBlockAsync(ClientImpl impl, GetAgenticBucketPublicAccessBlockRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPublicAccessBlock.fromGetAgenticBucketPublicAccessBlock(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketPublicAccessBlock::toGetAgenticBucketPublicAccessBlock);
    }

    public static DeleteAgenticBucketPublicAccessBlockResult deleteAgenticBucketPublicAccessBlock(ClientImpl impl, DeleteAgenticBucketPublicAccessBlockRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPublicAccessBlock.fromDeleteAgenticBucketPublicAccessBlock(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketPublicAccessBlock.toDeleteAgenticBucketPublicAccessBlock(output);
    }

    public static CompletableFuture<DeleteAgenticBucketPublicAccessBlockResult> deleteAgenticBucketPublicAccessBlockAsync(ClientImpl impl, DeleteAgenticBucketPublicAccessBlockRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPublicAccessBlock.fromDeleteAgenticBucketPublicAccessBlock(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketPublicAccessBlock::toDeleteAgenticBucketPublicAccessBlock);
    }
}
