package com.aliyun.sdk.service.oss2.tables.paginator;

import com.aliyun.sdk.service.oss2.paginator.PaginatedIterable;
import com.aliyun.sdk.service.oss2.paginator.PaginatorOptions;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.models.ListTablesRequest;
import com.aliyun.sdk.service.oss2.tables.models.ListTablesResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A paginator for ListTables.
 * This class is an iterable of {@link ListTablesResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListTablesIterable implements PaginatedIterable<ListTablesResult> {

    private final OSSTablesClient client;

    private final ListTablesRequest firstRequest;

    public ListTablesIterable(OSSTablesClient client, ListTablesRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListTablesIterable(OSSTablesClient client, ListTablesRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListTablesRequest applyRequest(ListTablesRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxTables(options.limit().get().intValue()).build();
        }
        return request;
    }

    ListTablesRequest cloneRequest() {
        return ListTablesRequest.newBuilder()
                .tableBucketARN(this.firstRequest.tableBucketARN())
                .namespace(this.firstRequest.namespace())
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListTablesResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListTablesResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String nextToken;
        private ListTablesRequest request;

        IteratorImpl(ListTablesRequest request) {
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
        public ListTablesResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.nextToken)) {
                this.request = this.request.toBuilder().continuationToken(this.nextToken).build();
            }

            ListTablesResult result = client.listTables(this.request);

            this.firstPage = false;
            this.nextToken = result.continuationToken();
            this.isTruncated = !StringUtils.isNullOrEmpty(this.nextToken);

            return result;
        }
    }
}
