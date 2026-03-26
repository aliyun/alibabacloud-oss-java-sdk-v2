package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Map;

public final class SerdeBucketObjectWormConfiguration {

    public static OperationInput fromPutBucketObjectWormConfiguration(PutBucketObjectWormConfigurationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutBucketObjectWormConfiguration")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("objectWorm", "");
        builder.parameters(parameters);

        // body
        if (request.objectWormConfiguration() != null) {
            BinaryData body = SerdeUtils.serializeXmlBody(request.objectWormConfiguration());
            builder.body(body);
        }

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketObjectWormConfigurationResult toPutBucketObjectWormConfiguration(OperationOutput output) {
        return PutBucketObjectWormConfigurationResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .build();
    }

    public static OperationInput fromGetBucketObjectWormConfiguration(GetBucketObjectWormConfigurationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetBucketObjectWormConfiguration")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("objectWorm", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input);
        return input;
    }

    public static GetBucketObjectWormConfigurationResult toGetBucketObjectWormConfiguration(OperationOutput output) {
        ObjectWormConfiguration objectWormConfiguration = null;
        if (output.body().isPresent()) {
            objectWormConfiguration = (ObjectWormConfiguration) SerdeUtils.deserializeXmlBody(output, ObjectWormConfiguration.class);
        }

        return GetBucketObjectWormConfigurationResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .objectWormConfiguration(objectWormConfiguration)
                .build();
    }

}
