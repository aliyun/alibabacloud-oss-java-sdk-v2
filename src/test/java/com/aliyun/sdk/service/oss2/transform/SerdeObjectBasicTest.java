package com.aliyun.sdk.service.oss2.transform;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.models.DeleteMultipleObjectsResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SerdeObjectBasicTest {

    @Test
    void toDeleteMultipleObjects() {
        // no url encoding type
        String xml = "<DeleteResult>\n" +
                "  <Deleted>\n" +
                "     <Key>multipart.data</Key>\n" +
                "     <VersionId>CAEQMhiBgIC4hZ2aqRciIDQzYjc4NDY5NzBiYjQ5MzVjNzVmMTAwMTdl****</VersionId>\n" +
                "     <DeleteMarker>true</DeleteMarker>\n" +
                "     <DeleteMarkerVersionId>CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****</DeleteMarkerVersionId>" +
                "  </Deleted>\n" +
                "  <Deleted>\n" +
                "     <Key>test.jpg</Key>\n" +
                "     <VersionId>CAEQMhiBgIDJgZ2aqRciIDQzYjc4NDY5NzBiYjQ5MzVjNzVmMTAwMTdl****</VersionId>\n" +
                "  </Deleted>\n" +
                "</DeleteResult>";

        DeleteMultipleObjectsResult result = SerdeObjectBasic.toDeleteMultipleObjects(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.deletedObjects()).hasSize(2);
        assertThat(result.deletedObjects().get(0).key()).isEqualTo("multipart.data");
        assertThat(result.deletedObjects().get(0).versionId()).isEqualTo("CAEQMhiBgIC4hZ2aqRciIDQzYjc4NDY5NzBiYjQ5MzVjNzVmMTAwMTdl****");
        assertThat(result.deletedObjects().get(0).deleteMarker()).isEqualTo(true);
        assertThat(result.deletedObjects().get(0).deleteMarkerVersionId()).isEqualTo("CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****");
        assertThat(result.deletedObjects().get(1).key()).isEqualTo("test.jpg");
        assertThat(result.deletedObjects().get(1).versionId()).isEqualTo("CAEQMhiBgIDJgZ2aqRciIDQzYjc4NDY5NzBiYjQ5MzVjNzVmMTAwMTdl****");
        assertThat(result.deletedObjects().get(1).deleteMarker()).isNull();
        assertThat(result.deletedObjects().get(1).deleteMarkerVersionId()).isNull();
        assertThat(result.encodingType()).isNull();

        // has url encoding type
        xml = "<DeleteResult>\n" +
                "  <EncodingType>url</EncodingType>\n" +
                "  <Deleted>\n" +
                "     <Key>multi%2Fpart.data</Key>\n" +
                "     <VersionId>CAEQMhiBgIC4hZ2aqRciIDQzYjc4NDY5NzBiYjQ5MzVjNzVmMTAwMTdl****</VersionId>\n" +
                "     <DeleteMarker>false</DeleteMarker>\n" +
                "     <DeleteMarkerVersionId>CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****</DeleteMarkerVersionId>" +
                "  </Deleted>\n" +
                "  <Deleted>\n" +
                "     <Key>test%2F.jpg</Key>\n" +
                "     <VersionId>CAEQMhiBgIDJgZ2aqRciIDQzYjc4NDY5NzBiYjQ5MzVjNzVmMTAwMTdl****</VersionId>\n" +
                "  </Deleted>\n" +
                "</DeleteResult>";

        result = SerdeObjectBasic.toDeleteMultipleObjects(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.encodingType()).isEqualTo("url");
        assertThat(result.deletedObjects()).hasSize(2);
        assertThat(result.deletedObjects().get(0).key()).isEqualTo("multi/part.data");
        assertThat(result.deletedObjects().get(0).versionId()).isEqualTo("CAEQMhiBgIC4hZ2aqRciIDQzYjc4NDY5NzBiYjQ5MzVjNzVmMTAwMTdl****");
        assertThat(result.deletedObjects().get(0).deleteMarker()).isEqualTo(false);
        assertThat(result.deletedObjects().get(0).deleteMarkerVersionId()).isEqualTo("CAEQMhiBgIDXiaaB0BYiIGQzYmRkZGUxMTM1ZDRjOTZhNjk4YjRjMTAyZjhl****");
        assertThat(result.deletedObjects().get(1).key()).isEqualTo("test/.jpg");
        assertThat(result.deletedObjects().get(1).versionId()).isEqualTo("CAEQMhiBgIDJgZ2aqRciIDQzYjc4NDY5NzBiYjQ5MzVjNzVmMTAwMTdl****");
        assertThat(result.deletedObjects().get(1).deleteMarker()).isNull();
        assertThat(result.deletedObjects().get(1).deleteMarkerVersionId()).isNull();
    }

    @Test
    void toDeleteMultipleObjects_ensureList() {
        // no Deleted element
        String xml = "<DeleteResult>\n" +
                "</DeleteResult>";

        DeleteMultipleObjectsResult result = SerdeObjectBasic.toDeleteMultipleObjects(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.deletedObjects()).isEmpty();

        // empty
        xml = "";
        result = SerdeObjectBasic.toDeleteMultipleObjects(
                OperationOutput.newBuilder()
                        .body(BinaryData.fromString(xml))
                        .headers(MapUtils.caseInsensitiveMap())
                        .statusCode(200)
                        .build());

        assertThat(result.deletedObjects()).isEmpty();
    }
}
