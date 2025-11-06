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

public final class SerdeBucketEncryption {


    public static OperationInput fromPutBucketEncryption(PutBucketEncryptionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("PutBucketEncryption")
            .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("encryption", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.serverSideEncryptionRule());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketEncryptionResult toPutBucketEncryption(OperationOutput output) {
        Object innerBody = null;
        return PutBucketEncryptionResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromGetBucketEncryption(GetBucketEncryptionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketEncryption")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("encryption", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketEncryptionResult toGetBucketEncryption(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, ServerSideEncryptionRule.class);
         
        return GetBucketEncryptionResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromDeleteBucketEncryption(DeleteBucketEncryptionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("DeleteBucketEncryption")
            .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);
         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("encryption", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteBucketEncryptionResult toDeleteBucketEncryption(OperationOutput output) {
        Object innerBody = null;
        return DeleteBucketEncryptionResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }
}