package com.aliyun.sdk.service.oss2.agentic.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.models.AccessControlPolicy;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeAgenticBucketAcl {

    public static OperationInput fromPutAgenticBucketAcl(PutAgenticBucketAclRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutAgenticBucketAcl")
                .method("PUT");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("acl", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutAgenticBucketAclResult toPutAgenticBucketAcl(OperationOutput output) {
        return PutAgenticBucketAclResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetAgenticBucketAcl(GetAgenticBucketAclRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetAgenticBucketAcl")
                .method("GET");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("acl", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetAgenticBucketAclResult toGetAgenticBucketAcl(OperationOutput output) {
        Object innerBody = SerdeUtils.deserializeXmlBody(output, AccessControlPolicy.class);
        return GetAgenticBucketAclResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }
}
