package com.aliyun.sdk.service.oss2.paginator;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.models.ListPartsRequest;
import com.aliyun.sdk.service.oss2.models.ListPartsResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A paginator for ListParts
 * This class is an iterable of {ListPartsResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListPartsIterable implements PaginatedIterable<ListPartsResult> {

    private final OSSClient client;

    private final ListPartsRequest firstRequest;

    public ListPartsIterable(OSSClient client, ListPartsRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListPartsIterable(OSSClient client, ListPartsRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListPartsRequest applyRequest(ListPartsRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxParts(options.limit().get()).build();
        }
        return request;
    }

    ListPartsRequest cloneRequest() {
        return ListPartsRequest.newBuilder()
                .bucket(this.firstRequest.bucket())
                .key(this.firstRequest.key())
                .uploadId(this.firstRequest.uploadId())
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListPartsResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListPartsResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private Long nextPartNumberMarker;
        private ListPartsRequest request;

        IteratorImpl(ListPartsRequest request) {
            this.firstPage = true;
            this.isTruncated = false;
            this.nextPartNumberMarker = null;
            this.request = request;
        }

        @Override
        public boolean hasNext() {
            return (this.firstPage || this.isTruncated);
        }

        @Override
        public ListPartsResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (this.nextPartNumberMarker != null) {
                this.request = this.request.toBuilder().partNumberMarker(this.nextPartNumberMarker).build();
            }

            ListPartsResult result = client.listParts(this.request);

            this.firstPage = false;
            this.isTruncated = Optional.ofNullable(result.isTruncated()).orElse(false);
            this.nextPartNumberMarker = result.nextPartNumberMarker();

            return result;
        }
    }
}
