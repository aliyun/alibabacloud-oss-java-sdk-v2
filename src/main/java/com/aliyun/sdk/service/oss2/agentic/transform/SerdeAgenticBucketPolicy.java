package com.aliyun.sdk.service.oss2.agentic.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeAgenticBucketPolicy {

    public static OperationInput fromPutAgenticBucketPolicy(PutAgenticBucketPolicyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutAgenticBucketPolicy")
                .method("PUT");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("policy", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        builder.body(request.policy() != null ? new StringBinaryData(request.policy()) : new StringBinaryData(""));

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutAgenticBucketPolicyResult toPutAgenticBucketPolicy(OperationOutput output) {
        return PutAgenticBucketPolicyResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetAgenticBucketPolicy(GetAgenticBucketPolicyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetAgenticBucketPolicy")
                .method("GET");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("policy", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetAgenticBucketPolicyResult toGetAgenticBucketPolicy(OperationOutput output) {
        Object innerBody = output.body != null ? output.body.toString() : null;
        return GetAgenticBucketPolicyResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromDeleteAgenticBucketPolicy(DeleteAgenticBucketPolicyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteAgenticBucketPolicy")
                .method("DELETE");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("policy", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteAgenticBucketPolicyResult toDeleteAgenticBucketPolicy(OperationOutput output) {
        return DeleteAgenticBucketPolicyResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }
}
