package com.aliyun.sdk.service.oss2.agentic.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketPolicy;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class AgenticBucketPolicy {

    public static PutAgenticBucketPolicyResult putAgenticBucketPolicy(ClientImpl impl, PutAgenticBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPolicy.fromPutAgenticBucketPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketPolicy.toPutAgenticBucketPolicy(output);
    }

    public static CompletableFuture<PutAgenticBucketPolicyResult> putAgenticBucketPolicyAsync(ClientImpl impl, PutAgenticBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPolicy.fromPutAgenticBucketPolicy(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketPolicy::toPutAgenticBucketPolicy);
    }

    public static GetAgenticBucketPolicyResult getAgenticBucketPolicy(ClientImpl impl, GetAgenticBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPolicy.fromGetAgenticBucketPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketPolicy.toGetAgenticBucketPolicy(output);
    }

    public static CompletableFuture<GetAgenticBucketPolicyResult> getAgenticBucketPolicyAsync(ClientImpl impl, GetAgenticBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPolicy.fromGetAgenticBucketPolicy(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketPolicy::toGetAgenticBucketPolicy);
    }

    public static DeleteAgenticBucketPolicyResult deleteAgenticBucketPolicy(ClientImpl impl, DeleteAgenticBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPolicy.fromDeleteAgenticBucketPolicy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketPolicy.toDeleteAgenticBucketPolicy(output);
    }

    public static CompletableFuture<DeleteAgenticBucketPolicyResult> deleteAgenticBucketPolicyAsync(ClientImpl impl, DeleteAgenticBucketPolicyRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketPolicy.fromDeleteAgenticBucketPolicy(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketPolicy::toDeleteAgenticBucketPolicy);
    }
}
