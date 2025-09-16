package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.AttributeKey;
import com.aliyun.sdk.service.oss2.AttributeMap;
import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.hash.CRC64Observer;
import com.aliyun.sdk.service.oss2.hash.CRC64ResponseChecker;
import com.aliyun.sdk.service.oss2.io.StreamObserver;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.models.internal.DeleteResultXml;
import com.aliyun.sdk.service.oss2.progress.ProgressObserver;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.InputStreamBinaryData;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;
import com.aliyun.sdk.service.oss2.types.FeatureFlagsType;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.utils.XmlUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class SerdeObjectBasic {


    public static OperationInput fromPutObject(PutObjectRequest request, int featureFlags) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("PutObject")
                .method("PUT");

        // default headers
        //Map<String, String> headers = MapUtils.caseInsensitiveMap();
        //builder.headers(headers);

        // body
        if (request.body() != null) {
            builder.body(request.body());
        }

        // prog observer
        AttributeMap opMetadata = AttributeMap.empty();
        List<StreamObserver> streamObservers = new ArrayList<>();
        List<Consumer<ResponseMessage>> responseHandlers = new ArrayList<>();
        if (request.progressListener() != null) {
            streamObservers.add(new ProgressObserver(request.progressListener(), request.body().getLength()));
        }

        // crc observer
        if (FeatureFlagsType.ENABLE_CRC64_CHECK_UPLOAD.isSet(featureFlags)) {
            CRC64Observer observer = new CRC64Observer();
            streamObservers.add(observer);
            responseHandlers.add(new CRC64ResponseChecker(observer.getChecksum()));
        }

        if (!streamObservers.isEmpty()) {
            opMetadata.put(AttributeKey.UPLOAD_OBSERVER, streamObservers);
        }
        if (!responseHandlers.isEmpty()) {
            opMetadata.put(AttributeKey.RESPONSE_HANDLER, responseHandlers);
        }
        builder.opMetadata(opMetadata);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();

        SerdeUtils.serializeInput(request, input);

        if (FeatureFlagsType.AUTO_DETECT_MIMETYPE.isSet(featureFlags)) {
            SerdeUtils.addContentType(input);
        }

        return input;
    }

    public static PutObjectResult toPutObject(OperationOutput output) {
        String bodyStr = null;
        if (output.body().isPresent()) {
            bodyStr = output.body().get().toString();
        }
        return PutObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(bodyStr)
                .build();
    }


    public static OperationInput fromCopyObject(CopyObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("CopyObject")
                .method("PUT");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("x-oss-copy-source", SerdeUtils.encodeCopySource(request));
        builder.headers(headers);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static CopyObjectResult toCopyObject(OperationOutput output) {
        Object innerBody = null;
        innerBody = SerdeUtils.deserializeXmlBody(output, String.class);

        return CopyObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromGetObject(GetObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetObject")
                .method("GET");

        // default headers

        builder.bucket(request.bucket());
        builder.key(request.key());

        // bodyConsumer
        if (request.dataConsumerSupplier() != null) {
            AttributeMap opMetadata = AttributeMap.empty();
            opMetadata.put(AttributeKey.RESPONSE_CONSUMER_SUPPLIER, request.dataConsumerSupplier());
            builder.opMetadata(opMetadata);
        }

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input);
        return input;
    }

    public static GetObjectResult toGetObject(OperationOutput output) {
        InputStream is;
        BinaryData body = output.body().orElse(null);
        if (body instanceof InputStreamBinaryData) {
            is = ((InputStreamBinaryData) body).unwrap();
        } else if (body != null) {
            is = body.toStream();
        } else {
            is = new ByteArrayInputStream(new byte[0]);
        }
        return GetObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(is)
                .build();
    }


    public static OperationInput fromAppendObject(AppendObjectRequest request, int featureFlags) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("AppendObject")
                .method("POST");

        // default headers
        //Map<String, String> headers = MapUtils.caseInsensitiveMap();
        //headers.put("Content-Type", "application/xml");
        //builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("append", "");
        builder.parameters(parameters);

        // body
        builder.body(request.body());

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input);

        if (FeatureFlagsType.AUTO_DETECT_MIMETYPE.isSet(featureFlags)) {
            SerdeUtils.addContentType(input);
        }

        return input;
    }

    public static AppendObjectResult toAppendObject(OperationOutput output) {
        Object innerBody = null;
        return AppendObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromDeleteObject(DeleteObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteObject")
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

    public static DeleteObjectResult toDeleteObject(OperationOutput output) {
        Object innerBody = null;
        return DeleteObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromDeleteMultipleObjects(DeleteMultipleObjectsRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("DeleteMultipleObjects")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("delete", "");
        parameters.put("encoding-type", "url");
        builder.parameters(parameters);

        // body
        StringBuilder xmlBody = new StringBuilder();
        xmlBody.append("<Delete>");
        if (request.quiet() != null) {
            xmlBody.append("<Quiet>").append(request.quiet()).append("</Quiet>");
        }
        if (request.deleteObjects() != null) {
            for (DeleteObject o: request.deleteObjects()) {
                xmlBody.append("<Object>");
                xmlBody.append("<Key>").append(XmlUtils.escapeText(o.key())).append("</Key>");
                if (o.versionId() != null) {
                    xmlBody.append("<VersionId>").append(XmlUtils.escapeText(o.versionId())).append("</VersionId>");
                }
                xmlBody.append("</Object>");
            }
        }
        xmlBody.append("</Delete>");
        String xmlStr = xmlBody.toString();
        builder.body(BinaryData.fromBytes(xmlStr.getBytes(StandardCharsets.UTF_8)));

        builder.bucket(request.bucket());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static DeleteMultipleObjectsResult toDeleteMultipleObjects(OperationOutput output) {
        DeleteResultXml innerBody = (DeleteResultXml)SerdeUtils.deserializeXmlBody(output, DeleteResultXml.class);

        // When in quiet mode, data mey be empty.
        if (innerBody == null) {
            innerBody = new DeleteResultXml();
        }

        // encoding type
        if ("url".equals(innerBody.encodingType)) {

            if (innerBody.deleted != null) {
                innerBody.deleted = innerBody.deleted.stream()
                        .map(x->x.toBuilder().key(HttpUtils.urlDecode(x.key())).build())
                        .collect(Collectors.toList());
            }
        }

        return DeleteMultipleObjectsResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromHeadObject(HeadObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("HeadObject")
                .method("HEAD");

        // default headers
        //Map<String, String> headers = MapUtils.caseInsensitiveMap();
        //headers.put("Content-Type", "application/xml");
        //builder.headers(headers);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static HeadObjectResult toHeadObject(OperationOutput output) {
        Object innerBody = null;
        return HeadObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromGetObjectMeta(GetObjectMetaRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("GetObjectMeta")
                .method("HEAD");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("objectMeta", "");
        builder.parameters(parameters);


        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static GetObjectMetaResult toGetObjectMeta(OperationOutput output) {
        Object innerBody = null;
        return GetObjectMetaResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromRestoreObject(RestoreObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("RestoreObject")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("restore", "");
        builder.parameters(parameters);


        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static RestoreObjectResult toRestoreObject(OperationOutput output) {
        Object innerBody = null;
        return RestoreObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }


    public static OperationInput fromCleanRestoredObject(CleanRestoredObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("CleanRestoredObject")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);


        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("cleanRestoredObject", "");
        builder.parameters(parameters);


        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }

    public static CleanRestoredObjectResult toCleanRestoredObject(OperationOutput output) {
        Object innerBody = null;
        return CleanRestoredObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

}