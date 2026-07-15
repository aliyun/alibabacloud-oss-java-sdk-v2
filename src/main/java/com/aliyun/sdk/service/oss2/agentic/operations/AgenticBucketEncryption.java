package com.aliyun.sdk.service.oss2.agentic.operations;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.agentic.transform.SerdeAgenticBucketEncryption;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;

import java.util.concurrent.CompletableFuture;
import static java.util.Objects.requireNonNull;

public final class AgenticBucketEncryption {

    public static PutAgenticBucketEncryptionResult putAgenticBucketEncryption(ClientImpl impl, PutAgenticBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketEncryption.fromPutAgenticBucketEncryption(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketEncryption.toPutAgenticBucketEncryption(output);
    }

    public static CompletableFuture<PutAgenticBucketEncryptionResult> putAgenticBucketEncryptionAsync(ClientImpl impl, PutAgenticBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketEncryption.fromPutAgenticBucketEncryption(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketEncryption::toPutAgenticBucketEncryption);
    }

    public static GetAgenticBucketEncryptionResult getAgenticBucketEncryption(ClientImpl impl, GetAgenticBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketEncryption.fromGetAgenticBucketEncryption(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketEncryption.toGetAgenticBucketEncryption(output);
    }

    public static CompletableFuture<GetAgenticBucketEncryptionResult> getAgenticBucketEncryptionAsync(ClientImpl impl, GetAgenticBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketEncryption.fromGetAgenticBucketEncryption(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketEncryption::toGetAgenticBucketEncryption);
    }

    public static DeleteAgenticBucketEncryptionResult deleteAgenticBucketEncryption(ClientImpl impl, DeleteAgenticBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketEncryption.fromDeleteAgenticBucketEncryption(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeAgenticBucketEncryption.toDeleteAgenticBucketEncryption(output);
    }

    public static CompletableFuture<DeleteAgenticBucketEncryptionResult> deleteAgenticBucketEncryptionAsync(ClientImpl impl, DeleteAgenticBucketEncryptionRequest request, OperationOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        OperationInput input = SerdeAgenticBucketEncryption.fromDeleteAgenticBucketEncryption(request);
        return impl.executeAsync(input, options).thenApply(SerdeAgenticBucketEncryption::toDeleteAgenticBucketEncryption);
    }
}
