package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.internal.DeleteResultXml;
import com.aliyun.sdk.service.oss2.transform.SerdeObjectBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteMultipleObjectsResultTest {

    @Test
    public void testEmptyBuilder() {
        DeleteMultipleObjectsResult result = DeleteMultipleObjectsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        List<DeletedInfo> deletedObjects = Arrays.asList(
                DeletedInfo.newBuilder()
                        .key("multipart.data")
                        .versionId("CAEQNRiBgICEoPiC0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****")
                        .deleteMarker(true)
                        .deleteMarkerVersionId("CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****")
                        .build(),
                DeletedInfo.newBuilder()
                        .key("test.jpg")
                        .versionId("0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****")
                        .deleteMarker(true)
                        .deleteMarkerVersionId("CAEQMhiBgIDB3aWB0BYiIGUzYTA3YzliMzVmNzRkZGM5NjllYTVlMjYyYWEy****")
                        .build()
        );

        DeleteResultXml deleteResultXml = new DeleteResultXml();
        deleteResultXml.deleted = deletedObjects;
        deleteResultXml.encodingType = "url";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        DeleteMultipleObjectsResult result = DeleteMultipleObjectsResult.newBuilder()
                .headers(headers)
                .innerBody(deleteResultXml)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.deletedObjects()).hasSize(2);

        DeletedInfo firstDeleted = result.deletedObjects().get(0);
        assertThat(firstDeleted.key()).isEqualTo("multipart.data");
        assertThat(firstDeleted.versionId()).isEqualTo("CAEQNRiBgICEoPiC0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****");
        assertThat(firstDeleted.deleteMarker()).isEqualTo(true);
        assertThat(firstDeleted.deleteMarkerVersionId()).isEqualTo("CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****");

        DeletedInfo secondDeleted = result.deletedObjects().get(1);
        assertThat(secondDeleted.key()).isEqualTo("test.jpg");
        assertThat(secondDeleted.versionId()).isEqualTo("0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****");
        assertThat(secondDeleted.deleteMarker()).isEqualTo(true);
        assertThat(secondDeleted.deleteMarkerVersionId()).isEqualTo("CAEQMhiBgIDB3aWB0BYiIGUzYTA3YzliMzVmNzRkZGM5NjllYTVlMjYyYWEy****");
    }

    @Test
    public void testToBuilderPreserveState() {
        List<DeletedInfo> deletedObjects = Arrays.asList(
                DeletedInfo.newBuilder()
                        .key("sample.data")
                        .versionId("VERSION123456")
                        .deleteMarker(true)
                        .deleteMarkerVersionId("MARKER123456")
                        .build(),
                DeletedInfo.newBuilder()
                        .key("sample.jpg")
                        .versionId("VERSION654321")
                        .deleteMarker(false)
                        .deleteMarkerVersionId("MARKER654321")
                        .build()
        );

        DeleteResultXml deleteResultXml = new DeleteResultXml();
        deleteResultXml.deleted = deletedObjects;
        deleteResultXml.encodingType = "url";

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        DeleteMultipleObjectsResult original = DeleteMultipleObjectsResult.newBuilder()
                .headers(headers)
                .innerBody(deleteResultXml)
                .build();

        DeleteMultipleObjectsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.encodingType()).isEqualTo("url");
        assertThat(copy.deletedObjects()).hasSize(2);

        DeletedInfo firstDeleted = copy.deletedObjects().get(0);
        assertThat(firstDeleted.key()).isEqualTo("sample.data");
        assertThat(firstDeleted.versionId()).isEqualTo("VERSION123456");
        assertThat(firstDeleted.deleteMarker()).isEqualTo(true);
        assertThat(firstDeleted.deleteMarkerVersionId()).isEqualTo("MARKER123456");

        DeletedInfo secondDeleted = copy.deletedObjects().get(1);
        assertThat(secondDeleted.key()).isEqualTo("sample.jpg");
        assertThat(secondDeleted.versionId()).isEqualTo("VERSION654321");
        assertThat(secondDeleted.deleteMarker()).isEqualTo(false);
        assertThat(secondDeleted.deleteMarkerVersionId()).isEqualTo("MARKER654321");
    }

    @Test
    public void testXmlBuilderWithEncoding() throws JsonProcessingException {
        String blankXml = "";
        OperationOutput blankOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(blankXml))
                .build();
        SerdeObjectBasic.toDeleteMultipleObjects(blankOutput);

        String xml =
                "<DeleteResult>\n" +
                        "    <EncodingType>url</EncodingType>\n" +
                        "    <Deleted>\n" +
                        "       <Key>test%20key%23%40%24</Key>\n" +
                        "       <VersionId>CAEQNRiBgICEoPiC0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****</VersionId>\n" +
                        "       <DeleteMarker>true</DeleteMarker>\n" +
                        "       <DeleteMarkerVersionId>CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****</DeleteMarkerVersionId>\n" +
                        "    </Deleted>\n" +
                        "    <Deleted>\n" +
                        "       <Key>test%20image%23.jpg</Key>\n" +
                        "       <VersionId>0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****</VersionId>\n" +
                        "       <DeleteMarker>true</DeleteMarker>\n" +
                        "       <DeleteMarkerVersionId>CAEQMhiBgIDB3aWB0BYiIGUzYTA3YzliMzVmNzRkZGM5NjllYTVlMjYyYWEy****</DeleteMarkerVersionId>\n" +
                        "    </Deleted>\n" +
                        "</DeleteResult>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        DeleteMultipleObjectsResult result = SerdeObjectBasic.toDeleteMultipleObjects(output);

        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.deletedObjects()).hasSize(2);

        DeletedInfo firstDeleted = result.deletedObjects().get(0);
        assertThat(firstDeleted.key()).isEqualTo("test key#@$");
        assertThat(firstDeleted.versionId()).isEqualTo("CAEQNRiBgICEoPiC0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****");
        assertThat(firstDeleted.deleteMarker()).isEqualTo(true);
        assertThat(firstDeleted.deleteMarkerVersionId()).isEqualTo("CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****");

        DeletedInfo secondDeleted = result.deletedObjects().get(1);
        assertThat(secondDeleted.key()).isEqualTo("test image#.jpg");
        assertThat(secondDeleted.versionId()).isEqualTo("0BYiIGMxZWJmYmMzYjE0OTQ0ZmZhYjgzNzkzYjc2NjZk****");
        assertThat(secondDeleted.deleteMarker()).isEqualTo(true);
        assertThat(secondDeleted.deleteMarkerVersionId()).isEqualTo("CAEQMhiBgIDB3aWB0BYiIGUzYTA3YzliMzVmNzRkZGM5NjllYTVlMjYyYWEy****");
    }
}
