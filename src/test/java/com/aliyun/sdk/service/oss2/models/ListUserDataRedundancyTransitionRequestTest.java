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

public class ListUserDataRedundancyTransitionRequestTest {
    @Test
    public void testEmptyBuilder() {
        ListUserDataRedundancyTransitionRequest request = ListUserDataRedundancyTransitionRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        ListUserDataRedundancyTransitionRequest request = ListUserDataRedundancyTransitionRequest.newBuilder()
                .headers(headers)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .continuationToken("test-token")
                .maxKeys(100L)
                .build();

        assertThat(request.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(request.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param1", "value1"),
                new AbstractMap.SimpleEntry<>("param2", "value2"),
                new AbstractMap.SimpleEntry<>("continuation-token", "test-token"),
                new AbstractMap.SimpleEntry<>("max-keys", "100")
        );
        
        assertThat(request.continuationToken()).isEqualTo("test-token");
        assertThat(request.maxKeys()).isEqualTo(100L);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        ListUserDataRedundancyTransitionRequest original = ListUserDataRedundancyTransitionRequest.newBuilder()
                .headers(headers)
                .parameter("param3", "value3")
                .parameter("param4", "value4")
                .continuationToken("original-token")
                .maxKeys(50L)
                .build();

        ListUserDataRedundancyTransitionRequest copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("param3", "value3"),
                new AbstractMap.SimpleEntry<>("param4", "value4"),
                new AbstractMap.SimpleEntry<>("continuation-token", "original-token"),
                new AbstractMap.SimpleEntry<>("max-keys", "50")
        );
        
        assertThat(copy.continuationToken()).isEqualTo("original-token");
        assertThat(copy.maxKeys()).isEqualTo(50L);
    }

    @Test
    public void testHeaderProperties() {
        ListUserDataRedundancyTransitionRequest request = ListUserDataRedundancyTransitionRequest.newBuilder()
                .continuationToken("header-test-token")
                .maxKeys(200L)
                .build();
                
        assertThat(request.continuationToken()).isEqualTo("header-test-token");
        assertThat(request.maxKeys()).isEqualTo(200L);
    }

    @Test
    public void xmlBuilder() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ListUserDataRedundancyTransitionRequest request = ListUserDataRedundancyTransitionRequest.newBuilder()
                .continuationToken("xml-test-token")
                .maxKeys(300L)
                .build();

        OperationInput input = SerdeBucketRedundancyTransition.fromListUserDataRedundancyTransition(request);

        assertThat(input.parameters().get("redundancyTransition")).isEqualTo("");
        assertThat(input.headers().get("Content-Type")).isEqualTo("application/xml");
        assertThat(input.method()).isEqualTo("GET");
        assertThat(input.parameters().get("continuation-token")).isEqualTo("xml-test-token");
        assertThat(input.parameters().get("max-keys")).isEqualTo("300");
    }
}