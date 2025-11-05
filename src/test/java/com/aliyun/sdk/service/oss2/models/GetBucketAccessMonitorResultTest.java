package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketAccessmonitor;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketAccessMonitorResultTest {

    @Test
    public void testEmptyBuilder() {
        GetBucketAccessMonitorResult result = GetBucketAccessMonitorResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        AccessMonitorConfiguration accessMonitorConfiguration = AccessMonitorConfiguration.newBuilder()
                .status("Enabled")
                .allowCopy(true)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketAccessMonitorResult result = GetBucketAccessMonitorResult.newBuilder()
                .headers(headers)
                .innerBody(accessMonitorConfiguration)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");

        assertThat(result.accessMonitorConfiguration()).isEqualTo(accessMonitorConfiguration);
        assertThat(result.accessMonitorConfiguration().status()).isEqualTo("Enabled");
        assertThat(result.accessMonitorConfiguration().allowCopy()).isEqualTo(true);
    }

    @Test
    public void testToBuilderPreserveState() {
        AccessMonitorConfiguration accessMonitorConfiguration = AccessMonitorConfiguration.newBuilder()
                .status("Disabled")
                .allowCopy(false)
                .build();

        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        GetBucketAccessMonitorResult original = GetBucketAccessMonitorResult.newBuilder()
                .headers(headers)
                .innerBody(accessMonitorConfiguration)
                .build();

        GetBucketAccessMonitorResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");

        assertThat(copy.accessMonitorConfiguration()).isEqualTo(accessMonitorConfiguration);
        assertThat(copy.accessMonitorConfiguration().status()).isEqualTo("Disabled");
        assertThat(copy.accessMonitorConfiguration().allowCopy()).isEqualTo(false);
    }

    @Test
    public void testXmlBuilder() {
        String xml =
                "<AccessMonitorConfiguration>\n" +
                "    <Status>Enabled</Status>\n" +
                "    <AllowCopy>true</AllowCopy>\n" +
                "</AccessMonitorConfiguration>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        GetBucketAccessMonitorResult result = SerdeBucketAccessmonitor.toGetBucketAccessMonitor(output);

        assertThat(result.accessMonitorConfiguration()).isNotNull();
        assertThat(result.accessMonitorConfiguration().status()).isEqualTo("Enabled");
        assertThat(result.accessMonitorConfiguration().allowCopy()).isEqualTo(true);
    }
}