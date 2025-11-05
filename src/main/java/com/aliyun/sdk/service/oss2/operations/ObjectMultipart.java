package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.types.FeatureFlagsType;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class ObjectMultipart {


    public static InitiateMultipartUploadResult initiateMultipartUpload(ClientImpl impl, InitiateMultipartUploadRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectMultipart.fromInitiateMultipartUpload(request, impl.getFeatureFlags());
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectMultipart.toInitiateMultipartUpload(output);
    }

    public static CompletableFuture<InitiateMultipartUploadResult> initiateMultipartUploadAsync(ClientImpl impl, InitiateMultipartUploadRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectMultipart.fromInitiateMultipartUpload(request, impl.getFeatureFlags());
        return impl.executeAsync(input, options).thenApply(SerdeObjectMultipart::toInitiateMultipartUpload);
    }


    public static UploadPartResult uploadPart(ClientImpl impl, UploadPartRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");
        requireNonNull(request.partNumber(), "request.partNumber is required");

        OperationInput input = SerdeObjectMultipart.fromUploadPart(request, impl.getFeatureFlags());
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectMultipart.toUploadPart(output);
    }

    public static CompletableFuture<UploadPartResult> uploadPartAsync(ClientImpl impl, UploadPartRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");
        requireNonNull(request.partNumber(), "request.partNumber is required");

        OperationInput input = SerdeObjectMultipart.fromUploadPart(request, impl.getFeatureFlags());
        return impl.executeAsync(input, options).thenApply(SerdeObjectMultipart::toUploadPart);
    }


    public static CompleteMultipartUploadResult completeMultipartUpload(ClientImpl impl, CompleteMultipartUploadRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");

        OperationInput input = SerdeObjectMultipart.fromCompleteMultipartUpload(request);
        OperationOutput output = impl.execute(input, options);
        boolean callback = !StringUtils.isNullOrEmpty(request.callback());
        return SerdeObjectMultipart.toCompleteMultipartUpload(output, callback);
    }

    public static CompletableFuture<CompleteMultipartUploadResult> completeMultipartUploadAsync(ClientImpl impl, CompleteMultipartUploadRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");

        OperationInput input = SerdeObjectMultipart.fromCompleteMultipartUpload(request);
        final boolean callback = !StringUtils.isNullOrEmpty(request.callback());
        return impl.executeAsync(input, options).thenApply(x -> SerdeObjectMultipart.toCompleteMultipartUpload(x, callback));
    }


    public static UploadPartCopyResult uploadPartCopy(ClientImpl impl, UploadPartCopyRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");
        requireNonNull(request.partNumber(), "request.partNumber is required");

        OperationInput input = SerdeObjectMultipart.fromUploadPartCopy(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectMultipart.toUploadPartCopy(output);
    }

    public static CompletableFuture<UploadPartCopyResult> uploadPartCopyAsync(ClientImpl impl, UploadPartCopyRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");
        requireNonNull(request.partNumber(), "request.partNumber is required");

        OperationInput input = SerdeObjectMultipart.fromUploadPartCopy(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectMultipart::toUploadPartCopy);
    }


    public static AbortMultipartUploadResult abortMultipartUpload(ClientImpl impl, AbortMultipartUploadRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");

        OperationInput input = SerdeObjectMultipart.fromAbortMultipartUpload(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectMultipart.toAbortMultipartUpload(output);
    }

    public static CompletableFuture<AbortMultipartUploadResult> abortMultipartUploadAsync(ClientImpl impl, AbortMultipartUploadRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");

        OperationInput input = SerdeObjectMultipart.fromAbortMultipartUpload(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectMultipart::toAbortMultipartUpload);
    }


    public static ListMultipartUploadsResult listMultipartUploads(ClientImpl impl, ListMultipartUploadsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeObjectMultipart.fromListMultipartUploads(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectMultipart.toListMultipartUploads(output);
    }

    public static CompletableFuture<ListMultipartUploadsResult> listMultipartUploadsAsync(ClientImpl impl, ListMultipartUploadsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

        OperationInput input = SerdeObjectMultipart.fromListMultipartUploads(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectMultipart::toListMultipartUploads);
    }


    public static ListPartsResult listParts(ClientImpl impl, ListPartsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");

        OperationInput input = SerdeObjectMultipart.fromListParts(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectMultipart.toListParts(output);
    }

    public static CompletableFuture<ListPartsResult> listPartsAsync(ClientImpl impl, ListPartsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");

        OperationInput input = SerdeObjectMultipart.fromListParts(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectMultipart::toListParts);
    }

}