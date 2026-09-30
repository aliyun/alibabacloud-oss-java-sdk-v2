package com.aliyun.sdk.service.oss2.agentic.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketStorageQuota;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class AgenticBucketStorageQuota {

    public static PutAgenticBucketStorageQuotaResult putAgenticBucketStorageQuota(ClientImpl impl, PutAgenticBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.quotaConfiguration(), "request.quotaConfiguration is required");
        OperationInput input = SerdeAgenticBucketStorageQuota.fromPutAgenticBucketStorageQuota(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketStorageQuota.toPutAgenticBucketStorageQuota(output);
    }

    public static CompletableFuture<PutAgenticBucketStorageQuotaResult> putAgenticBucketStorageQuotaAsync(ClientImpl impl, PutAgenticBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.quotaConfiguration(), "request.quotaConfiguration is required");
        OperationInput input = SerdeAgenticBucketStorageQuota.fromPutAgenticBucketStorageQuota(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketStorageQuota::toPutAgenticBucketStorageQuota);
    }

    public static GetAgenticBucketStorageQuotaResult getAgenticBucketStorageQuota(ClientImpl impl, GetAgenticBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketStorageQuota.fromGetAgenticBucketStorageQuota(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketStorageQuota.toGetAgenticBucketStorageQuota(output);
    }

    public static CompletableFuture<GetAgenticBucketStorageQuotaResult> getAgenticBucketStorageQuotaAsync(ClientImpl impl, GetAgenticBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketStorageQuota.fromGetAgenticBucketStorageQuota(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketStorageQuota::toGetAgenticBucketStorageQuota);
    }

    public static DeleteAgenticBucketStorageQuotaResult deleteAgenticBucketStorageQuota(ClientImpl impl, DeleteAgenticBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketStorageQuota.fromDeleteAgenticBucketStorageQuota(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketStorageQuota.toDeleteAgenticBucketStorageQuota(output);
    }

    public static CompletableFuture<DeleteAgenticBucketStorageQuotaResult> deleteAgenticBucketStorageQuotaAsync(ClientImpl impl, DeleteAgenticBucketStorageQuotaRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketStorageQuota.fromDeleteAgenticBucketStorageQuota(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketStorageQuota::toDeleteAgenticBucketStorageQuota);
    }
}
