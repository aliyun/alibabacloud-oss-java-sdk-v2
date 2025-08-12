package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.models.internal.ListMultipartUploadsResultXml;
import com.aliyun.sdk.service.oss2.models.internal.ListPartResultXml;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public final class SerdeObjectMultipart {

    public static OperationInput fromInitiateMultipartUpload(InitiateMultipartUploadRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("InitiateMultipartUpload")
                .method("POST");

        // default headers
        //Map<String, String> headers = MapUtils.caseInsensitiveMap();
        //headers.put("Content-Type", "application/xml");
        //builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("uploads", "");
        parameters.put("encoding-type", "url");
        builder.parameters(parameters);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static InitiateMultipartUploadResult toInitiateMultipartUpload(OperationOutput output) {
        InitiateMultipartUpload innerBody = (InitiateMultipartUpload)SerdeUtils.deserializeXmlBody(output, InitiateMultipartUpload.class);

        // encoding type
        if (innerBody != null && "url".equals(innerBody.encodingType())) {
            String key = innerBody.key();
            if (key != null) {
                innerBody = innerBody.toBuilder().key(HttpUtils.urlDecode(key)).build();
            }
        }

        return InitiateMultipartUploadResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromUploadPart(UploadPartRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("UploadPart")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/octet-stream");
        builder.headers(headers);

        // body
        builder.body(request.body());

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input);
        return input;
    }

    public static UploadPartResult toUploadPart(OperationOutput output) {
        Object innerBody = null;
        return UploadPartResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromCompleteMultipartUpload(CompleteMultipartUploadRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("CompleteMultipartUpload")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("encoding-type", "url");
        builder.parameters(parameters);

        // body
        if (request.completeMultipartUpload() != null) {
            BinaryData body = SerdeUtils.serializeXmlBody(request.completeMultipartUpload());
            builder.body(body);
        }

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static CompleteMultipartUploadResult toCompleteMultipartUpload(OperationOutput output) {
        CompleteMultipartUploadResultXml innerBody = (CompleteMultipartUploadResultXml)SerdeUtils.deserializeXmlBody(output, CompleteMultipartUploadResultXml.class);

        // encoding type
        if (innerBody != null && "url".equals(innerBody.encodingType())) {
            String key = innerBody.key();
            if (key != null) {
                innerBody = innerBody.toBuilder().key(HttpUtils.urlDecode(key)).build();
            }
        }

        return CompleteMultipartUploadResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromUploadPartCopy(UploadPartCopyRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("UploadPartCopy")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/octet-stream");
        headers.put("x-oss-copy-source", SerdeUtils.encodeCopySource(request));
        builder.headers(headers);

        builder.bucket(request.bucket());
        builder.key(request.key());


        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static UploadPartCopyResult toUploadPartCopy(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, CopyPartResult.class);

        return UploadPartCopyResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody).build();
    }


    public static OperationInput fromAbortMultipartUpload(AbortMultipartUploadRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("AbortMultipartUpload")
                .method("DELETE");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static AbortMultipartUploadResult toAbortMultipartUpload(OperationOutput output) {
        Object innerBody = null;
        return AbortMultipartUploadResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromListMultipartUploads(ListMultipartUploadsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListMultipartUploads")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("uploads", "");
        parameters.put("encoding-type", "url");
        builder.parameters(parameters);

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListMultipartUploadsResult toListMultipartUploads(OperationOutput output) {

        ListMultipartUploadsResultXml innerBody = (ListMultipartUploadsResultXml)SerdeUtils.deserializeXmlBody(output, ListMultipartUploadsResultXml.class);

        // encoding type
        if (innerBody != null && "url".equals(innerBody.encodingType)) {
            innerBody.keyMarker = HttpUtils.urlDecode(innerBody.keyMarker);
            innerBody.nextKeyMarker = HttpUtils.urlDecode(innerBody.nextKeyMarker);
            innerBody.prefix = HttpUtils.urlDecode(innerBody.prefix);
            innerBody.delimiter = HttpUtils.urlDecode(innerBody.delimiter);

            if (innerBody.uploads != null) {
                innerBody.uploads = innerBody.uploads.stream()
                        .map(x->x.toBuilder().key(HttpUtils.urlDecode(x.key())).build())
                        .collect(Collectors.toList());
            }
        }

        return ListMultipartUploadsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromListParts(ListPartsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("ListParts")
                .method("GET");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("encoding-type", "url");
        builder.parameters(parameters);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static ListPartsResult toListParts(OperationOutput output) {
        ListPartResultXml innerBody = (ListPartResultXml)SerdeUtils.deserializeXmlBody(output, ListPartResultXml.class);

        // encoding type
        if (innerBody != null && "url".equals(innerBody.encodingType)) {
            innerBody.key = HttpUtils.urlDecode(innerBody.key);
        }

        return ListPartsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


}