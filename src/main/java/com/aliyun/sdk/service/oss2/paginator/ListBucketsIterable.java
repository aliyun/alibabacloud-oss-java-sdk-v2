package com.aliyun.sdk.service.oss2.paginator;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.models.ListBucketsRequest;
import com.aliyun.sdk.service.oss2.models.ListBucketsResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A paginator for ListBuckets
 * This class is an iterable of {ListBucketsResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListBucketsIterable implements PaginatedIterable<ListBucketsResult> {

    private final OSSClient client;

    private final ListBucketsRequest firstRequest;

    public ListBucketsIterable(OSSClient client, ListBucketsRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListBucketsIterable(OSSClient client, ListBucketsRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListBucketsRequest applyRequest(ListBucketsRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxKeys(options.limit().get()).build();
        }
        return request;
    }

    ListBucketsRequest cloneRequest() {
        return ListBucketsRequest.newBuilder()
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListBucketsResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListBucketsResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String marker;
        private ListBucketsRequest request;

        IteratorImpl(ListBucketsRequest request) {
            this.firstPage = true;
            this.isTruncated = false;
            this.marker = null;
            this.request = request;
        }

        @Override
        public boolean hasNext() {
            return (this.firstPage || this.isTruncated);
        }

        @Override
        public ListBucketsResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.marker)) {
                this.request = this.request.toBuilder().marker(this.marker).build();
            }

            ListBucketsResult result = client.listBuckets(this.request);

            this.firstPage = false;
            this.isTruncated = Optional.ofNullable(result.isTruncated()).orElse(false);
            this.marker = result.nextMarker();

            return result;
        }
    }
}
