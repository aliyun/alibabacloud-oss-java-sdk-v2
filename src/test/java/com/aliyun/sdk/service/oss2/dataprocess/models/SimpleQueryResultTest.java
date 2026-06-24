package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleQueryResultTest {

    @Test
    public void testEmptyBuilder() {
        SimpleQueryResult result = SimpleQueryResult.newBuilder().build();
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

        SimpleQueryResult result = SimpleQueryResult.newBuilder()
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

        SimpleQueryResult original = SimpleQueryResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        SimpleQueryResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §10 SimpleQuery response
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<MetaQuery>"
                + "  <NextToken>next-page-token-xyz</NextToken>"
                + "  <TotalHits>42</TotalHits>"
                + "  <Files>"
                + "    <File>"
                + "      <Filename>photos/sunset.jpg</Filename>"
                + "      <Size>2097152</Size>"
                + "      <FileModifiedTime>2026-05-19T15:30:00.000+08:00</FileModifiedTime>"
                + "      <ContentType>image/jpeg</ContentType>"
                + "      <ObjectACL>default</ObjectACL>"
                + "      <OSSStorageClass>Standard</OSSStorageClass>"
                + "      <ETag>\"D41D8CD98F00B204E9800998ECF8427E\"</ETag>"
                + "      <OSSTagging>"
                + "        <Key>routing-dataset</Key>"
                + "        <Value>photos-2026</Value>"
                + "      </OSSTagging>"
                + "      <Labels>"
                + "        <Label>"
                + "          <LabelName>夕阳</LabelName>"
                + "          <LabelConfidence>0.98</LabelConfidence>"
                + "        </Label>"
                + "      </Labels>"
                + "    </File>"
                + "  </Files>"
                + "</MetaQuery>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .headers(MapUtils.of("x-oss-request-id", "req-simple-query"))
                .build();

        SimpleQueryResult result = SerdeDatasetBasic.toSimpleQuery(output);

        assertThat(result).isNotNull();
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-simple-query");

        // Verify response fields
        assertThat(result.nextToken()).isEqualTo("next-page-token-xyz");
        assertThat(result.totalHits()).isEqualTo(42L);

        // Verify files
        assertThat(result.files()).isNotNull();
        assertThat(result.files()).hasSize(1);
        File file = result.files().get(0);
        assertThat(file.filename()).isEqualTo("photos/sunset.jpg");
        assertThat(file.size()).isEqualTo(2097152L);
        assertThat(file.fileModifiedTime()).isEqualTo("2026-05-19T15:30:00.000+08:00");
        assertThat(file.contentType()).isEqualTo("image/jpeg");
        assertThat(file.objectAcl()).isEqualTo("default");
        assertThat(file.ossStorageClass()).isEqualTo("Standard");
        assertThat(file.eTag()).isEqualTo("\"D41D8CD98F00B204E9800998ECF8427E\"");

        // Verify labels
        assertThat(file.labels()).isNotNull();
        assertThat(file.labels()).hasSize(1);
        assertThat(file.labels().get(0).labelName()).isEqualTo("夕阳");
        assertThat(file.labels().get(0).labelConfidence()).isEqualTo(0.98f);
    }
}
