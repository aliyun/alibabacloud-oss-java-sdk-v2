package com.aliyun.sdk.service.oss2.agentic.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketAcl;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class AgenticBucketAcl {

    public static PutAgenticBucketAclResult putAgenticBucketAcl(ClientImpl impl, PutAgenticBucketAclRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.acl(), "request.acl is required");
        OperationInput input = SerdeAgenticBucketAcl.fromPutAgenticBucketAcl(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketAcl.toPutAgenticBucketAcl(output);
    }

    public static CompletableFuture<PutAgenticBucketAclResult> putAgenticBucketAclAsync(ClientImpl impl, PutAgenticBucketAclRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.acl(), "request.acl is required");
        OperationInput input = SerdeAgenticBucketAcl.fromPutAgenticBucketAcl(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketAcl::toPutAgenticBucketAcl);
    }

    public static GetAgenticBucketAclResult getAgenticBucketAcl(ClientImpl impl, GetAgenticBucketAclRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketAcl.fromGetAgenticBucketAcl(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketAcl.toGetAgenticBucketAcl(output);
    }

    public static CompletableFuture<GetAgenticBucketAclResult> getAgenticBucketAclAsync(ClientImpl impl, GetAgenticBucketAclRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketAcl.fromGetAgenticBucketAcl(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketAcl::toGetAgenticBucketAcl);
    }
}
