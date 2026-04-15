package com.aliyun.sdk.service.oss2.tables.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.models.internal.CreateTableBucketResultJson;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketResultJson;
import com.aliyun.sdk.service.oss2.tables.models.internal.ListTableBucketsResultJson;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

import static com.aliyun.sdk.service.oss2.AttributeKey.IS_BUCKET_ARN;

public final class SerdeTableBucketBasic {

    // ==================== CreateTableBucket ====================

    public static OperationInput fromCreateTableBucket(CreateTableBucketRequest request) {

        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());
        OperationInput input = OperationInput.newBuilder()
                .opName("CreateTableBucket")
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .key("buckets")
                .body(body)
                .build();
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static CreateTableBucketResult toCreateTableBucket(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, CreateTableBucketResultJson.class);
        return CreateTableBucketResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== DeleteTableBucket ====================

    public static OperationInput fromDeleteTableBucket(DeleteTableBucketRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteTableBucket")
                .method("DELETE");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("buckets/%s", HttpUtils.urlEncode(request.tableBucketARN())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static DeleteTableBucketResult toDeleteTableBucket(OperationOutput output) {
        Object innerBody = null;
        return DeleteTableBucketResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== GetTableBucket ====================

    public static OperationInput fromGetTableBucket(GetTableBucketRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetTableBucket")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("buckets/%s", HttpUtils.urlEncode(request.tableBucketARN())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetTableBucketResult toGetTableBucket(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetTableBucketResultJson.class);
        return GetTableBucketResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== ListTableBuckets ====================

    public static OperationInput fromListTableBuckets(ListTableBucketsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListTableBuckets")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.key("buckets");
        OperationInput input = builder.build();
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static ListTableBucketsResult toListTableBuckets(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, ListTableBucketsResultJson.class);
        return ListTableBucketsResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }
}
