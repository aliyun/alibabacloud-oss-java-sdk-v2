package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.AttributeMap;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.models.internal.ListVersionsResultXml;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.aliyun.sdk.service.oss2.AttributeKey.SUBRESOURCE;

public final class SerdeBucketVersioning {


    public static OperationInput fromPutBucketVersioning(PutBucketVersioningRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("PutBucketVersioning")
            .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("versioning", "");
        builder.parameters(parameters);
        

        
        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.versioningConfiguration());
        builder.body(body);


        builder.bucket(request.bucket());
        

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketVersioningResult toPutBucketVersioning(OperationOutput output) {
        Object innerBody = null;
        return PutBucketVersioningResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromGetBucketVersioning(GetBucketVersioningRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("GetBucketVersioning")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("versioning", "");
        builder.parameters(parameters);


        builder.bucket(request.bucket());
        

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketVersioningResult toGetBucketVersioning(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, VersioningConfiguration.class);
         
        return GetBucketVersioningResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


    public static OperationInput fromListObjectVersions(ListObjectVersionsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
            .opName("ListObjectVersions")
            .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

         
        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("versions", "");
        parameters.put("encoding-type", "url");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListObjectVersionsResult toListObjectVersions(OperationOutput output) {
        ListVersionsResultXml innerBody = (ListVersionsResultXml)SerdeUtils.deserializeXmlBody(output, ListVersionsResultXml.class);

        // encoding type
        if (innerBody != null && "url".equals(innerBody.encodingType)) {
            innerBody.prefix = HttpUtils.urlDecode(innerBody.prefix);
            innerBody.keyMarker = HttpUtils.urlDecode(innerBody.keyMarker);
            innerBody.delimiter = HttpUtils.urlDecode(innerBody.delimiter);
            innerBody.nextKeyMarker = HttpUtils.urlDecode(innerBody.nextKeyMarker);

            if (innerBody.versions != null) {
                innerBody.versions = innerBody.versions.stream()
                        .map(x->x.toBuilder().key(HttpUtils.urlDecode(x.key())).build())
                        .collect(Collectors.toList());
            }

            if (innerBody.deleteMarkers != null) {
                innerBody.deleteMarkers = innerBody.deleteMarkers.stream()
                        .map(x->x.toBuilder().key(HttpUtils.urlDecode(x.key())).build())
                        .collect(Collectors.toList());
            }

            if (innerBody.commonPrefixes != null) {
                innerBody.commonPrefixes = innerBody.commonPrefixes.stream()
                        .map(x->x.toBuilder().prefix(HttpUtils.urlDecode(x.prefix())).build())
                        .collect(Collectors.toList());
            }
        }
        return ListObjectVersionsResult.newBuilder()
            .headers(output.headers)
            .status(output.status)
            .statusCode(output.statusCode)
            .innerBody(innerBody)
            .build();
    }


}