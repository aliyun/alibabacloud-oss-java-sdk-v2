package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeAccessPoint;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateAccessPointResultTest {

    @Test
    public void testEmptyBuilder() {
        CreateAccessPointResult result = CreateAccessPointResult.newBuilder().build();
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

        CreateAccessPointResult result = CreateAccessPointResult.newBuilder()
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

        CreateAccessPointResult original = CreateAccessPointResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        CreateAccessPointResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("Date")).isEqualTo("Tue, 02 Jan 2024 12:00:00 GMT");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void xmlBuilder() {
        String xmlContent = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<CreateAccessPointResult>\n" +
                "<AccessPointArn>acs:oss:cn-hangzhou:128364106451xxxx:accesspoint/ap-01</AccessPointArn>\n" +
                "  <Alias>ap-01-45ee7945007a2f0bcb595f63e2215cxxxx-ossalias</Alias>\n" +
                "</CreateAccessPointResult>";
        
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

        CreateAccessPointResult result = SerdeAccessPoint.toCreateAccessPoint(output);

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("Date")).isEqualTo("Mon, 01 Jan 2024 12:00:00 GMT");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
        assertThat(result.accessPointResult().accessPointArn()).isEqualTo("acs:oss:cn-hangzhou:128364106451xxxx:accesspoint/ap-01");
        assertThat(result.accessPointResult().alias()).isEqualTo("ap-01-45ee7945007a2f0bcb595f63e2215cxxxx-ossalias");
    }
}