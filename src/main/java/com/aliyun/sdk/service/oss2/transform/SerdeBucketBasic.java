package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.AttributeMap;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.models.internal.BucketInfoXml;
import com.aliyun.sdk.service.oss2.models.internal.ListBucketResultXml;
import com.aliyun.sdk.service.oss2.models.internal.ListBucketV2ResultXml;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.StringBinaryData;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.aliyun.sdk.service.oss2.AttributeKey.SUBRESOURCE;

public final class SerdeBucketBasic {

    public static OperationInput fromGetBucketStat(GetBucketStatRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetBucketStat")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("stat", "");
        builder.parameters(parameters);


        // opMetadata
        AttributeMap opMetadata = AttributeMap.empty();
        opMetadata.put(SUBRESOURCE, Collections.singletonList("stat"));
        builder.opMetadata(opMetadata);


        builder.bucket(request.bucket());


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketStatResult toGetBucketStat(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, BucketStat.class);

        return GetBucketStatResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromPutBucket(PutBucketRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutBucket")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.createBucketConfiguration());
        builder.body(body != null ? body : new StringBinaryData(""));

        builder.bucket(request.bucket());


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static PutBucketResult toPutBucket(OperationOutput output) {
        Object innerBody = null;
        return PutBucketResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromDeleteBucket(DeleteBucketRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteBucket")
                .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        builder.bucket(request.bucket());


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteBucketResult toDeleteBucket(OperationOutput output) {
        Object innerBody = null;
        return DeleteBucketResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromListObjects(ListObjectsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListObjects")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseInsensitiveMap();
        parameters.put("encoding-type", "url");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListObjectsResult toListObjects(OperationOutput output) {
        //Object innerBody = null;
        ListBucketResultXml innerBody = (ListBucketResultXml)SerdeUtils.deserializeXmlBody(output, ListBucketResultXml.class);

        // encoding type
        if (innerBody != null && "url".equals(innerBody.encodingType)) {
            innerBody.prefix = HttpUtils.urlDecode(innerBody.prefix);
            innerBody.marker = HttpUtils.urlDecode(innerBody.marker);
            innerBody.delimiter = HttpUtils.urlDecode(innerBody.delimiter);
            innerBody.nextMarker = HttpUtils.urlDecode(innerBody.nextMarker);

            if (innerBody.contents != null) {
                innerBody.contents = innerBody.contents.stream()
                        .map(x->x.toBuilder().key(HttpUtils.urlDecode(x.key())).build())
                        .collect(Collectors.toList());
            }

            if (innerBody.commonPrefixes != null) {
                innerBody.commonPrefixes = innerBody.commonPrefixes.stream()
                        .map(x->x.toBuilder().prefix(HttpUtils.urlDecode(x.prefix())).build())
                        .collect(Collectors.toList());
            }
        }

        return ListObjectsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromListObjectsV2(ListObjectsV2Request request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListObjectsV2")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("encoding-type", "url");
        parameters.put("list-type", "2");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListObjectsV2Result toListObjectsV2(OperationOutput output) {
        ListBucketV2ResultXml innerBody = (ListBucketV2ResultXml)SerdeUtils.deserializeXmlBody(output, ListBucketV2ResultXml.class);

        // encoding type
        if (innerBody != null && "url".equals(innerBody.encodingType)) {
            innerBody.prefix = HttpUtils.urlDecode(innerBody.prefix);
            innerBody.startAfter = HttpUtils.urlDecode(innerBody.startAfter);
            innerBody.delimiter = HttpUtils.urlDecode(innerBody.delimiter);
            innerBody.continuationToken = HttpUtils.urlDecode(innerBody.continuationToken);
            innerBody.nextContinuationToken = HttpUtils.urlDecode(innerBody.nextContinuationToken);

            if (innerBody.contents != null) {
                innerBody.contents = innerBody.contents.stream()
                        .map(x->x.toBuilder().key(HttpUtils.urlDecode(x.key())).build())
                        .collect(Collectors.toList());
            }

            if (innerBody.commonPrefixes != null) {
                innerBody.commonPrefixes = innerBody.commonPrefixes.stream()
                        .map(x->x.toBuilder().prefix(HttpUtils.urlDecode(x.prefix())).build())
                        .collect(Collectors.toList());
            }
        }

        return ListObjectsV2Result.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromGetBucketInfo(GetBucketInfoRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetBucketInfo")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("bucketInfo", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketInfoResult toGetBucketInfo(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, BucketInfoXml.class);

        return GetBucketInfoResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromGetBucketLocation(GetBucketLocationRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetBucketLocation")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("location", "");
        builder.parameters(parameters);

        builder.bucket(request.bucket());


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetBucketLocationResult toGetBucketLocation(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, String.class);

        return GetBucketLocationResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


}