package com.aliyun.sdk.service.oss2.vectors.paginator;

import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorsResult;
import com.aliyun.sdk.service.oss2.vectors.models.VectorsSummary;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorsResultJson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ListVectorsIterableTest {

    @Test
    public void testIteratePages() {
        StubVectorsClient stub = new StubVectorsClient();

        stub.pages.add(buildVectorsResult(
                Arrays.asList(
                        VectorsSummary.newBuilder().key("vec-1").build(),
                        VectorsSummary.newBuilder().key("vec-2").build()
                ),
                "next-token-1"));

        stub.pages.add(buildVectorsResult(
                Arrays.asList(VectorsSummary.newBuilder().key("vec-3").build()),
                null));

        ListVectorsRequest request = ListVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .build();

        ListVectorsIterable iterable = new ListVectorsIterable(stub, request);

        List<String> keys = new ArrayList<>();
        for (ListVectorsResult page : iterable) {
            for (VectorsSummary v : page.vectors()) {
                keys.add(v.key());
            }
        }

        assertThat(keys).containsExactly("vec-1", "vec-2", "vec-3");
        assertThat(stub.requests).hasSize(2);
        assertThat(stub.requests.get(0).bucket()).isEqualTo("test-bucket");
        assertThat(stub.requests.get(0).indexName()).isEqualTo("test-index");
        assertThat(stub.requests.get(0).nextToken()).isNull();
        assertThat(stub.requests.get(1).nextToken()).isEqualTo("next-token-1");
    }

    @Test
    public void testApplyLimit() {
        StubVectorsClient stub = new StubVectorsClient();
        stub.pages.add(buildVectorsResult(new ArrayList<>(), null));

        ListVectorsRequest request = ListVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .build();
        PaginatorOptions options = PaginatorOptions.newBuilder().limit(50L).build();
        ListVectorsIterable iterable = new ListVectorsIterable(stub, request, options);

        Iterator<ListVectorsResult> it = iterable.iterator();
        assertThat(it.hasNext()).isTrue();
        it.next();
        assertThat(it.hasNext()).isFalse();

        assertThat(stub.requests).hasSize(1);
        // ListVectorsRequest.maxResults() is Integer.
        assertThat(stub.requests.get(0).maxResults()).isEqualTo(50);

        assertThatThrownBy(it::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testStopWhenNextTokenEmpty() {
        StubVectorsClient stub = new StubVectorsClient();
        stub.pages.add(buildVectorsResult(
                Arrays.asList(VectorsSummary.newBuilder().key("only-1").build()),
                ""));

        ListVectorsRequest request = ListVectorsRequest.newBuilder()
                .bucket("test-bucket")
                .indexName("test-index")
                .build();
        ListVectorsIterable iterable = new ListVectorsIterable(stub, request);

        long pageCount = iterable.stream().count();
        assertThat(pageCount).isEqualTo(1);
        assertThat(stub.requests).hasSize(1);
    }

    private static ListVectorsResult buildVectorsResult(List<VectorsSummary> vectors, String nextToken) {
        ListVectorsResultJson body = new ListVectorsResultJson();
        body.vectors = vectors;
        body.nextToken = nextToken;
        return ListVectorsResult.newBuilder()
                .statusCode(200)
                .status("OK")
                .innerBody(body)
                .build();
    }

    private static class StubVectorsClient implements OSSVectorsClient {
        final List<ListVectorsResult> pages = new ArrayList<>();
        final List<ListVectorsRequest> requests = new ArrayList<>();
        private int idx = 0;

        @Override
        public ListVectorsResult listVectors(ListVectorsRequest request, OperationOptions options) {
            requests.add(request);
            return pages.get(idx++);
        }

        @Override
        public void close() {
        }
    }
}
