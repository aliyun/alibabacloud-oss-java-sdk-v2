package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeObjectWorm {

    public static OperationInput fromPutObjectRetention(PutObjectRetentionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutObjectRetention")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");

        // bypassGovernanceRetention header
        if (request.bypassGovernanceRetention() != null) {
            headers.put("x-oss-bypass-governance-retention", String.valueOf(request.bypassGovernanceRetention()));
        }

        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("retention", "");
        builder.parameters(parameters);

        // body
        if (request.retention() != null) {
            BinaryData body = SerdeUtils.serializeXmlBody(request.retention());
            builder.body(body);
        }

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutObjectRetentionResult toPutObjectRetention(OperationOutput output) {
        return PutObjectRetentionResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetObjectRetention(GetObjectRetentionRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetObjectRetention")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("retention", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input);
        return input;
    }

    public static GetObjectRetentionResult toGetObjectRetention(OperationOutput output) {
        Retention retention = null;
        if (output.body().isPresent()) {
            retention = (Retention) SerdeUtils.deserializeXmlBody(output, Retention.class);
        }

        return GetObjectRetentionResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .retention(retention)
                .build();
    }

    public static OperationInput fromPutObjectLegalHold(PutObjectLegalHoldRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutObjectLegalHold")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("legalHold", "");
        builder.parameters(parameters);

        // body
        if (request.legalHold() != null) {
            BinaryData body = SerdeUtils.serializeXmlBody(request.legalHold());
            builder.body(body);
        }

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutObjectLegalHoldResult toPutObjectLegalHold(OperationOutput output) {
        return PutObjectLegalHoldResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetObjectLegalHold(GetObjectLegalHoldRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetObjectLegalHold")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("legalHold", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input);
        return input;
    }

    public static GetObjectLegalHoldResult toGetObjectLegalHold(OperationOutput output) {
        LegalHold legalHold = null;
        if (output.body().isPresent()) {
            legalHold = (LegalHold) SerdeUtils.deserializeXmlBody(output, LegalHold.class);
        }

        return GetObjectLegalHoldResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .legalHold(legalHold)
                .build();
    }
}
