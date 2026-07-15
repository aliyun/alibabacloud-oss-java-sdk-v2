package com.aliyun.sdk.service.oss2.agentic.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeAgenticBucketBasic {

    public static OperationInput fromCreateAgenticBucket(CreateAgenticBucketRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("CreateAgenticBucket")
                .method("PUT");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        BinaryData body = SerdeUtils.serializeXmlBody(request.createAgenticBucketConfiguration());
        builder.body(body != null ? body : new StringBinaryData(""));

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static CreateAgenticBucketResult toCreateAgenticBucket(OperationOutput output) {
        return CreateAgenticBucketResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromDeleteAgenticBucket(DeleteAgenticBucketRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteAgenticBucket")
                .method("DELETE");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteAgenticBucketResult toDeleteAgenticBucket(OperationOutput output) {
        return DeleteAgenticBucketResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetAgenticBucket(GetAgenticBucketRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetAgenticBucket")
                .method("GET");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetAgenticBucketResult toGetAgenticBucket(OperationOutput output) {
        Object innerBody = SerdeUtils.deserializeXmlBody(output, AgenticBucketInfo.class);
        return GetAgenticBucketResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromListAgenticBuckets(ListAgenticBucketsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListAgenticBuckets")
                .method("GET");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        builder.parameters(parameters);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListAgenticBucketsResult toListAgenticBuckets(OperationOutput output) {
        Object innerBody = SerdeUtils.deserializeXmlBody(output, ListAgenticBucketsResult.ListAgenticBucketsResultXml.class);
        return ListAgenticBucketsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromPutAgenticBucketStatus(PutAgenticBucketStatusRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutAgenticBucketStatus")
                .method("PUT");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("status", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        BinaryData body = SerdeUtils.serializeXmlBody(request.agenticBucketStatus());
        builder.body(body != null ? body : new StringBinaryData(""));

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutAgenticBucketStatusResult toPutAgenticBucketStatus(OperationOutput output) {
        return PutAgenticBucketStatusResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromListBucketSpaces(ListBucketSpacesRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListBucketSpaces")
                .method("GET");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("bucketSpace", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListBucketSpacesResult toListBucketSpaces(OperationOutput output) {
        Object innerBody = SerdeUtils.deserializeXmlBody(output, ListBucketSpacesResult.ListBucketSpacesResultXml.class);
        return ListBucketSpacesResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }
}
