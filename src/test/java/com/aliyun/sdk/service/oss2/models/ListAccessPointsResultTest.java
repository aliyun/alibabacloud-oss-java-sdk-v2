package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class ListAccessPointsResultTest {

    @Test
    public void testEmptyBuilder() {
        ListAccessPointsResult result = ListAccessPointsResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Date", "Mon, 01 Jan 2024 12:00:00 GMT"
        );

        ListAccessPointsResult result = ListAccessPointsResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Date")).isEqualTo("Mon, 01 Jan 2024 12:00:00 GMT");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "Date", "Tue, 02 Jan 2024 12:00:00 GMT"
        );

        ListAccessPointsResult original = ListAccessPointsResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListAccessPointsResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("Date")).isEqualTo("Tue, 02 Jan 2024 12:00:00 GMT");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String xmlContent = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ListAccessPointsResult>\n" +
                "  <IsTruncated>true</IsTruncated>\n" +
                "  <NextContinuationToken>abc</NextContinuationToken>\n" +
                "  <AccountId>111933544165****</AccountId>\n" +
                "  <AccessPoints>\n" +
                "    <AccessPoint>\n" +
                "      <Bucket>oss-example</Bucket>\n" +
                "      <AccessPointName>ap-01</AccessPointName>\n" +
                "      <Alias>ap-01-ossalias</Alias>\n" +
                "      <NetworkOrigin>vpc</NetworkOrigin>\n" +
                "      <VpcConfiguration>\n" +
                "        <VpcId>vpc-t4nlw426y44rd3iq4****</VpcId>\n" +
                "      </VpcConfiguration>\n" +
                "      <Status>enable</Status>\n" +
                "    </AccessPoint>\n" +
                "    <AccessPoint>\n" +
                "      <Bucket>oss-example2</Bucket>\n" +
                "      <AccessPointName>ap-02</AccessPointName>\n" +
                "      <Alias>ap-02-ossalias</Alias>\n" +
                "      <NetworkOrigin>internet</NetworkOrigin>\n" +
                "      <VpcConfiguration>\n" +
                "        <VpcId>vpc-t4n3iq4****</VpcId>\n" +
                "      </VpcConfiguration>\n" +
                "      <Status>creating</Status>\n" +
                "    </AccessPoint>\n" +
                "  </AccessPoints>\n" +
                "</ListAccessPointsResult>";
        
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "Date", "Mon, 01 Jan 2024 12:00:00 GMT"
        );

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xmlContent))
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        ListAccessPointsResult result = SerdeAccessPoint.toListAccessPoints(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Date")).isEqualTo("Mon, 01 Jan 2024 12:00:00 GMT");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.accessPointsResult().isTruncated()).isEqualTo("true");
        assertThat(result.accessPointsResult().nextContinuationToken()).isEqualTo("abc");
        assertThat(result.accessPointsResult().accountId()).isEqualTo("111933544165****");
        assertThat(result.accessPointsResult().accessPoints().accessPoint()).hasSize(2);
        
        AccessPoint firstAp = result.accessPointsResult().accessPoints().accessPoint().get(0);
        assertThat(firstAp.bucket()).isEqualTo("oss-example");
        assertThat(firstAp.accessPointName()).isEqualTo("ap-01");
        assertThat(firstAp.alias()).isEqualTo("ap-01-ossalias");
        assertThat(firstAp.networkOrigin()).isEqualTo("vpc");
        assertThat(firstAp.vpcConfiguration().vpcId()).isEqualTo("vpc-t4nlw426y44rd3iq4****");
        assertThat(firstAp.status()).isEqualTo("enable");
        
        AccessPoint secondAp = result.accessPointsResult().accessPoints().accessPoint().get(1);
        assertThat(secondAp.bucket()).isEqualTo("oss-example2");
        assertThat(secondAp.accessPointName()).isEqualTo("ap-02");
        assertThat(secondAp.alias()).isEqualTo("ap-02-ossalias");
        assertThat(secondAp.networkOrigin()).isEqualTo("internet");
        assertThat(secondAp.vpcConfiguration().vpcId()).isEqualTo("vpc-t4n3iq4****");
        assertThat(secondAp.status()).isEqualTo("creating");
    }
}