package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketAccessmonitor;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketAccessMonitorRequestTest {
    @Test
    public void testEmptyBuilder() {
        PutBucketAccessMonitorRequest request = PutBucketAccessMonitorRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.accessMonitorConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        AccessMonitorConfiguration accessMonitorConfiguration = AccessMonitorConfiguration.newBuilder()
                .status("Enabled")
                .allowCopy(true)
                .build();

        PutBucketAccessMonitorRequest request = PutBucketAccessMonitorRequest.newBuilder()
                .bucket("examplebucket")
                .accessMonitorConfiguration(accessMonitorConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.accessMonitorConfiguration()).isEqualTo(accessMonitorConfiguration);
        assertThat(request.accessMonitorConfiguration().allowCopy()).isEqualTo(true);

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testFullBuilderWithEnum() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        AccessMonitorConfiguration accessMonitorConfiguration = AccessMonitorConfiguration.newBuilder()
                .status(AccessMonitorStatusType.ENABLED)
                .allowCopy(true)
                .build();

        PutBucketAccessMonitorRequest request = PutBucketAccessMonitorRequest.newBuilder()
                .bucket("examplebucket")
                .accessMonitorConfiguration(accessMonitorConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.accessMonitorConfiguration()).isEqualTo(accessMonitorConfiguration);
        assertThat(request.accessMonitorConfiguration().allowCopy()).isEqualTo(true);
        assertThat(request.accessMonitorConfiguration().status()).isEqualTo("Enabled");

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        AccessMonitorConfiguration accessMonitorConfiguration = AccessMonitorConfiguration.newBuilder()
                .status("Disabled")
                .allowCopy(false)
                .build();

        PutBucketAccessMonitorRequest original = PutBucketAccessMonitorRequest.newBuilder()
                .bucket("testbucket")
                .accessMonitorConfiguration(accessMonitorConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutBucketAccessMonitorRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.accessMonitorConfiguration()).isEqualTo(accessMonitorConfiguration);
        assertThat(copy.accessMonitorConfiguration().allowCopy()).isEqualTo(false);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testToBuilderPreserveStateWithEnum() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        AccessMonitorConfiguration accessMonitorConfiguration = AccessMonitorConfiguration.newBuilder()
                .status(AccessMonitorStatusType.DISABLED)
                .allowCopy(false)
                .build();

        PutBucketAccessMonitorRequest original = PutBucketAccessMonitorRequest.newBuilder()
                .bucket("testbucket")
                .accessMonitorConfiguration(accessMonitorConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutBucketAccessMonitorRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.accessMonitorConfiguration()).isEqualTo(accessMonitorConfiguration);
        assertThat(copy.accessMonitorConfiguration().allowCopy()).isEqualTo(false);
        assertThat(copy.accessMonitorConfiguration().status()).isEqualTo("Disabled");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        AccessMonitorConfiguration accessMonitorConfiguration = AccessMonitorConfiguration.newBuilder()
                .status("Enabled")
                .allowCopy(true)
                .build();

        PutBucketAccessMonitorRequest request = PutBucketAccessMonitorRequest.newBuilder()
                .bucket("anotherbucket")
                .accessMonitorConfiguration(accessMonitorConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.accessMonitorConfiguration()).isEqualTo(accessMonitorConfiguration);
        assertThat(request.accessMonitorConfiguration().allowCopy()).isEqualTo(true);
    }

    @Test
    public void testHeaderPropertiesWithEnum() {
        AccessMonitorConfiguration accessMonitorConfiguration = AccessMonitorConfiguration.newBuilder()
                .status(AccessMonitorStatusType.ENABLED)
                .allowCopy(true)
                .build();

        PutBucketAccessMonitorRequest request = PutBucketAccessMonitorRequest.newBuilder()
                .bucket("anotherbucket")
                .accessMonitorConfiguration(accessMonitorConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.accessMonitorConfiguration()).isEqualTo(accessMonitorConfiguration);
        assertThat(request.accessMonitorConfiguration().allowCopy()).isEqualTo(true);
        assertThat(request.accessMonitorConfiguration().status()).isEqualTo("Enabled");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        AccessMonitorConfiguration accessMonitorConfiguration = AccessMonitorConfiguration.newBuilder()
                .status("Enabled")
                .allowCopy(true)
                .build();

        PutBucketAccessMonitorRequest request = PutBucketAccessMonitorRequest.newBuilder()
                .bucket("examplebucket")
                .accessMonitorConfiguration(accessMonitorConfiguration)
                .build();

        OperationInput input = SerdeBucketAccessmonitor.fromPutBucketAccessMonitor(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("accessmonitor")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<AccessMonitorConfiguration>");
        assertThat(xmlContent).contains("<Status>Enabled</Status>");
        assertThat(xmlContent).contains("<AllowCopy>true</AllowCopy>");
        assertThat(xmlContent).contains("</AccessMonitorConfiguration>");
    }

    @Test
    public void xmlBuilderWithEnum() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        AccessMonitorConfiguration accessMonitorConfiguration = AccessMonitorConfiguration.newBuilder()
                .status(AccessMonitorStatusType.ENABLED)
                .allowCopy(true)
                .build();

        PutBucketAccessMonitorRequest request = PutBucketAccessMonitorRequest.newBuilder()
                .bucket("examplebucket")
                .accessMonitorConfiguration(accessMonitorConfiguration)
                .build();

        OperationInput input = SerdeBucketAccessmonitor.fromPutBucketAccessMonitor(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("accessmonitor")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();
        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<AccessMonitorConfiguration>");
        assertThat(xmlContent).contains("<Status>Enabled</Status>");
        assertThat(xmlContent).contains("<AllowCopy>true</AllowCopy>");
        assertThat(xmlContent).contains("</AccessMonitorConfiguration>");
    }
}