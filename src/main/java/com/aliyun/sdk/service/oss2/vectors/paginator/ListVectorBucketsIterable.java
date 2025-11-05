package com.aliyun.sdk.service.oss2.vectors.paginator;

import com.aliyun.sdk.service.oss2.paginator.PaginatedIterable;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.vectors.OSSVectorsClient;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorBucketsRequest;
import com.aliyun.sdk.service.oss2.vectors.models.ListVectorBucketsResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A paginator for ListVectorBuckets
 * This class is an iterable of {ListVectorBucketsResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListVectorBucketsIterable implements PaginatedIterable<ListVectorBucketsResult> {

    private final OSSVectorsClient client;

    private final ListVectorBucketsRequest firstRequest;

    public ListVectorBucketsIterable(OSSVectorsClient client, ListVectorBucketsRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListVectorBucketsIterable(OSSVectorsClient client, ListVectorBucketsRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListVectorBucketsRequest applyRequest(ListVectorBucketsRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxKeys(options.limit().get()).build();
        }
        return request;
    }

    ListVectorBucketsRequest cloneRequest() {
        return ListVectorBucketsRequest.newBuilder()
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListVectorBucketsResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListVectorBucketsResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String nextMarker;
        private ListVectorBucketsRequest request;

        IteratorImpl(ListVectorBucketsRequest request) {
            this.firstPage = true;
            this.isTruncated = false;
            this.nextMarker = null;
            this.request = request;
        }

        @Override
        public boolean hasNext() {
            return (this.firstPage || this.isTruncated);
        }

        @Override
        public ListVectorBucketsResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.nextMarker)) {
                this.request = this.request.toBuilder().marker(this.nextMarker).build();
            }

            ListVectorBucketsResult result = client.listVectorBuckets(this.request);

            this.firstPage = false;
            this.isTruncated = Optional.ofNullable(result.isTruncated()).orElse(false);
            this.nextMarker = result.nextMarker();

            return result;
        }
    }
}
