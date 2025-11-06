package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.AttributeMap;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import java.util.Arrays;
import java.util.Map;
import static com.aliyun.sdk.service.oss2.AttributeKey.SUBRESOURCE;

public final class SerdeBucketRedundancyTransition {


    public static OperationInput fromListBucketDataRedundancyTransition(ListBucketDataRedundancyTransitionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("ListBucketDataRedundancyTransition")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("redundancyTransition", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListBucketDataRedundancyTransitionResult toListBucketDataRedundancyTransition(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, ListBucketDataRedundancyTransition.class);
         
        return ListBucketDataRedundancyTransitionResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromListUserDataRedundancyTransition(ListUserDataRedundancyTransitionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("ListUserDataRedundancyTransition")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("redundancyTransition", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListUserDataRedundancyTransitionResult toListUserDataRedundancyTransition(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, ListBucketDataRedundancyTransition.class);
         
        return ListUserDataRedundancyTransitionResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromGetBucketDataRedundancyTransition(GetBucketDataRedundancyTransitionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketDataRedundancyTransition")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("redundancyTransition", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketDataRedundancyTransitionResult toGetBucketDataRedundancyTransition(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, BucketDataRedundancyTransition.class);
         
        return GetBucketDataRedundancyTransitionResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromCreateBucketDataRedundancyTransition(CreateBucketDataRedundancyTransitionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("CreateBucketDataRedundancyTransition")
            .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("redundancyTransition", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static CreateBucketDataRedundancyTransitionResult toCreateBucketDataRedundancyTransition(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, BucketDataRedundancyTransition.class);
         
        return CreateBucketDataRedundancyTransitionResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromDeleteBucketDataRedundancyTransition(DeleteBucketDataRedundancyTransitionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("DeleteBucketDataRedundancyTransition")
            .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("redundancyTransition", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteBucketDataRedundancyTransitionResult toDeleteBucketDataRedundancyTransition(OperationOutput output) {
        Object innerBody = null;
        return DeleteBucketDataRedundancyTransitionResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }
}