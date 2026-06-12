package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DoMetaQueryResultTest {

    @Test
    public void testEmptyBuilder() {
        DoMetaQueryResult result = DoMetaQueryResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.nextToken()).isNull();
        assertThat(result.files()).isNull();
        assertThat(result.aggregations()).isNull();
        assertThat(result.totalHits()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Content-Length", "1024"
        );

        DoMetaQueryResult result = DoMetaQueryResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "Content-Length", "0"
        );

        DoMetaQueryResult original = DoMetaQueryResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        DoMetaQueryResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    // ==================== basic mode response ====================

    @Test
    public void xmlBuilderBasicMode() {
        // Reference: sdk_internal_reference(3).md §4 DoMetaQuery basic response
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<MetaQuery>"
                + "  <NextToken>next-page-token-abc</NextToken>"
                + "  <TotalHits>123</TotalHits>"
                + "  <Files>"
                + "    <File>"
                + "      <Filename>photos/sunset.jpg</Filename>"
                + "      <Size>2097152</Size>"
                + "      <FileModifiedTime>2026-05-19T15:30:00.000+08:00</FileModifiedTime>"
                + "      <ContentType>image/jpeg</ContentType>"
                + "      <ObjectACL>default</ObjectACL>"
                + "      <StorageClass>Standard</StorageClass>"
                + "    </File>"
                + "    <File>"
                + "      <Filename>photos/mountain.png</Filename>"
                + "      <Size>5242880</Size>"
                + "    </File>"
                + "  </Files>"
                + "  <Aggregations>"
                + "    <Aggregation>"
                + "      <Field>Size</Field>"
                + "      <Operation>sum</Operation>"
                + "      <Value>12345678</Value>"
                + "    </Aggregation>"
                + "  </Aggregations>"
                + "</MetaQuery>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .headers(MapUtils.of("x-oss-request-id", "req-do-meta-query-basic"))
                .build();

        DoMetaQueryResult result = SerdeDatasetBasic.toDoMetaQuery(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-do-meta-query-basic");

        // Verify basic mode fields
        assertThat(result.nextToken()).isEqualTo("next-page-token-abc");
        assertThat(result.totalHits()).isEqualTo(123L);

        // Verify files
        assertThat(result.files()).isNotNull();
        assertThat(result.files()).hasSize(2);
        assertThat(result.files().get(0).filename()).isEqualTo("photos/sunset.jpg");
        assertThat(result.files().get(0).size()).isEqualTo(2097152L);
        assertThat(result.files().get(1).filename()).isEqualTo("photos/mountain.png");

        // Verify aggregations
        assertThat(result.aggregations()).isNotNull();
        assertThat(result.aggregations()).hasSize(1);
        assertThat(result.aggregations().get(0).field()).isEqualTo("Size");
        assertThat(result.aggregations().get(0).operation()).isEqualTo("sum");
        assertThat(result.aggregations().get(0).value()).isEqualTo(12345678.0);
    }

    @Test
    public void xmlBuilderBasicModeWithGroupBy() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<MetaQuery>"
                + "  <TotalHits>50</TotalHits>"
                + "  <Aggregations>"
                + "    <Aggregation>"
                + "      <Field>StorageClass</Field>"
                + "      <Operation>group_by</Operation>"
                + "      <Groups>"
                + "        <Group><Value>Standard</Value><Count>30</Count></Group>"
                + "        <Group><Value>IA</Value><Count>20</Count></Group>"
                + "      </Groups>"
                + "    </Aggregation>"
                + "  </Aggregations>"
                + "</MetaQuery>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        DoMetaQueryResult result = SerdeDatasetBasic.toDoMetaQuery(output);

        assertThat(result.totalHits()).isEqualTo(50L);
        assertThat(result.aggregations()).hasSize(1);
        assertThat(result.aggregations().get(0).field()).isEqualTo("StorageClass");
        assertThat(result.aggregations().get(0).operation()).isEqualTo("group_by");
        assertThat(result.aggregations().get(0).groups()).hasSize(2);
        assertThat(result.aggregations().get(0).groups().get(0).value()).isEqualTo("Standard");
        assertThat(result.aggregations().get(0).groups().get(0).count()).isEqualTo(30L);
        assertThat(result.aggregations().get(0).groups().get(1).value()).isEqualTo("IA");
        assertThat(result.aggregations().get(0).groups().get(1).count()).isEqualTo(20L);
    }

    // ==================== semantic mode response ====================

    @Test
    public void xmlBuilderSemanticMode() {
        // Reference: sdk_internal_reference(3).md §4 DoMetaQuery semantic response
        // semantic mode: no NextToken, no Aggregations
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<MetaQuery>"
                + "  <TotalHits>2</TotalHits>"
                + "  <Files>"
                + "    <File>"
                + "      <Filename>photos/cat-in-living-room.jpg</Filename>"
                + "      <Size>3145728</Size>"
                + "      <OSSStorageClass>Standard</OSSStorageClass>"
                + "      <Labels>"
                + "        <Label>"
                + "          <LabelName>cat</LabelName>"
                + "          <LabelConfidence>0.98</LabelConfidence>"
                + "        </Label>"
                + "      </Labels>"
                + "    </File>"
                + "    <File>"
                + "      <Filename>photos/kitten-sofa.jpg</Filename>"
                + "      <Size>2621440</Size>"
                + "    </File>"
                + "  </Files>"
                + "</MetaQuery>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .headers(MapUtils.of("x-oss-request-id", "req-do-meta-query-semantic"))
                .build();

        DoMetaQueryResult result = SerdeDatasetBasic.toDoMetaQuery(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-do-meta-query-semantic");

        // semantic mode: no nextToken, no aggregations
        assertThat(result.nextToken()).isNull();
        assertThat(result.aggregations()).isNull();
        assertThat(result.totalHits()).isEqualTo(2L);

        // Verify files
        assertThat(result.files()).isNotNull();
        assertThat(result.files()).hasSize(2);
        assertThat(result.files().get(0).filename()).isEqualTo("photos/cat-in-living-room.jpg");
        assertThat(result.files().get(0).size()).isEqualTo(3145728L);
        assertThat(result.files().get(0).ossStorageClass()).isEqualTo("Standard");
        assertThat(result.files().get(0).labels()).isNotNull();
        assertThat(result.files().get(0).labels()).hasSize(1);
        assertThat(result.files().get(0).labels().get(0).labelName()).isEqualTo("cat");
        assertThat(result.files().get(0).labels().get(0).labelConfidence()).isEqualTo(0.98f);
        assertThat(result.files().get(1).filename()).isEqualTo("photos/kitten-sofa.jpg");
    }
}
