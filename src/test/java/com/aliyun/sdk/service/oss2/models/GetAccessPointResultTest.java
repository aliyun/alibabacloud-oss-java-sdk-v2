package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAccessPointResultTest {

    @Test
    public void testEmptyBuilder() {
        GetAccessPointResult result = GetAccessPointResult.newBuilder().build();
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

        GetAccessPointResult result = GetAccessPointResult.newBuilder()
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

        GetAccessPointResult original = GetAccessPointResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetAccessPointResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("Date")).isEqualTo("Tue, 02 Jan 2024 12:00:00 GMT");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String xmlContent = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<GetAccessPointResult>\n" +
                "  <AccessPointName>ap-01</AccessPointName>\n" +
                "  <Bucket>oss-example</Bucket>\n" +
                "  <AccountId>111933544165xxxx</AccountId>\n" +
                "  <NetworkOrigin>vpc</NetworkOrigin>\n" +
                "  <VpcConfiguration>\n" +
                "     <VpcId>vpc-t4nlw426y44rd3iq4xxxx</VpcId>\n" +
                "  </VpcConfiguration>\n" +
                "  <AccessPointArn>arn:acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/ap-01</AccessPointArn>\n" +
                "  <CreationDate>1626769503</CreationDate>\n" +
                "  <Alias>ap-01-ossalias</Alias>\n" +
                "  <Status>enable</Status>\n" +
                "  <Endpoints>\n" +
                "    <PublicEndpoint>ap-01.oss-cn-hangzhou.oss-accesspoint.aliyuncs.com</PublicEndpoint>\n" +
                "    <InternalEndpoint>ap-01.oss-cn-hangzhou-internal.oss-accesspoint.aliyuncs.com</InternalEndpoint>\n" +
                "  </Endpoints>\n" +
                "  <PublicAccessBlockConfiguration>\n" +
                "    <BlockPublicAccess>true</BlockPublicAccess>\n" +
                "  </PublicAccessBlockConfiguration>\n" +
                "</GetAccessPointResult>";
        
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

        GetAccessPointResult result = SerdeAccessPoint.toGetAccessPoint(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Date")).isEqualTo("Mon, 01 Jan 2024 12:00:00 GMT");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.accessPointResult().accessPointName()).isEqualTo("ap-01");
        assertThat(result.accessPointResult().bucket()).isEqualTo("oss-example");
        assertThat(result.accessPointResult().accountId()).isEqualTo("111933544165xxxx");
        assertThat(result.accessPointResult().networkOrigin()).isEqualTo("vpc");
        assertThat(result.accessPointResult().accessPointArn()).isEqualTo("arn:acs:oss:cn-hangzhou:111933544165xxxx:accesspoint/ap-01");
        assertThat(result.accessPointResult().creationDate()).isEqualTo("1626769503");
        assertThat(result.accessPointResult().alias()).isEqualTo("ap-01-ossalias");
        assertThat(result.accessPointResult().status()).isEqualTo("enable");
        assertThat(result.accessPointResult().vpcConfiguration().vpcId()).isEqualTo("vpc-t4nlw426y44rd3iq4xxxx");
        assertThat(result.accessPointResult().endpoints().publicEndpoint()).isEqualTo("ap-01.oss-cn-hangzhou.oss-accesspoint.aliyuncs.com");
        assertThat(result.accessPointResult().endpoints().internalEndpoint()).isEqualTo("ap-01.oss-cn-hangzhou-internal.oss-accesspoint.aliyuncs.com");
        assertThat(result.accessPointResult().publicAccessBlockConfiguration().blockPublicAccess()).isEqualTo(true);
    }
}