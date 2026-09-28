package com.aliyun.sdk.service.oss2.agentic.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.agentic.models.*;
import com.aliyun.sdk.service.oss2.models.BucketStorageQuotaConfiguration;
import com.aliyun.sdk.service.oss2.transform.SerdeUtils;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeAgenticBucketStorageQuota {

    public static OperationInput fromPutAgenticBucketStorageQuota(PutAgenticBucketStorageQuotaRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutAgenticBucketStorageQuota")
                .method("PUT");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("quota", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        BinaryData body = SerdeUtils.serializeXmlBody(request.bucketStorageQuotaConfiguration());
        builder.body(body != null ? body : new com.aliyun.sdk.service.oss2.transport.StringBinaryData(""));

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutAgenticBucketStorageQuotaResult toPutAgenticBucketStorageQuota(OperationOutput output) {
        return PutAgenticBucketStorageQuotaResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetAgenticBucketStorageQuota(GetAgenticBucketStorageQuotaRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetAgenticBucketStorageQuota")
                .method("GET");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("quota", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetAgenticBucketStorageQuotaResult toGetAgenticBucketStorageQuota(OperationOutput output) {
        Object innerBody = SerdeUtils.deserializeXmlBody(output, BucketStorageQuotaConfiguration.class);
        return GetAgenticBucketStorageQuotaResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromDeleteAgenticBucketStorageQuota(DeleteAgenticBucketStorageQuotaRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteAgenticBucketStorageQuota")
                .method("DELETE");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("agenticBucket", "");
        parameters.put("quota", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteAgenticBucketStorageQuotaResult toDeleteAgenticBucketStorageQuota(OperationOutput output) {
        return DeleteAgenticBucketStorageQuotaResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }
}
