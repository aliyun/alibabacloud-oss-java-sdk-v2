package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectFcAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListAccessPointsForObjectProcessResultTest {

    @Test
    public void testEmptyBuilder() {
        ListAccessPointsForObjectProcessResult result = ListAccessPointsForObjectProcessResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListAccessPointsForObjectProcessResult result = ListAccessPointsForObjectProcessResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListAccessPointsForObjectProcessResult original = ListAccessPointsForObjectProcessResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListAccessPointsForObjectProcessResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListAccessPointsForObjectProcessResult>\n" +
                "   <IsTruncated>true</IsTruncated>\n" +
                "   <NextContinuationToken>abc</NextContinuationToken>\n" +
                "   <AccountId>111933544165****</AccountId>\n" +
                "   <AccessPointsForObjectProcess>\n" +
                "      <AccessPointForObjectProcess>\n" +
                "          <AccessPointNameForObjectProcess>fc-ap-01</AccessPointNameForObjectProcess>\n" +
                "          <AccessPointForObjectProcessAlias>fc-ap-01-3b00521f653d2b3223680ec39dbbe2****-opapalias</AccessPointForObjectProcessAlias>\n" +
                "          <AccessPointName>fc-01</AccessPointName>\n" +
                "          <Status>enable</Status>\n" +
                "      </AccessPointForObjectProcess>\n" +
                "   </AccessPointsForObjectProcess>\n" +
                "</ListAccessPointsForObjectProcessResult>";
        
        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        ListAccessPointsForObjectProcessResult listResult = SerdeBucketObjectFcAccessPoint.toListAccessPointsForObjectProcess(output);
        assertThat(listResult).isNotNull();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        OperationOutput fullOutput = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListAccessPointsForObjectProcessResult result = SerdeBucketObjectFcAccessPoint.toListAccessPointsForObjectProcess(fullOutput);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        
        ListAccessPointsForObjectProcessResultXml accessPointsResult = result.accessPointsForObjectProcessResult();
        assertThat(accessPointsResult).isNotNull();
        assertThat(accessPointsResult.isTruncated()).isEqualTo(true);
        assertThat(accessPointsResult.nextContinuationToken()).isEqualTo("abc");
        assertThat(accessPointsResult.accountId()).isEqualTo("111933544165****");
        
        AccessPointsForObjectProcess accessPoints = accessPointsResult.accessPointsForObjectProcess();
        assertThat(accessPoints).isNotNull();
        assertThat(accessPoints.accessPointForObjectProcess()).isNotNull();
        assertThat(accessPoints.accessPointForObjectProcess().size()).isEqualTo(1);
        
        AccessPointForObjectProcess accessPoint = accessPoints.accessPointForObjectProcess().get(0);
        assertThat(accessPoint.accessPointNameForObjectProcess()).isEqualTo("fc-ap-01");
        assertThat(accessPoint.accessPointForObjectProcessAlias()).isEqualTo("fc-ap-01-3b00521f653d2b3223680ec39dbbe2****-opapalias");
        assertThat(accessPoint.accessPointName()).isEqualTo("fc-01");
        assertThat(accessPoint.status()).isEqualTo("enable");
    }
}