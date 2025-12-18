package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.AttributeMap;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Collections;
import java.util.Map;

import static com.aliyun.sdk.service.oss2.AttributeKey.SUBRESOURCE;

public final class SerdeBucketOverwriteConfig {


    public static OperationInput fromPutBucketOverwriteConfig(PutBucketOverwriteConfigRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutBucketOverwriteConfig")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("overwriteConfig", "");
        builder.parameters(parameters);


        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.overwriteConfiguration());
        builder.body(body);


        // opMetadata
        AttributeMap opMetadata = AttributeMap.empty();
        opMetadata.put(SUBRESOURCE, Collections.singletonList("overwriteConfig"));
        builder.opMetadata(opMetadata);


        builder.bucket(request.bucket());


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketOverwriteConfigResult toPutBucketOverwriteConfig(OperationOutput output) {
        Object innerBody = null;
        return PutBucketOverwriteConfigResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromGetBucketOverwriteConfig(GetBucketOverwriteConfigRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetBucketOverwriteConfig")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("overwriteConfig", "");
        builder.parameters(parameters);


        // opMetadata
        AttributeMap opMetadata = AttributeMap.empty();
        opMetadata.put(SUBRESOURCE, Collections.singletonList("overwriteConfig"));
        builder.opMetadata(opMetadata);


        builder.bucket(request.bucket());


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketOverwriteConfigResult toGetBucketOverwriteConfig(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, OverwriteConfiguration.class);

        return GetBucketOverwriteConfigResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromDeleteBucketOverwriteConfig(DeleteBucketOverwriteConfigRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteBucketOverwriteConfig")
                .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("overwriteConfig", "");
        builder.parameters(parameters);


        // opMetadata
        AttributeMap opMetadata = AttributeMap.empty();
        opMetadata.put(SUBRESOURCE, Collections.singletonList("overwriteConfig"));
        builder.opMetadata(opMetadata);


        builder.bucket(request.bucket());


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteBucketOverwriteConfigResult toDeleteBucketOverwriteConfig(OperationOutput output) {
        Object innerBody = null;
        return DeleteBucketOverwriteConfigResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


}