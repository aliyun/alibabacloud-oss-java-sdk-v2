package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import java.util.Map;

public final class SerdeBucketPolicy {


    public static OperationInput fromPutBucketPolicy(PutBucketPolicyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("PutBucketPolicy")
            .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("policy", "");
        builder.parameters(parameters);

        
        // body
        builder.body(request.body());

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketPolicyResult toPutBucketPolicy(OperationOutput output) {
        Object innerBody = null;
        return PutBucketPolicyResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromGetBucketPolicy(GetBucketPolicyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketPolicy")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("policy", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketPolicyResult toGetBucketPolicy(OperationOutput output) {
        Object innerBody = null;
        innerBody = output.body;
         
        return GetBucketPolicyResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromDeleteBucketPolicy(DeleteBucketPolicyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("DeleteBucketPolicy")
            .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("policy", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteBucketPolicyResult toDeleteBucketPolicy(OperationOutput output) {
        Object innerBody = null;
        return DeleteBucketPolicyResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromGetBucketPolicyStatus(GetBucketPolicyStatusRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketPolicyStatus")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("policyStatus", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketPolicyStatusResult toGetBucketPolicyStatus(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, PolicyStatus.class);
         
        return GetBucketPolicyStatusResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }
}