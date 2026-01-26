package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.AttributeMap;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import java.util.Arrays;
import java.util.Map;
import static com.aliyun.sdk.service.oss2.AttributeKey.SUBRESOURCE;

public final class SerdeBucketWorm {


    public static OperationInput fromInitiateBucketWorm(InitiateBucketWormRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("InitiateBucketWorm")
            .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("worm", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.initiateWormConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static InitiateBucketWormResult toInitiateBucketWorm(OperationOutput output) {
        Object innerBody = null; 
        return InitiateBucketWormResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromAbortBucketWorm(AbortBucketWormRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("AbortBucketWorm")
            .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("worm", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static AbortBucketWormResult toAbortBucketWorm(OperationOutput output) {
        Object innerBody = null;
        return AbortBucketWormResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromCompleteBucketWorm(CompleteBucketWormRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("CompleteBucketWorm")
            .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static CompleteBucketWormResult toCompleteBucketWorm(OperationOutput output) {
        Object innerBody = null;
        return CompleteBucketWormResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromExtendBucketWorm(ExtendBucketWormRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("ExtendBucketWorm")
            .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("wormExtend", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.extendWormConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ExtendBucketWormResult toExtendBucketWorm(OperationOutput output) {
        Object innerBody = null;
        return ExtendBucketWormResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromGetBucketWorm(GetBucketWormRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketWorm")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("worm", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketWormResult toGetBucketWorm(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, WormConfiguration.class);
         
        return GetBucketWormResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }
}