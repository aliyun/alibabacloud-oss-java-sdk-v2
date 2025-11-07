package com.aliyun.sdk.service.oss2.operations;


import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.exceptions.InconsistentException;
import com.aliyun.sdk.service.oss2.hash.CRC64Observer;
import com.aliyun.sdk.service.oss2.hash.CRC64ResponseChecker;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.io.StreamObserver;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressObserver;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;
import com.aliyun.sdk.service.oss2.types.FeatureFlagsType;
import com.aliyun.sdk.service.oss2.utils.MimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public final class ObjectBasic {


    public static PutObjectResult putObject(ClientImpl impl, PutObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromPutObject(request, impl.getFeatureFlags());
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toPutObject(output);
    }

    public static CompletableFuture<PutObjectResult> putObjectAsync(ClientImpl impl, PutObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput input = SerdeObjectBasic.fromPutObject(request, impl.getFeatureFlags());
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
        int featureFlags = impl.getFeatureFlags();

        OperationInput input = SerdeObjectBasic.fromAppendObject(request, featureFlags);

        // progress observer
        List<StreamObserver> streamObservers = null;
        if (request.progressListener() != null) {
            streamObservers = new ArrayList<>();
            streamObservers.add(new ProgressObserver(request.progressListener(), request.body().getLength()));
        }

        // crc observer
        // AppendObject is not idempotent, and cannot be retried
        CRC64Observer crcObserver = null;
        if (request.initHashCRC64() != null && FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD.isSet(featureFlags)) {
            if (streamObservers == null) {
                streamObservers = new ArrayList<>();
            }
            crcObserver = new CRC64Observer(request.initHashCRC64());
            streamObservers.add(crcObserver);
        }
        if (streamObservers != null) {
            input.opMetadata().put(AttributeKey.UPLOAD_OBSERVER, streamObservers);
        }

        OperationOutput output = impl.execute(input, options);
        checkResponseCrc(crcObserver, output.headers());
        return SerdeObjectBasic.toAppendObject(output);
    }

    public static CompletableFuture<AppendObjectResult> appendObjectAsync(ClientImpl impl, AppendObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.position(), "request.position is required");
        int featureFlags = impl.getFeatureFlags();

        OperationInput input = SerdeObjectBasic.fromAppendObject(request, featureFlags);

        // progress observer
        List<StreamObserver> streamObservers = null;
        if (request.progressListener() != null) {
            streamObservers = new ArrayList<>();
            streamObservers.add(new ProgressObserver(request.progressListener(), request.body().getLength()));
        }

        // crc observer
        // AppendObject is not idempotent, and cannot be retried
        CRC64Observer crcObserver = null;
        if (request.initHashCRC64() != null && FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD.isSet(featureFlags)) {
            if (streamObservers == null) {
                streamObservers = new ArrayList<>();
            }
            crcObserver = new CRC64Observer(request.initHashCRC64());
            streamObservers.add(crcObserver);
        }
        if (streamObservers != null) {
            input.opMetadata().put(AttributeKey.UPLOAD_OBSERVER, streamObservers);
        }
        final CRC64Observer crcChecker = crcObserver;
        return impl.executeAsync(input, options).thenApply(
                output -> {
                    checkResponseCrc(crcChecker, output.headers());
                    return SerdeObjectBasic.toAppendObject(output);
                });
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

        OperationInput input = SerdeObjectBasic.fromDeleteMultipleObjects(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toDeleteMultipleObjects(output);
    }

    public static CompletableFuture<DeleteMultipleObjectsResult> deleteMultipleObjectsAsync(ClientImpl impl, DeleteMultipleObjectsRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");

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

    private static void checkResponseCrc(CRC64Observer observer, Map<String, String> headers) {
        if (observer == null || headers == null) {
            return;
        }

        String serverCRC = headers.get("x-oss-hash-crc64ecma");
        if (serverCRC == null) {
            return;
        }

        String clientCRC = Long.toUnsignedString(observer.getChecksum().getValue());

        if (!serverCRC.equals(clientCRC)) {
            throw new InconsistentException(clientCRC, serverCRC, headers);
        }
    }

    public static SealAppendObjectResult sealAppendObject(ClientImpl impl, SealAppendObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.position(), "request.position is required");

        OperationInput input = SerdeObjectBasic.fromSealAppendObject(request);
        OperationOutput output = impl.execute(input, options);
        return SerdeObjectBasic.toSealAppendObject(output);
    }

    public static CompletableFuture<SealAppendObjectResult> sealAppendObjectAsync(ClientImpl impl, SealAppendObjectRequest request, OperationOptions options) {

        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.position(), "request.position is required");

        OperationInput input = SerdeObjectBasic.fromSealAppendObject(request);
        return impl.executeAsync(input, options).thenApply(SerdeObjectBasic::toSealAppendObject);
    }
}
