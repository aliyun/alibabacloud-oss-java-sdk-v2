package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDataPipelineBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ListDataPipelineConfigurationsRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListDataPipelineConfigurationsRequest request = ListDataPipelineConfigurationsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.maxResults()).isNull();
        assertThat(request.prefix()).isNull();
        assertThat(request.nextToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ListDataPipelineConfigurationsRequest request = ListDataPipelineConfigurationsRequest.newBuilder()
                .maxResults(50)
                .prefix("test-prefix")
                .nextToken("test-token")
                .header("x-header-value", "value1")
                .header("x-header-value", "value2")
                .parameter("empty-param", "")
                .parameter("null-param", null)
                .parameter("str-param", "value")
                .build();

        assertThat(request.maxResults()).isEqualTo("50");
        assertThat(request.prefix()).isEqualTo("test-prefix");
        assertThat(request.nextToken()).isEqualTo("test-token");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(request.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
        assertThat(request.parameters().get("null-param")).isNull();

        ListDataPipelineConfigurationsRequest copy = request.toBuilder().build();
        assertThat(copy.maxResults()).isEqualTo("50");
        assertThat(copy.prefix()).isEqualTo("test-prefix");
        assertThat(copy.nextToken()).isEqualTo("test-token");
        assertThat(copy.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-header-value", "value2"));
        assertThat(copy.parameters()).contains(
                new AbstractMap.SimpleEntry<>("empty-param", ""),
                new AbstractMap.SimpleEntry<>("str-param", "value")
        );
    }

    @Test
    public void testToBuilderPreserveState() {
        ListDataPipelineConfigurationsRequest original = ListDataPipelineConfigurationsRequest.newBuilder()
                .maxResults(100)
                .prefix("original-prefix")
                .nextToken("original-token")
                .build();

        ListDataPipelineConfigurationsRequest copy = original.toBuilder().build();

        assertThat(copy.maxResults()).isEqualTo("100");
        assertThat(copy.prefix()).isEqualTo("original-prefix");
        assertThat(copy.nextToken()).isEqualTo("original-token");
    }

    @Test
    public void testHeaderProperties() {
        ListDataPipelineConfigurationsRequest request = ListDataPipelineConfigurationsRequest.newBuilder()
                .maxResults(25)
                .prefix("header-prefix")
                .nextToken("header-token")
                .build();

        assertThat(request.maxResults()).isEqualTo("25");
        assertThat(request.prefix()).isEqualTo("header-prefix");
        assertThat(request.nextToken()).isEqualTo("header-token");
    }

    @Test
    public void xmlBuilder() {
        ListDataPipelineConfigurationsRequest request = ListDataPipelineConfigurationsRequest.newBuilder()
                .maxResults(50)
                .prefix("xml-prefix")
                .nextToken("xml-token")
                .build();

        OperationInput input = SerdeDataPipelineBasic.fromListDataPipelineConfigurations(request);

        assertThat(input.parameters().get("maxResults")).isEqualTo("50");
        assertThat(input.parameters().get("prefix")).isEqualTo("xml-prefix");
        assertThat(input.parameters().get("nextToken")).isEqualTo("xml-token");
        assertThat(input.parameters().get("action")).isEqualTo("listDataPipelineConfigurations");
        assertThat(input.body().isPresent()).isFalse();
    }
}
