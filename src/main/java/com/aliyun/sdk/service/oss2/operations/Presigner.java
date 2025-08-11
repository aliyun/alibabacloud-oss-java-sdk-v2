package com.aliyun.sdk.service.oss2.operations;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.internal.ClientImpl;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressObserver;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectMultipart;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.types.AuthMethodType;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Collections;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class Presigner {
    private final static OperationOptions defaultPresignOpOpt = OperationOptions.newBuilder()
            .authMethod(AuthMethodType.Query).build();

    private static PresignResult doPresign(ClientImpl impl, OperationInput input, PresignOptions options) {
        if (options != null && options.expiration().isPresent()) {
            input.opMetadata().put(AttributeKey.EXPIRATION_TIME, options.expiration().get());
        }

        ClientImpl.PresignInnerResult result = impl.presignInner(input, defaultPresignOpOpt);
        return PresignResult.newBuilder()
                .url(result.url)
                .method(result.method)
                .expiration(result.expiration)
                .signedHeaders(result.signedHeaders)
                .build();
    }

    public static PresignResult putObject(ClientImpl impl, PutObjectRequest request, PresignOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        OperationInput input = OperationInput.newBuilder()
                .opName("PutObject")
                .method("PUT")
                .bucket(request.bucket())
                .key(request.key())
                .build();
        SerdeUtils.serializeInput(request, input);
        return doPresign(impl, input, options);
    }

    public static PresignResult getObject(ClientImpl impl, GetObjectRequest request, PresignOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        OperationInput input = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET")
                .bucket(request.bucket())
                .key(request.key())
                .build();

        SerdeUtils.serializeInput(request, input);
        return doPresign(impl, input, options);
    }

    public static PresignResult headObject(ClientImpl impl, HeadObjectRequest request, PresignOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        OperationInput input = OperationInput.newBuilder()
                .opName("HeadObject")
                .method("HEAD")
                .bucket(request.bucket())
                .key(request.key())
                .build();

        SerdeUtils.serializeInput(request, input);
        return doPresign(impl, input, options);
    }

    public static PresignResult initiateMultipartUpload(ClientImpl impl, InitiateMultipartUploadRequest request, PresignOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");

        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("InitiateMultipartUpload")
                .method("POST")
                .bucket(request.bucket())
                .key(request.key());

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("uploads", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input);

        return doPresign(impl, input, options);
    }

    public static PresignResult uploadPart(ClientImpl impl, UploadPartRequest request, PresignOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");
        requireNonNull(request.partNumber(), "request.partNumber is required");

        OperationInput input = OperationInput.newBuilder()
                .opName("UploadPart")
                .method("PUT")
                .bucket(request.bucket())
                .key(request.key())
                .build();

        SerdeUtils.serializeInput(request, input);
        return doPresign(impl, input, options);
    }

    public static PresignResult completeMultipartUpload(ClientImpl impl, CompleteMultipartUploadRequest request, PresignOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");

        OperationInput input = OperationInput.newBuilder()
                .opName("CompleteMultipartUpload")
                .method("POST")
                .bucket(request.bucket())
                .key(request.key())
                .build();

        SerdeUtils.serializeInput(request, input);
        return doPresign(impl, input, options);
    }

    public static PresignResult abortMultipartUpload(ClientImpl impl, AbortMultipartUploadRequest request, PresignOptions options) {
        requireNonNull(request.bucket(), "request.bucket is required");
        requireNonNull(request.key(), "request.key is required");
        requireNonNull(request.uploadId(), "request.uploadId is required");

        OperationInput input = OperationInput.newBuilder()
                .opName("AbortMultipartUpload")
                .method("DELETE")
                .bucket(request.bucket())
                .key(request.key())
                .build();

        SerdeUtils.serializeInput(request, input);
        return doPresign(impl, input, options);
    }
}
