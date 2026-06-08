package com.aliyun.sdk.service.oss2.vectors.paginator;

import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.models.IndexSummary;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorIndexesRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorIndexesResult;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorIndexesResultJson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ListVectorIndexesIterableTest {

    @Test
    public void testIteratePages() {
        StubVectorsClient stub = new StubVectorsClient();

        stub.pages.add(buildIndexesResult(
                Arrays.asList(
                        IndexSummary.newBuilder().indexName("idx-1").build(),
                        IndexSummary.newBuilder().indexName("idx-2").build()
                ),
                "next-token-1"));

        stub.pages.add(buildIndexesResult(
                Arrays.asList(IndexSummary.newBuilder().indexName("idx-3").build()),
                null));

        ListVectorIndexesRequest request = ListVectorIndexesRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        ListVectorIndexesIterable iterable = new ListVectorIndexesIterable(stub, request);

        List<String> names = new ArrayList<>();
        for (ListVectorIndexesResult page : iterable) {
            for (IndexSummary index : page.indexes()) {
                names.add(index.indexName());
            }
        }

        assertThat(names).containsExactly("idx-1", "idx-2", "idx-3");
        assertThat(stub.requests).hasSize(2);
        assertThat(stub.requests.get(0).bucket()).isEqualTo("test-bucket");
        assertThat(stub.requests.get(0).nextToken()).isNull();
        assertThat(stub.requests.get(1).nextToken()).isEqualTo("next-token-1");
    }

    @Test
    public void testApplyLimit() {
        StubVectorsClient stub = new StubVectorsClient();
        stub.pages.add(buildIndexesResult(new ArrayList<>(), null));

        ListVectorIndexesRequest request = ListVectorIndexesRequest.newBuilder()
                .bucket("test-bucket")
                .build();
        PaginatorOptions options = PaginatorOptions.newBuilder().limit(20L).build();
        ListVectorIndexesIterable iterable = new ListVectorIndexesIterable(stub, request, options);

        Iterator<ListVectorIndexesResult> it = iterable.iterator();
        assertThat(it.hasNext()).isTrue();
        it.next();
        assertThat(it.hasNext()).isFalse();

        assertThat(stub.requests).hasSize(1);
        assertThat(stub.requests.get(0).maxResults()).isEqualTo(20L);

        assertThatThrownBy(it::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testEmptyResult() {
        StubVectorsClient stub = new StubVectorsClient();
        stub.pages.add(buildIndexesResult(new ArrayList<>(), null));

        ListVectorIndexesRequest request = ListVectorIndexesRequest.newBuilder()
                .bucket("test-bucket")
                .build();

        long pageCount = new ListVectorIndexesIterable(stub, request).stream().count();
        assertThat(pageCount).isEqualTo(1);
    }

    private static ListVectorIndexesResult buildIndexesResult(List<IndexSummary> indexes, String nextToken) {
        ListVectorIndexesResultJson body = new ListVectorIndexesResultJson();
        body.indexes = indexes;
        body.nextToken = nextToken;
        return ListVectorIndexesResult.newBuilder()
                .statusCode(200)
                .status("OK")
                .innerBody(body)
                .build();
    }

    private static class StubVectorsClient implements OSSVectorsClient {
        final List<ListVectorIndexesResult> pages = new ArrayList<>();
        final List<ListVectorIndexesRequest> requests = new ArrayList<>();
        private int idx = 0;

        @Override
        public ListVectorIndexesResult listVectorIndexes(ListVectorIndexesRequest request, OperationOptions options) {
            requests.add(request);
            return pages.get(idx++);
        }

        @Override
        public void close() {
        }
    }
}
