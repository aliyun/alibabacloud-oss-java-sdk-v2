package com.aliyun.sdk.service.oss2.vectors.transform;


import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.vectors.models.*;
import com.aliyun.sdk.service.oss2.vectors.models.internal.GetVectorBucketResultJson;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorBucketsResultJson;

import java.util.Map;


public final class SerdeVectorBucketBasic {


    public static OperationInput fromPutVectorBucket(PutVectorBucketRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutVectorBucket")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutVectorBucketResult toPutVectorBucket(OperationOutput output) {
        Object innerBody = null;
        return PutVectorBucketResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromGetVectorBucket(GetVectorBucketRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetVectorBucket")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("bucketInfo", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetVectorBucketResult toGetVectorBucket(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetVectorBucketResultJson.class);

        return GetVectorBucketResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromDeleteVectorBucket(DeleteVectorBucketRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteVectorBucket")
                .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteVectorBucketResult toDeleteVectorBucket(OperationOutput output) {
        Object innerBody = null;
        return DeleteVectorBucketResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromListVectorBuckets(ListVectorBucketsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListVectorBuckets")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/octet-stream");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("vector", "");
        builder.parameters(parameters);


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListVectorBucketsResult toListVectorBuckets(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, ListVectorBucketsResultJson.class);

        return ListVectorBucketsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

}