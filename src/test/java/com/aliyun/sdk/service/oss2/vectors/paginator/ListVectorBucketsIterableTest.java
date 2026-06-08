package com.aliyun.sdk.service.oss2.vectors.paginator;

import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.models.BucketsSummary;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorBucketsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorBucketsResult;
import com.aliyun.sdk.service.oss2.vectors.models.VectorBucketSummary;
import com.aliyun.sdk.service.oss2.vectors.models.internal.ListVectorBucketsResultJson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ListVectorBucketsIterableTest {

    @Test
    public void testIteratePages() {
        StubVectorsClient stub = new StubVectorsClient();

        BucketsSummary page1 = BucketsSummary.newBuilder()
                .isTruncated(true)
                .nextMarker("bucket-3")
                .buckets(Arrays.asList(
                        VectorBucketSummary.newBuilder().name("bucket-1").region("cn-hangzhou").build(),
                        VectorBucketSummary.newBuilder().name("bucket-2").region("cn-hangzhou").build()
                ))
                .build();

        BucketsSummary page2 = BucketsSummary.newBuilder()
                .isTruncated(false)
                .buckets(Arrays.asList(
                        VectorBucketSummary.newBuilder().name("bucket-3").region("cn-hangzhou").build()
                ))
                .build();

        stub.bucketPages.add(buildBucketsResult(page1));
        stub.bucketPages.add(buildBucketsResult(page2));

        ListVectorBucketsRequest request = ListVectorBucketsRequest.newBuilder()
                .prefix("bucket-")
                .build();

        ListVectorBucketsIterable iterable = new ListVectorBucketsIterable(stub, request);

        List<String> names = new ArrayList<>();
        for (ListVectorBucketsResult page : iterable) {
            for (VectorBucketSummary bucket : page.buckets()) {
                names.add(bucket.name());
            }
        }

        assertThat(names).containsExactly("bucket-1", "bucket-2", "bucket-3");
        assertThat(stub.requests).hasSize(2);
        // The first request should not have marker; the second should carry the next-marker.
        assertThat(stub.requests.get(0).marker()).isNull();
        assertThat(stub.requests.get(1).marker()).isEqualTo("bucket-3");
    }

    @Test
    public void testApplyLimit() {
        StubVectorsClient stub = new StubVectorsClient();
        stub.bucketPages.add(buildBucketsResult(
                BucketsSummary.newBuilder().isTruncated(false).build()
        ));

        ListVectorBucketsRequest request = ListVectorBucketsRequest.newBuilder().build();
        PaginatorOptions options = PaginatorOptions.newBuilder().limit(10L).build();
        ListVectorBucketsIterable iterable = new ListVectorBucketsIterable(stub, request, options);

        Iterator<ListVectorBucketsResult> it = iterable.iterator();
        assertThat(it.hasNext()).isTrue();
        it.next();
        assertThat(it.hasNext()).isFalse();

        assertThat(stub.requests).hasSize(1);
        assertThat(stub.requests.get(0).maxKeys()).isEqualTo(10L);

        // Calling next() after exhaustion should throw.
        assertThatThrownBy(it::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testStreamSupport() {
        StubVectorsClient stub = new StubVectorsClient();
        stub.bucketPages.add(buildBucketsResult(
                BucketsSummary.newBuilder()
                        .isTruncated(false)
                        .buckets(Arrays.asList(
                                VectorBucketSummary.newBuilder().name("b-1").build(),
                                VectorBucketSummary.newBuilder().name("b-2").build()
                        ))
                        .build()
        ));

        ListVectorBucketsRequest request = ListVectorBucketsRequest.newBuilder().build();
        ListVectorBucketsIterable iterable = new ListVectorBucketsIterable(stub, request);

        long count = iterable.stream().mapToLong(p -> p.buckets().size()).sum();
        assertThat(count).isEqualTo(2);
    }

    private static ListVectorBucketsResult buildBucketsResult(BucketsSummary summary) {
        ListVectorBucketsResultJson body = new ListVectorBucketsResultJson();
        body.bucketsSummary = summary;
        return ListVectorBucketsResult.newBuilder()
                .statusCode(200)
                .status("OK")
                .innerBody(body)
                .build();
    }

    /**
     * Minimal stub that records each request and returns predefined pages.
     */
    private static class StubVectorsClient implements OSSVectorsClient {
        final List<ListVectorBucketsResult> bucketPages = new ArrayList<>();
        final List<ListVectorBucketsRequest> requests = new ArrayList<>();
        private int idx = 0;

        @Override
        public ListVectorBucketsResult listVectorBuckets(ListVectorBucketsRequest request, OperationOptions options) {
            requests.add(request);
            return bucketPages.get(idx++);
        }

        @Override
        public void close() {
        }
    }
}
