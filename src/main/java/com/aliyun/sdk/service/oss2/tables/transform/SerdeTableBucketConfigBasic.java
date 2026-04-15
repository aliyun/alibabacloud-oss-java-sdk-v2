package com.aliyun.sdk.service.oss2.tables.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketEncryptionResultJson;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketMaintenanceConfigurationResultJson;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableBucketPolicyResultJson;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

import static com.aliyun.sdk.service.oss2.AttributeKey.IS_BUCKET_ARN;

public final class SerdeTableBucketConfigBasic {

    // ==================== DeleteTableBucketEncryption ====================

    public static OperationInput fromDeleteTableBucketEncryption(DeleteTableBucketEncryptionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteTableBucketEncryption")
                .method("DELETE");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("buckets/%s/encryption", HttpUtils.urlEncode(request.tableBucketARN())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static DeleteTableBucketEncryptionResult toDeleteTableBucketEncryption(OperationOutput output) {
        Object innerBody = null;
        return DeleteTableBucketEncryptionResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== GetTableBucketEncryption ====================

    public static OperationInput fromGetTableBucketEncryption(GetTableBucketEncryptionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetTableBucketEncryption")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("buckets/%s/encryption", HttpUtils.urlEncode(request.tableBucketARN())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetTableBucketEncryptionResult toGetTableBucketEncryption(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetTableBucketEncryptionResultJson.class);
        return GetTableBucketEncryptionResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== PutTableBucketEncryption ====================

    public static OperationInput fromPutTableBucketEncryption(PutTableBucketEncryptionRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put(request.tableBucketARN(), "");
        parameters.put("encryption", "");
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());
        OperationInput input = OperationInput.newBuilder()
                .opName("PutTableBucketEncryption")
                .bucket(request.tableBucketARN())
                .key(String.format("buckets/%s/encryption", HttpUtils.urlEncode(request.tableBucketARN())))
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static PutTableBucketEncryptionResult toPutTableBucketEncryption(OperationOutput output) {
        Object innerBody = null;
        return PutTableBucketEncryptionResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== GetTableBucketMaintenanceConfiguration ====================

    public static OperationInput fromGetTableBucketMaintenanceConfiguration(GetTableBucketMaintenanceConfigurationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetTableBucketMaintenanceConfiguration")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("buckets/%s/maintenance", HttpUtils.urlEncode(request.tableBucketARN())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetTableBucketMaintenanceConfigurationResult toGetTableBucketMaintenanceConfiguration(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetTableBucketMaintenanceConfigurationResultJson.class);
        return GetTableBucketMaintenanceConfigurationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== PutTableBucketMaintenanceConfiguration ====================

    public static OperationInput fromPutTableBucketMaintenanceConfiguration(PutTableBucketMaintenanceConfigurationRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());
        OperationInput input = OperationInput.newBuilder()
                .opName("PutTableBucketMaintenanceConfiguration")
                .bucket(request.tableBucketARN())
                .key(String.format("buckets/%s/maintenance/%s", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.type())))
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static PutTableBucketMaintenanceConfigurationResult toPutTableBucketMaintenanceConfiguration(OperationOutput output) {
        Object innerBody = null;
        return PutTableBucketMaintenanceConfigurationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }


    // ==================== DeleteTableBucketPolicy ====================

    public static OperationInput fromDeleteTableBucketPolicy(DeleteTableBucketPolicyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteTableBucketPolicy")
                .method("DELETE");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("buckets/%s/policy", HttpUtils.urlEncode(request.tableBucketARN())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static DeleteTableBucketPolicyResult toDeleteTableBucketPolicy(OperationOutput output) {
        Object innerBody = null;
        return DeleteTableBucketPolicyResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== GetTableBucketPolicy ====================

    public static OperationInput fromGetTableBucketPolicy(GetTableBucketPolicyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetTableBucketPolicy")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("buckets/%s/policy", HttpUtils.urlEncode(request.tableBucketARN())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetTableBucketPolicyResult toGetTableBucketPolicy(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetTableBucketPolicyResultJson.class);
        return GetTableBucketPolicyResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== PutTableBucketPolicy ====================

    public static OperationInput fromPutTableBucketPolicy(PutTableBucketPolicyRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());
        OperationInput input = OperationInput.newBuilder()
                .opName("PutTableBucketPolicy")
                .bucket(request.tableBucketARN())
                .key(String.format("buckets/%s/policy", HttpUtils.urlEncode(request.tableBucketARN())))
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static PutTableBucketPolicyResult toPutTableBucketPolicy(OperationOutput output) {
        Object innerBody = null;
        return PutTableBucketPolicyResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }


}
