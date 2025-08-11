package com.aliyun.sdk.service.oss2.paginator;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.models.ListMultipartUploadsRequest;
import com.aliyun.sdk.service.oss2.models.ListMultipartUploadsResult;
import com.aliyun.sdk.service.oss2.utils.StringUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * A paginator for ListMultipartUploads
 * This class is an iterable of {ListMultipartUploadsResult} that can be used to iterate through all the
 * response pages of the operation.
 */
public class ListMultipartUploadsIterable implements PaginatedIterable<ListMultipartUploadsResult> {

    private final OSSClient client;

    private final ListMultipartUploadsRequest firstRequest;

    public ListMultipartUploadsIterable(OSSClient client, ListMultipartUploadsRequest request) {
        this(client, request, PaginatorOptions.defaults());
    }

    public ListMultipartUploadsIterable(OSSClient client, ListMultipartUploadsRequest request, PaginatorOptions options) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(request);
        this.client = client;
        this.firstRequest = applyRequest(request, options);
    }

    ListMultipartUploadsRequest applyRequest(ListMultipartUploadsRequest request, PaginatorOptions options) {
        if (options.limit().isPresent()) {
            return request.toBuilder().maxUploads(options.limit().get()).build();
        }
        return request;
    }

    ListMultipartUploadsRequest cloneRequest() {
        return ListMultipartUploadsRequest.newBuilder()
                .bucket(this.firstRequest.bucket())
                .headers(this.firstRequest.headers())
                .parameters(this.firstRequest.parameters())
                .build();
    }

    @Override
    public Iterator<ListMultipartUploadsResult> iterator() {
        return new IteratorImpl(cloneRequest());
    }

    private class IteratorImpl implements Iterator<ListMultipartUploadsResult> {

        private boolean firstPage;
        private boolean isTruncated;
        private String nextKeyMarker;
        private String nextUploadIdMarker;
        private ListMultipartUploadsRequest request;

        IteratorImpl(ListMultipartUploadsRequest request) {
            this.firstPage = true;
            this.isTruncated = false;
            this.nextKeyMarker = null;
            this.nextUploadIdMarker = null;
            this.request = request;
        }

        @Override
        public boolean hasNext() {
            return (this.firstPage || this.isTruncated);
        }

        @Override
        public ListMultipartUploadsResult next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more pages left");
            }

            if (!StringUtils.isNullOrEmpty(this.nextKeyMarker)) {
                this.request = this.request.toBuilder()
                        .keyMarker(this.nextKeyMarker)
                        .uploadIdMarker(this.nextUploadIdMarker)
                        .build();
            }

            ListMultipartUploadsResult result = client.listMultipartUploads(this.request);

            this.firstPage = false;
            this.isTruncated = Optional.ofNullable(result.isTruncated()).orElse(false);
            this.nextKeyMarker = result.nextKeyMarker();
            this.nextUploadIdMarker = result.nextUploadIdMarker();

            return result;
        }
    }
}
