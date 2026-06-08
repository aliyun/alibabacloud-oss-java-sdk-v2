package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketReplication;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class PutBucketRtcRequestTest {

    @Test
    public void testEmptyBuilder() {
        PutBucketRtcRequest request = PutBucketRtcRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.rtcConfiguration()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        RTC rtc = RTC.newBuilder()
                .status("enabled")
                .build();

        RtcConfiguration rtcConfiguration = RtcConfiguration.newBuilder()
                .rtc(rtc)
                .id("rule-id")
                .build();

        PutBucketRtcRequest request = PutBucketRtcRequest.newBuilder()
                .bucket("examplebucket")
                .rtcConfiguration(rtcConfiguration)
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.rtcConfiguration()).isEqualTo(rtcConfiguration);

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

        RTC rtc = RTC.newBuilder()
                .status("enabled")
                .build();

        RtcConfiguration rtcConfiguration = RtcConfiguration.newBuilder()
                .rtc(rtc)
                .id("rule-id")
                .build();

        PutBucketRtcRequest original = PutBucketRtcRequest.newBuilder()
                .bucket("testbucket")
                .rtcConfiguration(rtcConfiguration)
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        PutBucketRtcRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.rtcConfiguration()).isEqualTo(rtcConfiguration);

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        RTC rtc = RTC.newBuilder()
                .status("enabled")
                .build();

        RtcConfiguration rtcConfiguration = RtcConfiguration.newBuilder()
                .rtc(rtc)
                .id("rule-id")
                .build();

        PutBucketRtcRequest request = PutBucketRtcRequest.newBuilder()
                .bucket("anotherbucket")
                .rtcConfiguration(rtcConfiguration)
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.rtcConfiguration()).isEqualTo(rtcConfiguration);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        String xml = "<ReplicationRule><RTC><Status>enabled</Status></RTC><ID>test_replication_rule_1</ID></ReplicationRule>";

        RTC rtc = RTC.newBuilder()
                .status("enabled")
                .build();

        RtcConfiguration rtcConfiguration = RtcConfiguration.newBuilder()
                .rtc(rtc)
                .id("test_replication_rule_1")
                .build();

        PutBucketRtcRequest request = PutBucketRtcRequest.newBuilder()
                .bucket("oss-example")
                .rtcConfiguration(rtcConfiguration)
                .build();

        OperationInput input = SerdeBucketReplication.fromPutBucketRtc(request);

        assertThat(input.bucket().get()).isEqualTo("oss-example");
        assertThat(input.parameters().get("rtc")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");

        // Verify the XML body content
        BinaryData body = input.body().get();

        assertThat(xml).isEqualTo(body.toString());

        String xmlContent = new String(body.toBytes(), StandardCharsets.UTF_8);
        assertThat(xmlContent).contains("<ReplicationRule>");
        assertThat(xmlContent).contains("<RTC>");
        assertThat(xmlContent).contains("<Status>enabled</Status>");
        assertThat(xmlContent).contains("<ID>test_replication_rule_1</ID>");
        assertThat(xmlContent).contains("</RTC>");
        assertThat(xmlContent).contains("</ReplicationRule>");
    }
}