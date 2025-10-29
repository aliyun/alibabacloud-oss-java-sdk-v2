package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketRedundancyTransition;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBucketDataRedundancyTransitionRequestTest {
    @Test
    public void testEmptyBuilder() {
        GetBucketDataRedundancyTransitionRequest request = GetBucketDataRedundancyTransitionRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.redundancyTransitionTaskid()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        GetBucketDataRedundancyTransitionRequest request = GetBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket("examplebucket")
                .redundancyTransitionTaskid("909c6c818dd041d1a44e0fdc66aa****")
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.redundancyTransitionTaskid()).isEqualTo("909c6c818dd041d1a44e0fdc66aa****");

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

        GetBucketDataRedundancyTransitionRequest original = GetBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket("testbucket")
                .redundancyTransitionTaskid("909c6c818dd041d1a44e0fdc66aa****")
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .build();

        GetBucketDataRedundancyTransitionRequest copy = original.toBuilder().build();

        assertThat(copy.bucket()).isEqualTo("testbucket");
        assertThat(copy.redundancyTransitionTaskid()).isEqualTo("909c6c818dd041d1a44e0fdc66aa****");

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4")
        );
    }

    @Test
    public void testHeaderProperties() {
        GetBucketDataRedundancyTransitionRequest request = GetBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket("anotherbucket")
                .redundancyTransitionTaskid("909c6c818dd041d1a44e0fdc66aa****")
                .build();

        assertThat(request.bucket()).isEqualTo("anotherbucket");
        assertThat(request.redundancyTransitionTaskid()).isEqualTo("909c6c818dd041d1a44e0fdc66aa****");
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        GetBucketDataRedundancyTransitionRequest request = GetBucketDataRedundancyTransitionRequest.newBuilder()
                .bucket("examplebucket")
                .redundancyTransitionTaskid("909c6c818dd041d1a44e0fdc66aa****")
                .build();

        OperationInput input = SerdeBucketRedundancyTransition.fromGetBucketDataRedundancyTransition(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("redundancyTransition")).isEqualTo("");
        assertThat(input.parameters().get("x-oss-redundancy-transition-taskid")).isEqualTo("909c6c818dd041d1a44e0fdc66aa****");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("GET");
    }
}