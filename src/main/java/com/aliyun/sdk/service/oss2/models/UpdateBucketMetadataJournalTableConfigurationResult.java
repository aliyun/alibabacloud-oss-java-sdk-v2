package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the UpdateBucketMetadataJournalTableConfiguration operation.
 */
public final class UpdateBucketMetadataJournalTableConfigurationResult extends ResultModel {
    private UpdateBucketMetadataJournalTableConfigurationResult(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder extends ResultModel.Builder<Builder> {
        private Builder() {
        }

        private Builder(UpdateBucketMetadataJournalTableConfigurationResult result) {
            super(result);
        }

        public UpdateBucketMetadataJournalTableConfigurationResult build() {
            return new UpdateBucketMetadataJournalTableConfigurationResult(this);
        }
    }
}
