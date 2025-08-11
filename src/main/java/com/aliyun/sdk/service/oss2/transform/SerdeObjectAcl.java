package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeObjectAcl {


    public static OperationInput fromPutObjectAcl(PutObjectAclRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutObjectAcl")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("acl", "");
        builder.parameters(parameters);


        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutObjectAclResult toPutObjectAcl(OperationOutput output) {
        Object innerBody = null;
        return PutObjectAclResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromGetObjectAcl(GetObjectAclRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetObjectAcl")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("acl", "");
        builder.parameters(parameters);


        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetObjectAclResult toGetObjectAcl(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, AccessControlPolicy.class);

        return GetObjectAclResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


}