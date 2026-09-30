package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeBucketStorageQuota {

    public static OperationInput fromPutBucketStorageQuota(PutBucketStorageQuotaRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutBucketStorageQuota")
                .method("PUT");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("quota", "");
        builder.parameters(parameters);

        BinaryData body = SerdeUtils.serializeXmlBody(request.quotaConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketStorageQuotaResult toPutBucketStorageQuota(OperationOutput output) {
        return PutBucketStorageQuotaResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetBucketStorageQuota(GetBucketStorageQuotaRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetBucketStorageQuota")
                .method("GET");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("quota", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketStorageQuotaResult toGetBucketStorageQuota(OperationOutput output) {
        Object innerBody = SerdeUtils.deserializeXmlBody(output, QuotaConfiguration.class);
        return GetBucketStorageQuotaResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromDeleteBucketStorageQuota(DeleteBucketStorageQuotaRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteBucketStorageQuota")
                .method("DELETE");

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("quota", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteBucketStorageQuotaResult toDeleteBucketStorageQuota(OperationOutput output) {
        return DeleteBucketStorageQuotaResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }
}
