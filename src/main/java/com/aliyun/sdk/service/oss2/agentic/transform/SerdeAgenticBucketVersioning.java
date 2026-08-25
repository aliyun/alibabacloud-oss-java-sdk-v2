package com.aliyun.sdk.service.oss2.agentic.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.models.VersioningConfiguration;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeAgenticBucketVersioning {

    public static OperationInput fromPutAgenticBucketVersioning(PutAgenticBucketVersioningRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutAgenticBucketVersioning")
                .method("PUT");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("versioning", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        BinaryData body = SerdeUtils.serializeXmlBody(request.versioningConfiguration());
        builder.body(body);

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutAgenticBucketVersioningResult toPutAgenticBucketVersioning(OperationOutput output) {
        return PutAgenticBucketVersioningResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetAgenticBucketVersioning(GetAgenticBucketVersioningRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetAgenticBucketVersioning")
                .method("GET");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("versioning", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetAgenticBucketVersioningResult toGetAgenticBucketVersioning(OperationOutput output) {
        Object innerBody = SerdeUtils.deserializeXmlBody(output, VersioningConfiguration.class);
        return GetAgenticBucketVersioningResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }
}
