package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketObjectFcAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAccessPointForObjectProcessResultTest {

    @Test
    public void testEmptyBuilder() {
        GetAccessPointForObjectProcessResult result = GetAccessPointForObjectProcessResult.newBuilder().build();
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

        GetAccessPointForObjectProcessResult result = GetAccessPointForObjectProcessResult.newBuilder()
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

        GetAccessPointForObjectProcessResult original = GetAccessPointForObjectProcessResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        GetAccessPointForObjectProcessResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<GetAccessPointForObjectProcessResult>\n" +
                "  <AccessPointNameForObjectProcess>fc-ap-01</AccessPointNameForObjectProcess>\n" +
                "  <AccessPointForObjectProcessAlias>fc-ap-01-3b00521f653d2b3223680ec39dbbe2****-opapalias</AccessPointForObjectProcessAlias>\n" +
                "  <AccessPointName>ap-01</AccessPointName>\n" +
                "  <AccountId>111933544165****</AccountId>\n" +
                "  <AccessPointForObjectProcessArn>acs:oss:cn-qingdao:11933544165****:accesspointforobjectprocess/fc-ap-01</AccessPointForObjectProcessArn>\n" +
                "  <CreationDate>1626769503</CreationDate>\n" +
                "  <Status>enable</Status>\n" +
                "  <Endpoints>\n" +
                "    <PublicEndpoint>fc-ap-01-111933544165****.oss-cn-qingdao.oss-object-process.aliyuncs.com</PublicEndpoint>\n" +
                "    <InternalEndpoint>fc-ap-01-111933544165****.oss-cn-qingdao-internal.oss-object-process.aliyuncs.com</InternalEndpoint>\n" +
                "  </Endpoints>\n" +
                "  <PublicAccessBlockConfiguration>\n" +
                "    <BlockPublicAccess>true</BlockPublicAccess>\n" +
                "  </PublicAccessBlockConfiguration>\n" +
                "</GetAccessPointForObjectProcessResult>";
        
        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetAccessPointForObjectProcessResult getResult = SerdeBucketObjectFcAccessPoint.toGetAccessPointForObjectProcess(output);
        assertThat(getResult).isNotNull();

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

        GetAccessPointForObjectProcessResult result = SerdeBucketObjectFcAccessPoint.toGetAccessPointForObjectProcess(fullOutput);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        
        GetAccessPointForObjectProcessResultXml accessPointResult = result.accessPointForObjectProcessResult();
        assertThat(accessPointResult).isNotNull();
        assertThat(accessPointResult.accessPointNameForObjectProcess()).isEqualTo("fc-ap-01");
        assertThat(accessPointResult.accessPointForObjectProcessAlias()).isEqualTo("fc-ap-01-3b00521f653d2b3223680ec39dbbe2****-opapalias");
        assertThat(accessPointResult.accessPointName()).isEqualTo("ap-01");
        assertThat(accessPointResult.accountId()).isEqualTo("111933544165****");
        assertThat(accessPointResult.accessPointForObjectProcessArn()).isEqualTo("acs:oss:cn-qingdao:11933544165****:accesspointforobjectprocess/fc-ap-01");
        assertThat(accessPointResult.creationDate()).isEqualTo("1626769503");
        assertThat(accessPointResult.status()).isEqualTo("enable");
        
        AccessPointEndpoints endpoints = accessPointResult.endpoints();
        assertThat(endpoints).isNotNull();
        assertThat(endpoints.publicEndpoint()).isEqualTo("fc-ap-01-111933544165****.oss-cn-qingdao.oss-object-process.aliyuncs.com");
        assertThat(endpoints.internalEndpoint()).isEqualTo("fc-ap-01-111933544165****.oss-cn-qingdao-internal.oss-object-process.aliyuncs.com");
        
        PublicAccessBlockConfiguration publicAccessBlock = accessPointResult.publicAccessBlockConfiguration();
        assertThat(publicAccessBlock).isNotNull();
        assertThat(publicAccessBlock.blockPublicAccess()).isEqualTo(true);
    }
}