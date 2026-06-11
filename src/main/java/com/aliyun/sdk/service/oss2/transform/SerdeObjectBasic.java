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
import com.aliyun.sdk.service.oss2.transport.BinaryDataConsumerSupplier;
import com.aliyun.sdk.service.oss2.transport.InputStreamBinaryData;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;
import com.aliyun.sdk.service.oss2.types.FeatureFlagsType;
import com.aliyun.sdk.service.oss2.utils.HttpUtils;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.aliyun.sdk.service.oss2.utils.XmlUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.util.DefaultXmlPrettyPrinter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
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

        // Check if both old and new parameters are set or neither is set
        if ((request.deleteObjects() != null) && (request.delete() != null)) {
            throw new IllegalArgumentException(
                "Either old parameters (objects, quiet) or new parameter (delete) is set, but not both"
            );
        }

        // body
        StringBuilder xmlBody = new StringBuilder();
        xmlBody.append("<Delete>");
        
        // Handle new parameter (delete)
        if (request.delete() != null) {
            if (request.delete().quiet() != null) {
                xmlBody.append("<Quiet>").append(request.delete().quiet()).append("</Quiet>");
            }
            if (request.delete().objects() != null) {
                for (ObjectIdentifier o: request.delete().objects()) {
                    xmlBody.append("<Object>");
                    xmlBody.append("<Key>").append(XmlUtils.escapeText(o.key())).append("</Key>");
                    if (o.versionId() != null) {
                        xmlBody.append("<VersionId>").append(XmlUtils.escapeText(o.versionId())).append("</VersionId>");
                    }
                    xmlBody.append("</Object>");
                }
            }
        } 
        // Handle old parameters (quiet, deleteObjects)
        else {
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


    public static OperationInput fromSealAppendObject(SealAppendObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("SealAppendObject")
                .method("POST");

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("seal", "");
        if (request.position() != null) {
            parameters.put("position", request.position());
        }
        builder.parameters(parameters);

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input);
        return input;
    }

    public static SealAppendObjectResult toSealAppendObject(OperationOutput output) {
        Object innerBody = null;
        return SealAppendObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    public static OperationInput fromSelectObject(SelectObjectRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("SelectObject")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("x-oss-process", request.process());
        builder.parameters(parameters);

        // body
        BinaryData body = SerdeUtils.serializeXmlBody(request.selectRequest());
        builder.body(body);

        builder.bucket(request.bucket());
        builder.key(request.key());

        // async data consumer
        if (request.dataConsumerSupplier() != null) {
            final BinaryDataConsumerSupplier userSupplier = request.dataConsumerSupplier();
            BinaryDataConsumerSupplier decodingSupplier = new BinaryDataConsumerSupplier() {
                @Override
                public Object get() {
                    Object inner = userSupplier.get();
                    if (inner instanceof WritableByteChannel) {
                        return new SelectFrameDecodingChannel((WritableByteChannel) inner);
                    }
                    throw new UnsupportedOperationException(
                            "Only supports WritableByteChannel, but got " + inner.getClass().getName());
                }

                @Override
                public boolean isReplayable() {
                    return userSupplier.isReplayable();
                }

                @Override
                public boolean autoRelease() {
                    return userSupplier.autoRelease();
                }
            };
            AttributeMap opMetadata = AttributeMap.empty();
            opMetadata.put(AttributeKey.RESPONSE_CONSUMER_SUPPLIER, decodingSupplier);
            builder.opMetadata(opMetadata);
        }

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }


    public static SelectObjectResult toSelectObject(OperationOutput output) {
        InputStream is;
        BinaryData body = output.body().orElse(null);
        if (body instanceof InputStreamBinaryData) {
            is = ((InputStreamBinaryData) body).unwrap();
        } else if (body != null) {
            is = body.toStream();
        } else {
            is = new ByteArrayInputStream(new byte[0]);
        }

        boolean isRawOutput = "true".equalsIgnoreCase(output.headers.get("x-oss-select-output-raw"));
        InputStream parsedStream = isRawOutput ? is : new SelectObjectInputStream(is);
        return SelectObjectResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(parsedStream)
                .build();
    }


    public static OperationInput fromCreateSelectObjectMeta(CreateSelectObjectMetaRequest request) {
        OperationInput.Builder builder = OperationInput.newBuilder()
                .opName("CreateSelectObjectMeta")
                .method("POST");

        // default headers
        Map<String, String> headers = MapUtils.caseInsensitiveMap();
        headers.put("Content-Type", "application/xml");
        builder.headers(headers);

        // parameters
        Map<String, String> parameters = MapUtils.caseSensitiveMap();
        parameters.put("x-oss-process", request.process());
        builder.parameters(parameters);

        // body
        SelectMetaRequest metaRequest = request.selectMetaRequest();
        if (metaRequest != null) {
            try {
                XmlMapper xmlMapper = new XmlMapper();
                xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                String rootName = "CsvMetaRequest";
                if (metaRequest.inputSerialization() != null && metaRequest.inputSerialization().json() != null) {
                    rootName = "JsonMetaRequest";
                }
                String xml = xmlMapper.writer(new DefaultXmlPrettyPrinter())
                        .withRootName(rootName)
                        .writeValueAsString(metaRequest);

                builder.body(BinaryData.fromBytes(xml.getBytes(StandardCharsets.UTF_8)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        builder.bucket(request.bucket());
        builder.key(request.key());

        OperationInput input = builder.build();
        SerdeUtils.serializeInput(request, input, SerdeUtils.addContentMd5);
        return input;
    }



    public static CreateSelectObjectMetaResult toCreateSelectObjectMeta(OperationOutput output) {
        InputStream is;
        BinaryData body = output.body().orElse(null);
        if (body instanceof InputStreamBinaryData) {
            is = ((InputStreamBinaryData) body).unwrap();
        } else if (body != null) {
            is = body.toStream();
        } else {
            is = new ByteArrayInputStream(new byte[0]);
        }

        SelectObjectMeta innerBody = parseCreateSelectMetaStream(is);
        return CreateSelectObjectMetaResult.newBuilder()
                .headers(output.headers)
                .status(output.status)
                .statusCode(output.statusCode)
                .innerBody(innerBody)
                .build();
    }

    private static SelectObjectMeta parseCreateSelectMetaStream(InputStream is) {
        try {
            byte[] frameTypeBytes = new byte[4];
            byte[] payloadLengthBytes = new byte[4];
            byte[] headerChecksumBytes = new byte[4];
            byte[] scannedDataBytes = new byte[8];
            byte[] payloadChecksumBytes = new byte[4];

            while (true) {
                readFully(is, frameTypeBytes);
                if (frameTypeBytes[0] != 1) {
                    throw new RuntimeException("Invalid select version: " + frameTypeBytes[0]);
                }
                readFully(is, payloadLengthBytes);
                readFully(is, headerChecksumBytes);
                readFully(is, scannedDataBytes);

                int payloadLength = ByteBuffer.wrap(payloadLengthBytes).getInt();

                frameTypeBytes[0] = 0;
                int frameType = ByteBuffer.wrap(frameTypeBytes).getInt();

                if (frameType == 8388612) {
                    skipFully(is, payloadLength - 4);
                    continue;
                } else if (frameType == 8388614 || frameType == 8388615) {
                    boolean isCsv = (frameType == 8388614);
                    int actualPayloadLength = payloadLength - 8;

                    byte[] totalScanSizeBytes = new byte[8];
                    byte[] statusBytes = new byte[4];
                    byte[] splitBytes = new byte[4];
                    byte[] totalLineBytes = new byte[8];
                    byte[] columnBytes = new byte[4];

                    readFully(is, totalScanSizeBytes);
                    readFully(is, statusBytes);
                    readFully(is, splitBytes);
                    readFully(is, totalLineBytes);

                    if (isCsv) {
                        readFully(is, columnBytes);
                    }

                    int errorMessageSize = isCsv ? (actualPayloadLength - 28) : (actualPayloadLength - 24);
                    String errorMessage = "";
                    if (errorMessageSize > 0) {
                        byte[] errorMessageBytes = new byte[errorMessageSize];
                        readFully(is, errorMessageBytes);
                        errorMessage = new String(errorMessageBytes, StandardCharsets.UTF_8);
                    }

                    readFully(is, payloadChecksumBytes);

                    long status = ByteBuffer.wrap(statusBytes).getInt() & 0xFFFFFFFFL;
                    if (status / 100 != 2) {
                        throw new RuntimeException("CreateSelectObjectMeta failed with status: " + status + ", error: " + errorMessage);
                    }

                    long offset = ByteBuffer.wrap(scannedDataBytes).getLong();
                    long totalScanned = ByteBuffer.wrap(totalScanSizeBytes).getLong();
                    long splitsCount = ByteBuffer.wrap(splitBytes).getInt() & 0xFFFFFFFFL;
                    long rowsCount = ByteBuffer.wrap(totalLineBytes).getLong();
                    Long columnsCount = isCsv ? (ByteBuffer.wrap(columnBytes).getInt() & 0xFFFFFFFFL) : null;

                    return SelectObjectMeta.newBuilder()
                            .offset(offset)
                            .totalScanned(totalScanned)
                            .status(status)
                            .splitsCount(splitsCount)
                            .rowsCount(rowsCount)
                            .columnsCount(columnsCount)
                            .errorMessage(errorMessage.isEmpty() ? null : errorMessage)
                            .build();
                } else {
                    throw new RuntimeException("Unsupported frame type: " + frameType);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void readFully(InputStream is, byte[] buf) throws IOException {
        int totalRead = 0;
        while (totalRead < buf.length) {
            int read = is.read(buf, totalRead, buf.length - totalRead);
            if (read < 0) {
                throw new IOException("Unexpected end of stream, need " + (buf.length - totalRead) + " more bytes");
            }
            totalRead += read;
        }
    }

    private static void skipFully(InputStream is, long n) throws IOException {
        long remaining = n;
        while (remaining > 0) {
            long skipped = is.skip(remaining);
            if (skipped <= 0) {
                throw new IOException("Failed to skip " + remaining + " bytes");
            }
            remaining -= skipped;
        }
    }
}