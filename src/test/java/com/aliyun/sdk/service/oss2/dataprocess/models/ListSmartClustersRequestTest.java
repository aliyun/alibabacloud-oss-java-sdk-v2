package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;

public class ListSmartClustersRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListSmartClustersRequest request = ListSmartClustersRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.datasetName()).isNull();
        assertThat(request.nextToken()).isNull();
        assertThat(request.maxResults()).isNull();
        assertThat(request.clusterType()).isNull();
        assertThat(request.ruleTypes()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ListSmartClustersRequest request = ListSmartClustersRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("my-dataset")
                .nextToken("token-123")
                .maxResults(10)
                .clusterType("keyword")
                .ruleTypes(Arrays.asList("Keyword", "Semantic"))
                .header("x-custom-header", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("my-dataset");
        assertThat(request.nextToken()).isEqualTo("token-123");
        assertThat(request.maxResults()).isEqualTo(10);
        assertThat(request.clusterType()).isEqualTo("keyword");
        assertThat(request.ruleTypes()).containsExactly("Keyword", "Semantic");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));

        ListSmartClustersRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("my-dataset");
        assertThat(copy.nextToken()).isEqualTo("token-123");
        assertThat(copy.maxResults()).isEqualTo(10);
    }

    @Test
    public void testToBuilderPreserveState() {
        ListSmartClustersRequest original = ListSmartClustersRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .build();

        ListSmartClustersRequest copy = original.toBuilder()
                .maxResults(5)
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.maxResults()).isEqualTo(5);
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §17 ListSmartClusters URL params
        ListSmartClustersRequest request = ListSmartClustersRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .maxResults(50)
                .nextToken("token-abc123")
                .clusterType("figure")
                .ruleTypes(Collections.singletonList("face"))
                .build();

        OperationInput input = SerdeDatasetBasic.fromListSmartClusters(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("listSmartClusters");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("datasetName")).isEqualTo("photos-2026");
        assertThat(input.parameters().get("maxResults")).isEqualTo("50");
        assertThat(input.parameters().get("nextToken")).isEqualTo("token-abc123");
        assertThat(input.parameters().get("clusterType")).isEqualTo("figure");
        assertThat(input.parameters().get("ruleTypes")).isEqualTo("[\"face\"]");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().isPresent()).isFalse();
    }
}
