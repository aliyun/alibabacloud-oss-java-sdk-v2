package com.aliyun.sdk.service.oss2.models;

public final class GetBucketArchiveDirectReadResult extends ResultModel {

    /**
     * The archive direct read configuration.
     */
    public ArchiveDirectReadConfiguration archiveDirectReadConfiguration() {
        return (ArchiveDirectReadConfiguration)innerBody;
    }

    GetBucketArchiveDirectReadResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {

        public GetBucketArchiveDirectReadResult build() {
            return new GetBucketArchiveDirectReadResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetBucketArchiveDirectReadResult result) {
            super(result);
        }
    }
}