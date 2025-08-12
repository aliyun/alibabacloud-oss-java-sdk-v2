package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;


public final class SerdeBucketAcl {


    public static OperationInput fromPutBucketAcl(PutBucketAclRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("PutBucketAcl")
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
        

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketAclResult toPutBucketAcl(OperationOutput output) {
        Object innerBody = null;
        return PutBucketAclResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromGetBucketAcl(GetBucketAclRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketAcl")
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
        

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketAclResult toGetBucketAcl(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, AccessControlPolicy.class);
         
        return GetBucketAclResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


}