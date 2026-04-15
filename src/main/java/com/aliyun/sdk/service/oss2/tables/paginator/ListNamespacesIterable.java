package com.aliyun.sdk.service.oss2.tables.paginator;

import com.aliyun.sdk.service.oss2.paginator.PaginatedIterable;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.models.ListNamespacesRequest;
import com.aliyun.sdk.service.oss2.tables.models.ListNamespacesResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A paginator for ListNamespaces.
 * This class is an iterable of {@link ListNamespacesResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListNamespacesIterable implements PaginatedIterable<ListNamespacesResult> {

    private final OSSTablesClient client;

    private final ListNamespacesRequest firstRequest;

    public ListNamespacesIterable(OSSTablesClient client, ListNamespacesRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListNamespacesIterable(OSSTablesClient client, ListNamespacesRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListNamespacesRequest applyRequest(ListNamespacesRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxNamespaces(options.limit().get().intValue()).build();
        }
        return request;
    }

    ListNamespacesRequest cloneRequest() {
        return ListNamespacesRequest.newBuilder()
                .tableBucketARN(this.firstRequest.tableBucketARN())
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListNamespacesResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListNamespacesResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String nextToken;
        private ListNamespacesRequest request;

        IteratorImpl(ListNamespacesRequest request) {
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
        public ListNamespacesResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.nextToken)) {
                this.request = this.request.toBuilder().continuationToken(this.nextToken).build();
            }

            ListNamespacesResult result = client.listNamespaces(this.request);

            this.firstPage = false;
            this.nextToken = result.continuationToken();
            this.isTruncated = !StringUtils.isNullOrEmpty(this.nextToken);

            return result;
        }
    }
}
