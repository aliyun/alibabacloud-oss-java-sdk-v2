package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.transport.Abortable;
import com.aliyun.sdk.service.oss2.utils.ConvertUtils;

import java.io.InputStream;

/**
 * The result for the SelectObject operation.
 */
public final class SelectObjectResult extends ResultModel implements AutoCloseable {

    SelectObjectResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * The CRC-64 value of the object.
     */
    public String hashCrc64ecma() {
        String value = headers.get("x-oss-hash-crc64ecma");
        return value;
    }

    /**
     * The content type of the object.
     */
    public String contentType() {
        String value = headers.get("Content-Type");
        return value;
    }

    /**
     * Select object data.
     */
    public InputStream body() {
        return (InputStream) innerBody;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public void close() throws Exception {
        if (innerBody instanceof InputStream) {
            ((InputStream) innerBody).close();
        }
    }

    /**
     * Close the response forcefully. The remaining data in the server will not
     * be downloaded.
     */
    public void abort() {
        if (innerBody instanceof Abortable) {
            ((Abortable) innerBody).abort();
        }
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(SelectObjectResult result) {
            super(result);
        }

        public SelectObjectResult build() {
            return new SelectObjectResult(this);
        }
    }
}
