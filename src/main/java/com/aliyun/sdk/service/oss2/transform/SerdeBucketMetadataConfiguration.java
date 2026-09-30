package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.models.internal.GetBucketMetadataConfigurationResultXml;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

/**
 * Serializes and deserializes bucket metadata configuration operations.
 */
public final class SerdeBucketMetadataConfiguration {
    private SerdeBucketMetadataConfiguration() {
    }

    public static OperationInput fromCreateBucketMetadataConfiguration(CreateBucketMetadataConfigurationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("CreateBucketMetadataConfiguration")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metadataConfiguration", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.metadataConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static CreateBucketMetadataConfigurationResult toCreateBucketMetadataConfiguration(OperationOutput output) {
        return CreateBucketMetadataConfigurationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(null).build();
    }

    public static OperationInput fromGetBucketMetadataConfiguration(GetBucketMetadataConfigurationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetBucketMetadataConfiguration")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metadataConfiguration", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketMetadataConfigurationResult toGetBucketMetadataConfiguration(OperationOutput output) {
        Object innerBody = SerdeUtils.deserializeXmlBody(output, GetBucketMetadataConfigurationResultXml.class);
        return GetBucketMetadataConfigurationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }

    public static OperationInput fromDeleteBucketMetadataConfiguration(DeleteBucketMetadataConfigurationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteBucketMetadataConfiguration")
                .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metadataConfiguration", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteBucketMetadataConfigurationResult toDeleteBucketMetadataConfiguration(OperationOutput output) {
        return DeleteBucketMetadataConfigurationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(null).build();
    }

    public static OperationInput fromUpdateBucketMetadataInventoryTableConfiguration(
            UpdateBucketMetadataInventoryTableConfigurationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("UpdateBucketMetadataInventoryTableConfiguration")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metadataInventoryTable", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.inventoryTableConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static UpdateBucketMetadataInventoryTableConfigurationResult toUpdateBucketMetadataInventoryTableConfiguration(
            OperationOutput output) {
        return UpdateBucketMetadataInventoryTableConfigurationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(null).build();
    }

    public static OperationInput fromUpdateBucketMetadataJournalTableConfiguration(
            UpdateBucketMetadataJournalTableConfigurationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("UpdateBucketMetadataJournalTableConfiguration")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("metadataJournalTable", "");
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.journalTableConfiguration());
        builder.body(body);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static UpdateBucketMetadataJournalTableConfigurationResult toUpdateBucketMetadataJournalTableConfiguration(
            OperationOutput output) {
        return UpdateBucketMetadataJournalTableConfigurationResult.newBuilder()
                .headers(output.headers).status(output.status).statusCode(output.statusCode)
                .innerBody(null).build();
    }

}
