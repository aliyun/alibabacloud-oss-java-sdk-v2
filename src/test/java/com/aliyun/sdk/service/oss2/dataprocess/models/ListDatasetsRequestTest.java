package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ListDatasetsRequestTest {

    @Test
    public void testEmptyBuilder() {
        ListDatasetsRequest request = ListDatasetsRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.prefix()).isNull();
        assertThat(request.maxResults()).isNull();
        assertThat(request.nextToken()).isNull();
    }

    @Test
    public void testFullBuilder() {
        ListDatasetsRequest request = ListDatasetsRequest.newBuilder()
                .bucket("examplebucket")
                .prefix("test-prefix-")
                .maxResults(10L)
                .nextToken("next-token-abc")
                .header("x-custom-header", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.prefix()).isEqualTo("test-prefix-");
        assertThat(request.maxResults()).isEqualTo("10");
        assertThat(request.nextToken()).isEqualTo("next-token-abc");
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));

        ListDatasetsRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.prefix()).isEqualTo("test-prefix-");
        assertThat(copy.maxResults()).isEqualTo("10");
        assertThat(copy.nextToken()).isEqualTo("next-token-abc");
    }

    @Test
    public void testToBuilderPreserveState() {
        ListDatasetsRequest original = ListDatasetsRequest.newBuilder()
                .bucket("test-bucket")
                .prefix("pfx-")
                .build();

        ListDatasetsRequest copy = original.toBuilder()
                .maxResults(5L)
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.prefix()).isEqualTo("pfx-");
        assertThat(copy.maxResults()).isEqualTo("5");
    }

    @Test
    public void testNullSafeParameters() {
        ListDatasetsRequest request = ListDatasetsRequest.newBuilder()
                .bucket("test-bucket")
                .maxResults(null)
                .nextToken(null)
                .prefix(null)
                .build();

        assertThat(request.bucket()).isEqualTo("test-bucket");
        assertThat(request.maxResults()).isNull();
        assertThat(request.nextToken()).isNull();
        assertThat(request.prefix()).isNull();
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §9 ListDatasets URL params
        ListDatasetsRequest request = ListDatasetsRequest.newBuilder()
                .bucket("examplebucket")
                .prefix("oss_1023210024677934_")
                .maxResults(50L)
                .nextToken("token-abc123")
                .build();

        OperationInput input = SerdeDatasetBasic.fromListDatasets(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("listDatasets");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("prefix")).isEqualTo("oss_1023210024677934_");
        assertThat(input.parameters().get("maxResults")).isEqualTo("50");
        assertThat(input.parameters().get("nextToken")).isEqualTo("token-abc123");
        assertThat(input.method()).isEqualTo("POST");
        assertThat(input.body().isPresent()).isFalse();
    }
}
