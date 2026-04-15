package com.aliyun.sdk.service.oss2.tables.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.arns.Arn;
import com.aliyun.sdk.service.oss2.tables.models.*;
import com.aliyun.sdk.service.oss2.tables.models.internal.CreateTableResultJson;
import com.aliyun.sdk.service.oss2.tables.models.internal.GetTableResultJson;
import com.aliyun.sdk.service.oss2.tables.models.internal.ListTablesResultJson;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.security.InvalidParameterException;
import java.util.Map;

import static com.aliyun.sdk.service.oss2.AttributeKey.IS_BUCKET_ARN;

public final class SerdeTableBasic {

    // ==================== CreateTable ====================

    public static OperationInput fromCreateTable(CreateTableRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());
        OperationInput input = OperationInput.newBuilder()
                .opName("CreateTable")
                .bucket(request.tableBucketARN())
                .key(String.format("tables/%s/%s", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace())))
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static CreateTableResult toCreateTable(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, CreateTableResultJson.class);
        return CreateTableResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== DeleteTable ====================

    public static OperationInput fromDeleteTable(DeleteTableRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteTable")
                .method("DELETE");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("tables/%s/%s/%s", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static DeleteTableResult toDeleteTable(OperationOutput output) {
        Object innerBody = null;
        return DeleteTableResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== GetTable ====================

    public static OperationInput fromGetTable(GetTableRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetTable")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);

        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        String tableBucketARN = "";
        if (request.tableBucketARN() != null) {
            tableBucketARN = request.tableBucketARN();
            parameters.put("tableBucketARN", request.tableBucketARN());
            if (request.namespace() != null) {
                parameters.put("namespace", request.namespace());
            }
            if (request.name() != null) {
                parameters.put("name", request.name());
            }
        } else if (request.tableArn() != null) {
            parameters.put("tableArn", request.tableArn());
            Arn arn = Arn.fromString(request.tableArn());
            if (!("bucket".equals(arn.resource().resourceType().orElse("")) &&
                    arn.resource().qualifier().orElse("").startsWith("table/"))) {
                throw new InvalidParameterException("input.tableArn is not table arn");
            }
            int index = request.tableArn().indexOf("/table/");
            if (index > 0) {
                tableBucketARN = request.tableArn().substring(0, index);
            }
        }
        builder.parameters(parameters);

        builder.bucket(tableBucketARN);
        builder.key("get-table");

        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static GetTableResult toGetTable(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, GetTableResultJson.class);
        return GetTableResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== ListTables ====================

    public static OperationInput fromListTables(ListTablesRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListTables")
                .method("GET");
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        builder.headers(headers);
        builder.bucket(request.tableBucketARN());
        builder.key(String.format("tables/%s", HttpUtils.urlEncode(request.tableBucketARN())));
        OperationInput input = builder.build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static ListTablesResult toListTables(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeJsonUtils.fromJsonBody(output, ListTablesResultJson.class);
        return ListTablesResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    // ==================== RenameTable ====================

    public static OperationInput fromRenameTable(RenameTableRequest request) {
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/json");
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        BinaryData body = SerdeJsonUtils.toJson(request.bodyFields());
        OperationInput input = OperationInput.newBuilder()
                .opName("RenameTable")
                .bucket(request.tableBucketARN())
                .key(String.format("tables/%s/%s/%s/rename", HttpUtils.urlEncode(request.tableBucketARN()), HttpUtils.urlEncode(request.namespace()), HttpUtils.urlEncode(request.name())))
                .method("PUT")
                .headers(headers)
                .parameters(parameters)
                .body(body)
                .build();
        input.opMetadata().put(IS_BUCKET_ARN, true);
        SerdeJsonUtils.serializeInput(request, input, SerdeJsonUtils.addContentMd5);
        return input;
    }

    public static RenameTableResult toRenameTable(OperationOutput output) {
        Object innerBody = null;
        return RenameTableResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }
}
