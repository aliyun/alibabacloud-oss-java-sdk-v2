package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeObjectTagging {


    public static OperationInput fromPutObjectTagging(PutObjectTaggingRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutObjectTagging")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("tagging", "");
        builder.parameters(parameters);


        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.tagging());
        builder.body(body);


        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutObjectTaggingResult toPutObjectTagging(OperationOutput output) {
        Object innerBody = null;
        return PutObjectTaggingResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromGetObjectTagging(GetObjectTaggingRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetObjectTagging")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("tagging", "");
        builder.parameters(parameters);


        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetObjectTaggingResult toGetObjectTagging(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, Tagging.class);

        return GetObjectTaggingResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromDeleteObjectTagging(DeleteObjectTaggingRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteObjectTagging")
                .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("tagging", "");
        builder.parameters(parameters);


        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteObjectTaggingResult toDeleteObjectTagging(OperationOutput output) {
        Object innerBody = null;
        return DeleteObjectTaggingResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


}