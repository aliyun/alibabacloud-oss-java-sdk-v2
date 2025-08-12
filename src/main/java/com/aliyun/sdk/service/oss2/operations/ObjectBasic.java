package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.AttributeKey;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.types.FeatureFlagsType;
import com.aliyun.sdk.service.oss2.utils.MimeUtils;

import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

public final class ObjectBasic {


    public static PutObjectResult putObject(ClientImpl impl, PutObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromPutObject(request);
        if (FeatureFlagsType.AUTO_DETECT_MIMETYPE.isSet(impl.getFeatureFlags())) {
            addContentType(input);
        }
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toPutObject(output);
    }

    public static CompletableFuture<PutObjectResult> putObjectAsync(ClientImpl impl, PutObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromPutObject(request);
        if (FeatureFlagsType.AUTO_DETECT_MIMETYPE.isSet(impl.getFeatureFlags())) {
            addContentType(input);
        }
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toPutObject);
    }


    public static CopyObjectResult copyObject(ClientImpl impl, CopyObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.sourceKey(), "request.sourceKey is required");

        OperationInput input = SerdeObjectBasic.fromCopyObject(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toCopyObject(output);
    }

    public static CompletableFuture<CopyObjectResult> copyObjectAsync(ClientImpl impl, CopyObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.sourceKey(), "request.sourceKey is required");

        OperationInput input = SerdeObjectBasic.fromCopyObject(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toCopyObject);
    }


    public static GetObjectResult getObject(ClientImpl impl, GetObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromGetObject(request);
        input.opMetadata().put(AttributeKey.RESPONSE_HEADERS_READ, Boolean.TRUE);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toGetObject(output);
    }

    public static CompletableFuture<GetObjectResult> getObjectAsync(ClientImpl impl, GetObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromGetObject(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toGetObject);
    }

    public static AppendObjectResult appendObject(ClientImpl impl, AppendObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.position(), "request.position is required");

        OperationInput input = SerdeObjectBasic.fromAppendObject(request);
        if (FeatureFlagsType.AUTO_DETECT_MIMETYPE.isSet(impl.getFeatureFlags())) {
            addContentType(input);
        }
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toAppendObject(output);
    }

    public static CompletableFuture<AppendObjectResult> appendObjectAsync(ClientImpl impl, AppendObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.position(), "request.position is required");

        OperationInput input = SerdeObjectBasic.fromAppendObject(request);
        if (FeatureFlagsType.AUTO_DETECT_MIMETYPE.isSet(impl.getFeatureFlags())) {
            addContentType(input);
        }
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toAppendObject);
    }


    public static DeleteObjectResult deleteObject(ClientImpl impl, DeleteObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromDeleteObject(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toDeleteObject(output);
    }

    public static CompletableFuture<DeleteObjectResult> deleteObjectAsync(ClientImpl impl, DeleteObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromDeleteObject(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toDeleteObject);
    }

    public static DeleteMultipleObjectsResult deleteMultipleObjects(ClientImpl impl, DeleteMultipleObjectsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.deleteObjects(), "request.deleteObjects is required");

        OperationInput input = SerdeObjectBasic.fromDeleteMultipleObjects(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toDeleteMultipleObjects(output);
    }

    public static CompletableFuture<DeleteMultipleObjectsResult> deleteMultipleObjectsAsync(ClientImpl impl, DeleteMultipleObjectsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.deleteObjects(), "request.deleteObjects is required");

        OperationInput input = SerdeObjectBasic.fromDeleteMultipleObjects(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toDeleteMultipleObjects);
    }


    public static HeadObjectResult headObject(ClientImpl impl, HeadObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromHeadObject(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toHeadObject(output);
    }

    public static CompletableFuture<HeadObjectResult> headObjectAsync(ClientImpl impl, HeadObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromHeadObject(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toHeadObject);
    }


    public static GetObjectMetaResult getObjectMeta(ClientImpl impl, GetObjectMetaRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromGetObjectMeta(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toGetObjectMeta(output);
    }

    public static CompletableFuture<GetObjectMetaResult> getObjectMetaAsync(ClientImpl impl, GetObjectMetaRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromGetObjectMeta(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toGetObjectMeta);
    }


    public static RestoreObjectResult restoreObject(ClientImpl impl, RestoreObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromRestoreObject(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toRestoreObject(output);
    }

    public static CompletableFuture<RestoreObjectResult> restoreObjectAsync(ClientImpl impl, RestoreObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromRestoreObject(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toRestoreObject);
    }


    public static CleanRestoredObjectResult cleanRestoredObject(ClientImpl impl, CleanRestoredObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromCleanRestoredObject(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toCleanRestoredObject(output);
    }

    public static CompletableFuture<CleanRestoredObjectResult> cleanRestoredObjectAsync(ClientImpl impl, CleanRestoredObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromCleanRestoredObject(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toCleanRestoredObject);
    }

    static void addContentType(OperationInput input) {
        if (input.headers().containsKey("Content-Type")) {
            return;
        }
        String value = MimeUtils.getMimetype(input.key().orElse(null), MimeUtils.DEFAULT_MIMETYPE);
        input.headers().put("Content-Type",value);
    }
}