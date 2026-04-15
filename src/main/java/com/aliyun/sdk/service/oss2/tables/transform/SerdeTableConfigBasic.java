package com.aliyun.sdk.service.oss2.tables.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.models.internal.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

import static com.aliyun.sdk.service.oss2.AttributeKey.IS_BUCKET_ARN;

public final class SerdeTableConfigBasic {

    // ==================== GetTableEncryption ====================

    public static OperationInput fromGetTableEncryption(GetTableEncryptionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetTableEncryption")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("tables/%s/%s/%s/encryption", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetTableEncryptionResult toGetTableEncryption(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetTableEncryptionResultJson.class);
        return GetTableEncryptionResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== GetTableMaintenanceConfiguration ====================

    public static OperationInput fromGetTableMaintenanceConfiguration(GetTableMaintenanceConfigurationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetTableMaintenanceConfiguration")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("tables/%s/%s/%s/maintenance", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetTableMaintenanceConfigurationResult toGetTableMaintenanceConfiguration(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetTableMaintenanceConfigurationResultJson.class);
        return GetTableMaintenanceConfigurationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== PutTableMaintenanceConfiguration ====================

    public static OperationInput fromPutTableMaintenanceConfiguration(PutTableMaintenanceConfigurationRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());
        OperationInput input = OperationInput.newBuilder()
                .opName("PutTableMaintenanceConfiguration")
                .bucket(request.tableBucketARN())
                .key(String.format("tables/%s/%s/%s/maintenance/%s", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name()), HttpUtils.urlEncode(request.type())))
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static PutTableMaintenanceConfigurationResult toPutTableMaintenanceConfiguration(OperationOutput output) {
        Object innerBody = null;
        return PutTableMaintenanceConfigurationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== GetTableMaintenanceJobStatus ====================

    public static OperationInput fromGetTableMaintenanceJobStatus(GetTableMaintenanceJobStatusRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetTableMaintenanceJobStatus")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        if (request.tableARN() != null) {
            headers.put("x-oss-table-arn", request.tableARN());
        }
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("tables/%s/%s/%s/maintenance-job-status", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetTableMaintenanceJobStatusResult toGetTableMaintenanceJobStatus(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetTableMaintenanceJobStatusResultJson.class);
        return GetTableMaintenanceJobStatusResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== GetTableMetadataLocation ====================

    public static OperationInput fromGetTableMetadataLocation(GetTableMetadataLocationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetTableMetadataLocation")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("tables/%s/%s/%s/metadata-location", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetTableMetadataLocationResult toGetTableMetadataLocation(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetTableMetadataLocationResultJson.class);
        return GetTableMetadataLocationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== UpdateTableMetadataLocation ====================

    public static OperationInput fromUpdateTableMetadataLocation(UpdateTableMetadataLocationRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());
        OperationInput input = OperationInput.newBuilder()
                .opName("UpdateTableMetadataLocation")
                .bucket(request.tableBucketARN())
                .key(String.format("tables/%s/%s/%s/metadata-location", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name())))
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static UpdateTableMetadataLocationResult toUpdateTableMetadataLocation(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, UpdateTableMetadataLocationResultJson.class);
        return UpdateTableMetadataLocationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== DeleteTablePolicy ====================

    public static OperationInput fromDeleteTablePolicy(DeleteTablePolicyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteTablePolicy")
                .method("DELETE");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("tables/%s/%s/%s/policy", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static DeleteTablePolicyResult toDeleteTablePolicy(OperationOutput output) {
        Object innerBody = null;
        return DeleteTablePolicyResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== GetTablePolicy ====================

    public static OperationInput fromGetTablePolicy(GetTablePolicyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetTablePolicy")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("tables/%s/%s/%s/policy", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetTablePolicyResult toGetTablePolicy(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetTablePolicyResultJson.class);
        return GetTablePolicyResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== PutTablePolicy ====================

    public static OperationInput fromPutTablePolicy(PutTablePolicyRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());
        OperationInput input = OperationInput.newBuilder()
                .opName("PutTablePolicy")
                .bucket(request.tableBucketARN())
                .key(String.format("tables/%s/%s/%s/policy", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name())))
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static PutTablePolicyResult toPutTablePolicy(OperationOutput output) {
        Object innerBody = null;
        return PutTablePolicyResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }
}
