package com.aliyun.sdk.service.oss2.tables.paginator;

import com.aliyun.sdk.service.oss2.paginator.PaginatedIterable;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.models.ListTableBucketsRequest;
import com.aliyun.sdk.service.oss2.tables.models.ListTableBucketsResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A paginator for ListTableBuckets.
 * This class is an iterable of {@link ListTableBucketsResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListTableBucketsIterable implements PaginatedIterable<ListTableBucketsResult> {

    private final OSSTablesClient client;

    private final ListTableBucketsRequest firstRequest;

    public ListTableBucketsIterable(OSSTablesClient client, ListTableBucketsRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListTableBucketsIterable(OSSTablesClient client, ListTableBucketsRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListTableBucketsRequest applyRequest(ListTableBucketsRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxBuckets(options.limit().get().intValue()).build();
        }
        return request;
    }

    ListTableBucketsRequest cloneRequest() {
        return ListTableBucketsRequest.newBuilder()
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListTableBucketsResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListTableBucketsResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String nextToken;
        private ListTableBucketsRequest request;

        IteratorImpl(ListTableBucketsRequest request) {
            this.firstPage = true;
            this.isTruncated = false;
            this.nextToken = null;
            this.request = request;
        }

        @Override
        public boolean hasNext() {
            return (this.firstPage || this.isTruncated);
        }

        @Override
        public ListTableBucketsResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.nextToken)) {
                this.request = this.request.toBuilder().continuationToken(this.nextToken).build();
            }

            ListTableBucketsResult result = client.listTableBuckets(this.request);

            this.firstPage = false;
            this.nextToken = result.continuationToken();
            this.isTruncated = !StringUtils.isNullOrEmpty(this.nextToken);

            return result;
        }
    }
}
