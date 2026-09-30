package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the UpdateBucketMetadataJournalTableConfiguration operation.
 */
public final class UpdateBucketMetadataJournalTableConfigurationRequest extends RequestModel {
    private final String bucket;
    private final JournalTableConfiguration journalTableConfiguration;

    private UpdateBucketMetadataJournalTableConfigurationRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.journalTableConfiguration = builder.journalTableConfiguration;
    }

    public String bucket() {
        return bucket;
    }

    public JournalTableConfiguration journalTableConfiguration() {
        return journalTableConfiguration;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder extends RequestModel.Builder<Builder> {
        private String bucket;
        private JournalTableConfiguration journalTableConfiguration;

        private Builder() {
        }

        private Builder(UpdateBucketMetadataJournalTableConfigurationRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.journalTableConfiguration = request.journalTableConfiguration;
        }

        public Builder bucket(String value) {
            this.bucket = requireNonNull(value);
            return this;
        }

        public Builder journalTableConfiguration(JournalTableConfiguration value) {
            this.journalTableConfiguration = requireNonNull(value);
            return this;
        }

        public UpdateBucketMetadataJournalTableConfigurationRequest build() {
            return new UpdateBucketMetadataJournalTableConfigurationRequest(this);
        }
    }
}
