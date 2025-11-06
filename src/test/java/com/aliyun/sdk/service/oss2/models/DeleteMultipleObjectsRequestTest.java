package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteMultipleObjectsRequestTest {
    @Test
    public void testEmptyBuilder() {
        DeleteMultipleObjectsRequest request = DeleteMultipleObjectsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.encodingType()).isNull();
        assertThat(request.deleteObjects()).isNull();
        assertThat(request.quiet()).isNull();
        assertThat(request.requestPayer()).isNull();
        assertThat(request.delete()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        List<DeleteObject> deleteObjects = Arrays.asList(
                DeleteObject.newBuilder()
                        .key("multipart.data")
                        .versionId("CAEQNRiBgICEoPiC0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****")
                        .build(),
                DeleteObject.newBuilder()
                        .key("test.jpg")
                        .build()
        );

        List<ObjectIdentifier> objectIdentifiers = Arrays.asList(
                ObjectIdentifier.newBuilder()
                        .key("multipart.data")
                        .versionId("CAEQNRiBgICEoPiC0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****")
                        .build(),
                ObjectIdentifier.newBuilder()
                        .key("test.jpg")
                        .build()
        );

        Delete delete = Delete.newBuilder()
                .quiet(false)
                .objectIdentifiers(objectIdentifiers)
                .build();

        DeleteMultipleObjectsRequest request = DeleteMultipleObjectsRequest.newBuilder()
                .bucket("examplebucket")
                .encodingType("url")
                .deleteObjects(deleteObjects)
                .quiet(false)
                .delete(delete)
                .requestPayer("requester")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.encodingType()).isEqualTo("url");
        assertThat(request.deleteObjects()).isEqualTo(deleteObjects);
        assertThat(request.quiet()).isEqualTo(false);
        assertThat(request.delete()).isEqualTo(delete);
        assertThat(request.requestPayer()).isEqualTo("requester");

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        List<DeleteObject> deleteObjects = Arrays.asList(
                DeleteObject.newBuilder()
                        .key("sample.data")
                        .versionId("VERSION123456")
                        .build(),
                DeleteObject.newBuilder()
                        .key("sample.jpg")
                        .build()
        );

        List<ObjectIdentifier> objectIdentifiers = Arrays.asList(
                ObjectIdentifier.newBuilder()
                        .key("sample.data")
                        .versionId("VERSION123456")
                        .build(),
                ObjectIdentifier.newBuilder()
                        .key("sample.jpg")
                        .build()
        );

        Delete delete = Delete.newBuilder()
                .quiet(true)
                .objectIdentifiers(objectIdentifiers)
                .build();

        DeleteMultipleObjectsRequest original = DeleteMultipleObjectsRequest.newBuilder()
                .bucket("testbucket")
                .encodingType("url")
                .deleteObjects(deleteObjects)
                .quiet(true)
                .delete(delete)
                .requestPayer("requester")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        DeleteMultipleObjectsRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.encodingType()).isEqualTo("url");
        assertThat(copy.deleteObjects()).isEqualTo(deleteObjects);
        assertThat(copy.quiet()).isEqualTo(true);
        assertThat(copy.delete()).isEqualTo(delete);
        assertThat(copy.requestPayer()).isEqualTo("requester");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        List<DeleteObject> deleteObjects = Arrays.asList(
                DeleteObject.newBuilder()
                        .key("object1.txt")
                        .versionId("VERSION654321")
                        .build(),
                DeleteObject.newBuilder()
                        .key("object2.txt")
                        .build()
        );

        List<ObjectIdentifier> objectIdentifiers = Arrays.asList(
                ObjectIdentifier.newBuilder()
                        .key("object1.txt")
                        .versionId("VERSION654321")
                        .build(),
                ObjectIdentifier.newBuilder()
                        .key("object2.txt")
                        .build()
        );

        Delete delete = Delete.newBuilder()
                .quiet(false)
                .objectIdentifiers(objectIdentifiers)
                .build();

        DeleteMultipleObjectsRequest request = DeleteMultipleObjectsRequest.newBuilder()
                .bucket("anotherbucket")
                .encodingType("url")
                .deleteObjects(deleteObjects)
                .quiet(false)
                .delete(delete)
                .requestPayer("requester")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.encodingType()).isEqualTo("url");
        assertThat(request.deleteObjects()).isEqualTo(deleteObjects);
        assertThat(request.quiet()).isEqualTo(false);
        assertThat(request.delete()).isEqualTo(delete);
        assertThat(request.requestPayer()).isEqualTo("requester");
    }
    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        List<DeleteObject> deleteObjects = Arrays.asList(
                DeleteObject.newBuilder()
                        .key("multipart.data")
                        .versionId("CAEQNRiBgICEoPiC0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****")
                        .build(),
                DeleteObject.newBuilder()
                        .key("test.jpg")
                        .build()
        );

        DeleteMultipleObjectsRequest request = DeleteMultipleObjectsRequest.newBuilder()
                .bucket("examplebucket")
                .deleteObjects(deleteObjects)
                .quiet(false)
                .build();

        OperationInput input = SerdeObjectBasic.fromDeleteMultipleObjects(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("delete")).isEqualTo("");
        assertThat(input.parameters().get("encoding-type")).isEqualTo("url");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<Delete>");
        assertThat(xmlContent).contains("<Quiet>false</Quiet>");
        assertThat(xmlContent).contains("<Object>");
        assertThat(xmlContent).contains("<Key>multipart.data</Key>");
        assertThat(xmlContent).contains("<VersionId>CAEQNRiBgICEoPiC0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****</VersionId>");
        assertThat(xmlContent).contains("<Key>test.jpg</Key>");
        assertThat(xmlContent).contains("</Object>");
        assertThat(xmlContent).contains("</Delete>");
    }

    @Test
    public void xmlBuilderWithNewMode() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        List<ObjectIdentifier> objectIdentifiers = Arrays.asList(
                ObjectIdentifier.newBuilder()
                        .key("multipart.data")
                        .versionId("CAEQNRiBgICEoPiC0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****")
                        .build(),
                ObjectIdentifier.newBuilder()
                        .key("test.jpg")
                        .build()
        );

        Delete delete = Delete.newBuilder()
                .quiet(true)
                .objectIdentifiers(objectIdentifiers)
                .build();

        DeleteMultipleObjectsRequest request = DeleteMultipleObjectsRequest.newBuilder()
                .bucket("examplebucket")
                .delete(delete)
                .build();

        OperationInput input = SerdeObjectBasic.fromDeleteMultipleObjects(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("delete")).isEqualTo("");
        assertThat(input.parameters().get("encoding-type")).isEqualTo("url");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<Delete>");
        assertThat(xmlContent).contains("<Quiet>true</Quiet>");
        assertThat(xmlContent).contains("<Object>");
        assertThat(xmlContent).contains("<Key>multipart.data</Key>");
        assertThat(xmlContent).contains("<VersionId>CAEQNRiBgICEoPiC0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****</VersionId>");
        assertThat(xmlContent).contains("<Key>test.jpg</Key>");
        assertThat(xmlContent).contains("</Object>");
        assertThat(xmlContent).contains("</Delete>");
    }
}