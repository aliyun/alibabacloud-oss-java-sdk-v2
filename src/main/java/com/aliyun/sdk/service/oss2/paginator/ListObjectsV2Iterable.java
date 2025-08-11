package com.aliyun.sdk.service.oss2.paginator;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.models.ListBucketsRequest;
import com.aliyun.sdk.service.oss2.models.ListObjectsV2Request;
import com.aliyun.sdk.service.oss2.models.ListObjectsV2Result;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A paginator for ListObjectsV2
 * This class is an iterable of {ListObjectsV2Result} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListObjectsV2Iterable implements PaginatedIterable<ListObjectsV2Result> {

    private final OSSClient client;

    private final ListObjectsV2Request firstRequest;

    public ListObjectsV2Iterable(OSSClient client, ListObjectsV2Request request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListObjectsV2Iterable(OSSClient client, ListObjectsV2Request request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListObjectsV2Request applyRequest(ListObjectsV2Request request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxKeys(options.limit().get()).build();
        }
        return request;
    }

    ListObjectsV2Request cloneRequest() {
        return ListObjectsV2Request.newBuilder()
                .bucket(this.firstRequest.bucket())
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListObjectsV2Result> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListObjectsV2Result> {

        private boolean firstPage;
        private boolean isTruncated;
        private String continueToken;
        private ListObjectsV2Request request;

        IteratorImpl(ListObjectsV2Request request) {
            this.firstPage = true;
            this.isTruncated = false;
            this.continueToken = null;
            this.request = request;
        }

        @Override
        public boolean hasNext() {
            return (this.firstPage || this.isTruncated);
        }

        @Override
        public ListObjectsV2Result next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.continueToken)) {
                this.request = this.request.toBuilder().continuationToken(this.continueToken).build();
            }

            ListObjectsV2Result result = client.listObjectsV2(this.request);

            this.firstPage = false;
            this.isTruncated = Optional.ofNullable(result.isTruncated()).orElse(false);
            this.continueToken = result.nextContinuationToken();

            return result;
        }
    }
}
