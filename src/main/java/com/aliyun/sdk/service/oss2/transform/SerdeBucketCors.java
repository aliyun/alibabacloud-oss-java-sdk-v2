package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.AttributeMap;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Collections;
import java.util.Map;

import static com.aliyun.sdk.service.oss2.AttributeKey.SUBRESOURCE;
import static com.aliyun.sdk.service.oss2.transform.SerdeUtils.serializeInput;

final public class SerdeBucketCors {
    public static OperationInput fromPutBucketCors(PutBucketCorsRequest request) {
        // headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("cors", "");

        // body
        BinaryData body = null;
        body = SerdeUtils.serializeXmlBody(request.corsConfiguration());

        // opMetadata
        AttributeMap opMetadata = AttributeMap.empty();
        opMetadata.put(SUBRESOURCE, Collections.singletonList("cors"));

        OperationInput input = OperationInput.newBuilder()
                .opName("PutBucketCors")
                .bucket(request.bucket())
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .opMetadata(opMetadata)
                .body(body)
                .build();

        serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static PutBucketCorsResult toPutBucketCors(OperationOutput output) {
        return PutBucketCorsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetBucketCors(GetBucketCorsRequest request) {
        // headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("cors", "");

        // body
        BinaryData body = null;
        body = new StringBinaryData("");

        // opMetadata
        AttributeMap opMetadata = AttributeMap.empty();
        opMetadata.put(SUBRESOURCE, Collections.singletonList("cors"));

        OperationInput input = OperationInput.newBuilder()
                .opName("GetBucketCors")
                .method("GET")
                .headers(headers)
                .parameters(parameters)
                .opMetadata(opMetadata)
                .body(body)
                .bucket(request.bucket())
                .build();

        serializeInput(request, input, SerdeUtils.addContentMd5);

        return input;
    }

    public static GetBucketCorsResult toGetBucketCors(OperationOutput output) {
        return GetBucketCorsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(SerdeUtils.deserializeXmlBody(output, CORSConfiguration.class))
                .build();
    }

    public static OperationInput fromDeleteBucketCors(DeleteBucketCorsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteBucketCors")
                .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("cors", "");
        builder.parameters(parameters);

        // opMetadata
        AttributeMap opMetadata = AttributeMap.empty();
        opMetadata.put(SUBRESOURCE, Collections.singletonList("cors"));
        builder.opMetadata(opMetadata);


        builder.bucket(request.bucket());


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteBucketCorsResult toDeleteBucketCors(OperationOutput output) {
        Object innerBody = null;
        return DeleteBucketCorsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromOptionObject(OptionObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("OptionObject")
                .method("OPTIONS");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static OptionObjectResult toOptionObject(OperationOutput output) {
        Object innerBody = null;
        return OptionObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }
}