package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleQueryRequestTest {

    @Test
    public void testEmptyBuilder() {
        SimpleQueryRequest request = SimpleQueryRequest.newBuilder().build();
        assertThat(request).isNotNull();
        assertThat(request.headers()).isNotNull();
        assertThat(request.headers().isEmpty()).isTrue();
        assertThat(request.parameters()).isNotNull();
        assertThat(request.parameters().isEmpty()).isTrue();
        assertThat(request.bucket()).isNull();
        assertThat(request.datasetName()).isNull();
        assertThat(request.nextToken()).isNull();
        assertThat(request.maxResults()).isNull();
        assertThat(request.query()).isNull();
        assertThat(request.sort()).isNull();
        assertThat(request.order()).isNull();
        assertThat(request.aggregations()).isNull();
        assertThat(request.withFields()).isNull();
        assertThat(request.withoutTotalHits()).isNull();
    }

    @Test
    public void testFullBuilder() {
        SimpleQuery sq = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test")
                .operation("prefix")
                .build();

        Aggregation agg = Aggregation.newBuilder()
                .field("Size")
                .operation("sum")
                .build();

        SimpleQueryRequest request = SimpleQueryRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("my-dataset")
                .query(sq)
                .nextToken("token-123")
                .maxResults(10)
                .sort("Filename")
                .order("asc")
                .aggregations(Arrays.asList(agg))
                .withFields(Arrays.asList("Filename", "Size"))
                .withoutTotalHits(false)
                .header("x-custom-header", "value1")
                .build();

        assertThat(request.bucket()).isEqualTo("examplebucket");
        assertThat(request.datasetName()).isEqualTo("my-dataset");
        assertThat(request.query()).isNotNull();
        assertThat(request.nextToken()).isEqualTo("token-123");
        assertThat(request.maxResults()).isEqualTo(10);
        assertThat(request.sort()).isEqualTo("Filename");
        assertThat(request.order()).isEqualTo("asc");
        assertThat(request.aggregations()).isNotNull();
        assertThat(request.withFields()).isNotNull();
        assertThat(request.withoutTotalHits()).isFalse();
        assertThat(request.headers()).contains(
                new AbstractMap.SimpleEntry<>("x-custom-header", "value1"));

        SimpleQueryRequest copy = request.toBuilder().build();
        assertThat(copy.bucket()).isEqualTo("examplebucket");
        assertThat(copy.datasetName()).isEqualTo("my-dataset");
        assertThat(copy.maxResults()).isEqualTo(10);
    }

    @Test
    public void testToBuilderPreserveState() {
        SimpleQuery sq = SimpleQuery.newBuilder()
                .field("Filename")
                .value("test")
                .operation("eq")
                .build();

        SimpleQueryRequest original = SimpleQueryRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .query(sq)
                .build();

        SimpleQueryRequest copy = original.toBuilder()
                .maxResults(20)
                .build();

        assertThat(copy.bucket()).isEqualTo("test-bucket");
        assertThat(copy.datasetName()).isEqualTo("test-dataset");
        assertThat(copy.maxResults()).isEqualTo(20);
    }

    @Test
    public void testWithStringParameters() {
        SimpleQueryRequest request = SimpleQueryRequest.newBuilder()
                .bucket("test-bucket")
                .datasetName("test-dataset")
                .aggregations("[{\"Field\":\"Size\",\"Operation\":\"sum\"}]")
                .withFields("[\"Filename\",\"Size\"]")
                .build();

        assertThat(request.aggregations()).isEqualTo("[{\"Field\":\"Size\",\"Operation\":\"sum\"}]");
        assertThat(request.withFields()).isEqualTo("[\"Filename\",\"Size\"]");
    }

    @Test
    public void xmlBuilder() {
        // Reference: sdk_internal_reference(3).md §10 SimpleQuery URL params
        SimpleQuery sq = SimpleQuery.newBuilder()
                .field("Size")
                .value("1048576")
                .operation("gt")
                .build();

        Aggregation agg = Aggregation.newBuilder()
                .field("Size")
                .operation("sum")
                .build();

        SimpleQueryRequest request = SimpleQueryRequest.newBuilder()
                .bucket("examplebucket")
                .datasetName("photos-2026")
                .query(sq)
                .maxResults(100)
                .sort("Size")
                .order("desc")
                .aggregations(Arrays.asList(agg))
                .withFields(Arrays.asList("OSSURI", "Size", "FileHash"))
                .withoutTotalHits(false)
                .build();

        OperationInput input = SerdeDatasetBasic.fromSimpleQuery(request);

        assertThat(input.bucket().get()).isEqualTo("examplebucket");
        assertThat(input.parameters().get("action")).isEqualTo("simpleQuery");
        assertThat(input.parameters().get("metaQuery")).isEqualTo("");
        assertThat(input.parameters().get("datasetName")).isEqualTo("photos-2026");
        assertThat(input.parameters().get("maxResults")).isEqualTo("100");
        assertThat(input.parameters().get("sort")).isEqualTo("Size");
        assertThat(input.parameters().get("order")).isEqualTo("desc");
        assertThat(input.parameters().get("withoutTotalHits")).isEqualTo("false");
        assertThat(input.parameters().get("query")).isNotNull();
        assertThat(input.method()).isEqualTo("POST");
    }
}
